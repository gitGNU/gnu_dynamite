/* ThreadedChannelFactory.java - A ChannelFactory implementation for ThreadedChannel.
 * Copyright (C) 2009 The University of Sheffield
 * Copyright (C) 2009 Andrew John Hughes
 *
 * This file is part of DynamiTE.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Linking this library statically or dynamically with other modules is
 * making a combined work based on this library.  Thus, the terms and
 * conditions of the GNU General Public License cover the whole
 * combination.
 */

package uk.ac.shef.dcs.dynamite;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import uk.ac.shef.dcs.dynamite.lts.Transition;

/**
 * This class provides an implementation of
 * {@link ChannelFactory} for the channel implementation
 * represented by the {@link ThreadedChannel} class.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public class ThreadedChannelFactory
  extends DynamiTEProbeable
  implements ChannelFactory
{

  /**
   * The map of channel names to
   * {@link ThreadedChannel}s.
   */
  private ConcurrentMap<String,ThreadedChannel> channels;

  /**
   * The map of channel names to storage repositories
   * in the form of a {@link ThreadLocal}.
   */
  private ConcurrentMap<String,ThreadLocal<Object>> repositories;

  /**
   * The map of processes to runnable actions.
   */
  private ConcurrentMap<Process,RunnableAction> actions;

  /**
   * The pool of runnable actions.
   */
  private Pool pool;

  /**
   * Constructs a new {@link ThreadedChannelFactory}.
   */
  public ThreadedChannelFactory()
  {
    channels = new ConcurrentHashMap<String,ThreadedChannel>();
    repositories = new ConcurrentHashMap<String,ThreadLocal<Object>>();
    actions = new ConcurrentHashMap<Process,RunnableAction>();
    pool = new Pool();
  }

  /**
   * Creates or returns a {@link ThreadedChannel} with the
   * given name.  If a channel has already been created with
   * the supplied name, it is located and returned.  If not,
   * a new channel is created and returned, after a reference
   * to it has been stored for future reference.
   *
   * @param name the name of the channel to create or return.
   * @throw NullPointerException if the channel name is null.
   */
  public ThreadedChannel createOrReturnChannel(String name)
  {
    ThreadedChannel channel = channels.get(name);
    if (channel == null)
      {
        ThreadedChannel newChannel = new ThreadedChannel(name);
        channel = channels.putIfAbsent(name, newChannel);
        if (channel == null)
          channel = newChannel;
      }
    return channel;
  }

  /**
   * Creates or returns an {@link InputChannel} with the given name.
   *
   * @param name the name of the input channel.
   * @return an input channel with the given name.
   * @throw NullPointerException if the channel name is null.
   */
  public InputChannel getInputChannel(String name)
  {
    return createOrReturnChannel(name);
  }

  /**
   * Creates or returns an {@link OutputChannel} with the given name.
   *
   * @param name the name of the output channel.
   * @return an output channel with the given name.
   * @throw NullPointerException if the channel name is null.
   */
  public OutputChannel getOutputChannel(String name)
  {
    return createOrReturnChannel(name);
  }

  /**
   * Returns the name of this implementation.
   *
   * @return the name of the implementation.
   */
  public String getName()
  {
    return "threaded";
  }

  /**
   * Stores the supplied data in the repository of the specified
   * channel.
   *
   * @param name the name of the channel.
   * @param data the data to store.
   */
  public void store(String name, Object data)
  {
    ThreadLocal<Object> store = repositories.get(name);
    if (store == null)
      {
        ThreadLocal<Object> newStore = new ThreadLocal<Object>();
        store = repositories.putIfAbsent(name, newStore);
        if (store == null)
          store = newStore;
      }
    store.set(data);
  }

  /**
   * Retrieves the data stored in the repository of the specified
   * channel.
   *
   * @param name the name of the channel.
   * @return the stored data, or null if nothing is stored.
   */
  public Object retrieve(String name)
  {
    ThreadLocal<Object> store = repositories.get(name);
    if (store == null)
      return null;
    return store.get();
  }

  /**
   * Perform the given action by passing it to the
   * {@link ExecutorService} to run in a different thread.
   *
   * @param action the action to perform.
   * @throws Exception if the action throws an exception.
   */
  public void perform(Transition t)
    throws Exception
  {
    Transition[] derivs = t.getDerivatives();
    while (derivs != null && derivs.length == 1)
      {
        t = derivs[0];
        derivs = t.getDerivatives();
      }
    Process start = (Process) t.getStart();
    synchronized (actions)
      {
        RunnableAction runnable = actions.get(start);
        if (runnable == null)
          {
            runnable = pool.obtain();
            actions.put(start, runnable);
          }
        runnable.addAction(t.getAction());
        actions.remove(start);
        Process finish = (Process) t.getFinish();
        if (finish instanceof TerminalProcess)
          {
            pool.relinquish(runnable);
          }
        else
          {
            actions.put(finish, runnable);
          }
      }
  }

}
