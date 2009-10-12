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

import java.util.Collection;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;

import uk.ac.shef.dcs.dynamite.lts.Label;
import uk.ac.shef.dcs.dynamite.lts.Transition;

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
   * The combining macron used to mark co-names.
   */
  public static final char COMBINING_MACRON = '\u0304';

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
   * Registers a new co-name for use in calculus constructions.  The
   * name is automatically extended with combining macrons (Unicode
   * character 772) for the transition label.  The name is checked for
   * validity.  If valid, a transition label for the name is returned.
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
    return register(conames, name, Context.convertConameToLabel(name));
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
    if (!isRegisteredName(name))
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
    if (!isRegisteredConame(name))
      throw new IllegalArgumentException("The name, " + name +
                                         ", is not registered.");
    return channelImpl.getOutputChannel(name);
  }

  /**
   * Stores the supplied data in the specified channel's
   * repository.  Data read by a channel is stored in a specific
   * place maintained by the {@link ChannelFactory} so that it
   * can later be retrieved by user code.  Users should store
   * data they wish to transmit in the appropriate channel.
   * Any previous data stored is lost.
   *
   * @param name the name of the channel.
   * @param data the data to store.
   * @throw NullPointerException if the channel name is null.
   * @throws IllegalArgumentException if the name is not registered.
   */
  public void store(String name, Object data)
  {
    if (!isRegisteredName(name) && !isRegisteredConame(name))
      throw new IllegalArgumentException("The name, " + name +
                                         ", is not registered.");
    channelImpl.store(name, data);
  }

  /**
   * Retrieves any data stored in the specified channel's
   * repository.  Data read by a channel is stored in a specific
   * place maintained by the {@link ChannelFactory} so that it
   * can be retrieved using this method from user code.  The data
   * to write to a channel is expected to be stored in the
   * corresponding repository.
   *
   * @param name the name of the channel.
   * @return the data stored or null if there is no data stored.
   * @throw NullPointerException if the channel name is null.
   * @throws IllegalArgumentException if the name is not registered.
   */
  public Object retrieve(String name)
  {
    if (!isRegisteredName(name) && !isRegisteredConame(name))
      throw new IllegalArgumentException("The name, " + name +
                                         ", is not registered.");
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

  /**
   * Returns true if the given name is registered.
   *
   * @return true if the name is registered as a name.
   * @throws NullPointerException if the name is null.
   */
  public boolean isRegisteredName(String name)
  {
    return names.get(name) != null;
  }

  /**
   * Returns true if the given co-name is registered.
   *
   * @return true if the name is registered as a co-name.
   * @throws NullPointerException if the name is null.
   */
  public boolean isRegisteredConame(String name)
  {
    return conames.get(name) != null;
  }

  /**
   * Returns true if the given name is registered as
   * either a name or co-name.
   *
   * @return true if the name is registered as a name or co-name.
   * @throws NullPointerException if the name is null.
   */
  public boolean isRegistered(String name)
  {
    return isRegisteredName(name) || isRegisteredConame(name);
  }

  /**
   * Converts a transition label to a name.  This
   * is necessary for comparing co-names, where
   * the transition label includes combining macrons.
   * For the label of a transition arising from a name,
   * {@code convertLabelToName(label) == label}.
   *
   * @param label the label to convert.
   * @return the name.
   */
  public static String convertLabelToName(String label)
  {
    if (!isConame(label))
      return label;
    StringBuilder b = new StringBuilder(label.length() / 2);
    for (int a = 0; a < label.length(); ++a)
      {
        char next = label.charAt(a);
        if (next != COMBINING_MACRON)
          b.append(next);
      }
    return b.toString();
  }

  /**
   * Converts a name into a transition label for
   * a co-name.  A co-name has a combining macron
   * attached to each letter.
   *
   * @param name the name to convert.
   * @return the transition label.
   */
  public static String convertConameToLabel(String name)
  {
    StringBuilder b = new StringBuilder(name.length() * 2);
    for (int a = 0; a < name.length(); ++a)
      {
        b.append(name.charAt(a));
        b.append(COMBINING_MACRON);
      }
    return b.toString();
  }

  /**
   * Returns true if the label is a co-name.
   *
   * @param label the label to examine.
   * @return true if the label is a co-name.
   */
  public static boolean isConame(String label)
  {
    return label.indexOf(COMBINING_MACRON) != -1;
  }

  /**
   * Returns the syntax of the calculus in use.
   *
   * @return the syntax of the calculus being used.
   */
  public Collection<Class<? extends Process>> getSyntax()
  {
    return calculus.getSyntax();
  }

  /**
   * Performs the given {@link Transition}.  Actions need to be
   * run by the {@link ChannelFactory} to ensure this
   * happens in the correct environment.
   *
   * @param t the transition being performed.
   * @throws Exception if the action throws an exception.
   */
  public void perform(final Transition t)
    throws Exception
  {
    channelImpl.perform(t);
  }

}
