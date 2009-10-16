/* Pool.java - A fixed size thread pool.
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

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * A pool of threads for use by the {@link ThreadedChannelFactory}.
 * The pool is pre-populated on creation with a fixed number of threads,
 * but may grow as required.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public class Pool
{
  /**
   * The size of the pool.
   */
  private static final int NUMBER_OF_THREADS =
    Runtime.getRuntime().availableProcessors() * 2 + 1;

  /**
   * The pool.
   */
  private BlockingQueue<RunnableAction> pool;

  /**
   * The thread group of the pool threads.
   */
  private ThreadGroup group;

  /**
   * The index of the next thread.
   */
  private AtomicInteger nextThread = new AtomicInteger();

  /**
   * Constructs a new pool.
   */
  public Pool()
  {
    group = new ThreadGroup(this.toString());
    pool = new LinkedBlockingQueue<RunnableAction>();
    for (int a = 0; a < NUMBER_OF_THREADS; ++a)
      {
        pool.offer(constructRunnableAction());
      }
  }

  /**
   * Constructs a new {@link RunnableAction} and returns it.
   *
   * @return the newly constructed action.
   */
  private RunnableAction constructRunnableAction()
  {
    RunnableAction act = new RunnableAction();
    Thread t = new Thread(group, act, "PoolThread-" +
                          nextThread.getAndIncrement());
    t.start();
    return act;
  }

  /**
   * Obtains a {@link RunnableAction} from the pool.
   *
   * @return an action from the pool.
   */
  public RunnableAction obtain()
  {
    RunnableAction act = pool.poll();
    if (act == null)
      act = constructRunnableAction();
    return act;
  }

  /**
   * Returns a {@link RunnableAction} after use.
   *
   * @param act the action to return.
   */
  public void relinquish(RunnableAction act)
  {
    pool.offer(act);
  }

  /**
   * Returns a textual representation of this pool.
   *
   * @return a textual representation.
   */
  public String toString()
  {
    return getClass().getName() +
      "[pool=" + pool +
      ",group=" + group +
      ",nextThread=" + nextThread +
      "]";
  }

}