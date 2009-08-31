/* ChannelFactory.java - A factory that provides channels for a process calculus.
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

import uk.ac.shef.dcs.dynamite.plugins.Probeable;

/**
 * Provides {@link InputChannel}s and {@link OutputChannel}s
 * for use by a process calculus.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public interface ChannelFactory
  extends Probeable
{

  /**
   * Creates or returns an {@link InputChannel} with the given name.
   *
   * @param name the name of the input channel.
   * @return an input channel with the given name.
   * @throw NullPointerException if the channel name is null.
   */
  public InputChannel getInputChannel(String name);

  /**
   * Creates or returns an {@link OutputChannel} with the given name.
   *
   * @param name the name of the output channel.
   * @return an output channel with the given name.
   * @throw NullPointerException if the channel name is null.
   */
  public OutputChannel getOutputChannel(String name);

  /**
   * Stores the supplied data in the repository of the specified
   * channel.
   *
   * @param name the name of the channel.
   * @param data the data to store.
   * @throw NullPointerException if the channel name is null.
   */
  public void store(String name, Object data);

  /**
   * Retrieves the data stored in the repository of the specified
   * channel.
   *
   * @param name the name of the channel.
   * @return the stored data, or null if nothing is stored.
   * @throw NullPointerException if the channel name is null.
   */
  public Object retrieve(String name);

}
