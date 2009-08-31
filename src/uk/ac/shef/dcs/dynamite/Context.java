/* Context.java - Provides a context in which a calculus operates.
 * Copyright (C) 2009 The University of Sheffield
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

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;

import uk.ac.shef.dcs.dynamite.lts.Label;

/**
 * Represents the context in which a process calculus operates.
 * This includes the set of names and co-names for calculi based
 * on CCS, the set of clocks for timed calculi and the set of
 * localities for calculi that provide them.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public abstract class Context
{

  /**
   * The current context in use.
   */
  private static Context currentContext;

  /**
   * The calculus supported by this context.
   */
  private final Calculus calculus;

  /**
   * The channel implementation used by this context.
   */
  private final ChannelFactory channelImpl;

  /**
   * The locality implementation used by this context.
   */
  private final LocalityFactory localityImpl;

  /**
   * The map of names to transition labels.
   */
  private ConcurrentMap<String,Label> names;

  /**
   * The map of co-names to transition labels.
   */
  private ConcurrentMap<String,Label> conames;

  /**
   * Provides a {@link Context} for the specified calculus,
   * using the supplied channel and locality implementations.
   *
   * @param calculus the calculus to support.
   * @param channelImpl the channel implementation to use.
   * @param localityImpl the locality implementation to use.
   * @throws NullPointerException if any arguments are null.
   */
  public Context(Calculus calculus, ChannelFactory channelImpl,
                 LocalityFactory localityImpl)
  {
    if (calculus == null || channelImpl == null || localityImpl == null)
      throw new NullPointerException("Null argument(s) found: calculus=" + calculus +
                                     ", channelImpl=" + channelImpl +
                                     ", localityImpl=" + localityImpl);
    this.calculus = calculus;
    this.channelImpl = channelImpl;
    this.localityImpl = localityImpl;
    names = new ConcurrentHashMap<String,Label>();
    conames = new ConcurrentHashMap<String,Label>();
  }

  /**
   * Sets the current context.
   *
   * @param context the new current context.
   */
  public static synchronized void setContext(Context context)
  {
    currentContext = context;
  }

  /**
   * Retrieves the current context.
   *
   * @return the current context.
   */
  public static synchronized Context getContext()
  {
    return currentContext;
  }

  /**
   * Registers a new name for use in calculus
   * constructions.  The name is checked for validity.
   * If valid, a transition label for the name is returned.
   *
   * @param name the new name to register.
   * @return a transition label for this name.
   * @throws NullPointerException if the name is null.
   * @throws IllegalArgumentException if the name is invalid.
   * @throws NameNotFreeException if the name is already in use.
   * @throws UnsupportedOperationException if the calculus does
   *                                       not support names.
   */
  public Label registerName(String name)
    throws NameNotFreeException
  {
    return register(names, name, name);
  }

  /**
   * Registers a new co-name for use in calculus
   * constructions.  The name is checked for validity.
   * If valid, a transition label for the name is returned.
   *
   * @param name the new co-name to register.
   * @return a transition label for this name.
   * @throws NullPointerException if the name is null.
   * @throws IllegalArgumentException if the name is invalid.
   * @throws NameNotFreeException if the name is already in use.
   * @throws UnsupportedOperationException if the calculus does
   *                                       not support names.
   */
  public Label registerConame(String name)
    throws NameNotFreeException
  {
    return register(conames, name, '\u035e' + name);
  }

  /**
   * Registers a name in the supplied map, after first
   * checking that the name is not null, valid and free.
   *
   * @param names the map to register the name in.
   * @param name the new name to register.
   * @param labelText the text to use for the label.
   * @return a transition label for this name.
   * @throws NullPointerException if the name is null.
   * @throws IllegalArgumentException if the name is invalid.
   * @throws NameNotFreeException if the name is already in use.
   * @throws UnsupportedOperationException if the calculus does
   *                                       not support names.
   */
  private Label register(ConcurrentMap<String,Label> names,
                         String name, String labelText)
    throws NameNotFreeException
  {
    if (name == null)
      throw new NullPointerException("Null names are not allowed.");
    Label l = calculus.getLabel(labelText);
    Label mapLabel = names.putIfAbsent(name, l);
    if (mapLabel != null)
      throw new NameNotFreeException(name);
    return l;
  }

  /**
   * Returns the {@link InputChannel} for the specified name.
   *
   * @param name the name of the input channel.
   * @return the input channel for the given name.
   * @throws NullPointerException if the name is null.
   * @throws IllegalArgumentException if the name is not registered.
   */
  public InputChannel getInputChannel(String name)
  {
    if (names.get(name) == null)
      throw new IllegalArgumentException("The name, " + name +
                                         ", is not registered.");
    return channelImpl.getInputChannel(name);
  }

  /**
   * Returns the {@link OutputChannel} for the specified name.
   *
   * @param name the name of the output channel.
   * @return the output channel for the given name.
   * @throws NullPointerException if the name is null.
   * @throws IllegalArgumentException if the name is not registered.
   */
  public OutputChannel getOutputChannel(String name)
  {
    if (names.get(name) == null)
      throw new IllegalArgumentException("The name, " + name +
                                         ", is not registered.");
    return channelImpl.getOutputChannel(name);
  }

  /**
   * Stores the supplied data in the specified channel's
   * repository.  Data read by a channel is stored in a specific
   * place maintained by the {@link ChannelFactory} so that it
   * can later be retrieved by user code.  Any previous data
   * stored is lost.
   *
   * @param name the name of the channel.
   * @param data the data to store.
   * @throw NullPointerException if the channel name is null.
   */
  public void store(String name, Object data)
  {
    channelImpl.store(name, data);
  }

  /**
   * Retrieves any data stored in the specified channel's
   * repository.  Data read by a channel is stored in a specific
   * place maintained by the {@link ChannelFactory} so that it
   * can be retrieved using this method from user code.
   *
   * @param name the name of the channel.
   * @return the data stored or null if there is no data stored.
   * @throw NullPointerException if the channel name is null.
   */
  public Object retrieve(String name)
  {
    return channelImpl.retrieve(name);
  }

  /**
   * Returns a textual representation of this context.
   *
   * @return a textual representation.
   */
  public String toString()
  {
    return getClass().getName() + "[calculus=" + calculus +
      ",channelImpl=" + channelImpl +
      ",localityImpl=" + localityImpl +
      ",names=" + names.keySet() +
      ",conames=" + conames.keySet() +
      "]";
  }
}
