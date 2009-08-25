/* UnsupportedContextException.java - Thrown if a type of context is unavailable.
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
 * An exception that indicates that the requested
 * {@code Context} type is unavailable.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public class UnsupportedContextException
  extends Exception
{

  /**
   * Constructs a new exception for the specified calculus,
   * channel and locality implementations with no known cause.
   *
   * @param calculus the requested process calculus.
   * @param channelImpl the requested channel implementation.
   * @param localityImpl the requested locality implementation.
   */
  public UnsupportedContextException(String calculus, String channelImpl,
                                     String localityImpl)
  {
    this(calculus, channelImpl, localityImpl, null);
  }

  /**
   * Constructs a new exception for the specified calculus,
   * channel and locality implementations with the given cause.
   *
   * @param calculus the requested process calculus.
   * @param channelImpl the requested channel implementation.
   * @param localityImpl the requested locality implementation.
   * @param cause the cause of this exception.
   */
  public UnsupportedContextException(String calculus, String channelImpl,
                                     String localityImpl, Throwable cause)
  {
    super("No ContextFactory implementations which support the " + calculus +
          " calculus with " + channelImpl + " channels and " + localityImpl +
          " localities are available.", cause);
  }

}

