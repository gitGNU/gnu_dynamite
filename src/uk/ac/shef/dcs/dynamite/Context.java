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
  private final String calculus;

  /**
   * The channel implementation used by this context.
   */
  private final String channelImpl;

  /**
   * The locality implementation used by this context.
   */
  private final String localityImpl;

  /**
   * Provides a {@link Context} for the specified calculus,
   * using the supplied channel and locality implementations.
   *
   * @param calculus the calculus to support.
   * @param channelImpl the channel implementation to use.
   * @param localityImpl the locality implementation to use.
   * @throws NullPointerException if any arguments are null.
   */
  public Context(String calculus, String channelImpl, String localityImpl)
  {
    if (calculus == null || channelImpl == null || localityImpl == null)
      throw new NullPointerException("Null argument(s) found: calculus=" + calculus +
                                     ", channelImpl=" + channelImpl +
                                     ", localityImpl=" + localityImpl);
    this.calculus = calculus;
    this.channelImpl = channelImpl;
    this.localityImpl = localityImpl;
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

}
