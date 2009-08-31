/* ContextFactory.java - Provides instances of Context for a calculus.
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
import java.util.ServiceLoader;

/**
 * Provides implementations of {@code Context} for a
 * given process calculus and environment.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public abstract class ContextFactory
{

  /**
   * The service loader providing implementations of the factory.
   */
  private static ServiceLoader<ContextFactory> factoryLoader =
    ServiceLoader.load(ContextFactory.class);

  /**
   * Returns an instance of {@code ContextFactory} for the
   * given process calculus, {@code calculus}.  The channels
   * provided by the calculus should use the channel implementation,
   * {@code channelImpl}, and localities (if applicable) should
   * use the locality implementation, {@code localityImpl}.
   *
   * @param calculus the process calculus which this {@code ContextFactory}
   *                 should provide a {@code Context} for.
   * @param channelImpl the type of channel implementation to be used by
   *                    the calculus.
   * @param localityImpl the type of locality implementation to be used by
   *                     the calculus.
   * @return an instance of {@code ContextFactory} which meets the specified
   *         requirements.
   * @throws UnsupportedContextException if an appropriate factory can not
   *                                     be returned.
   */
  public static ContextFactory getInstance(String calculus,
                                           String channelImpl,
                                           String localityImpl)
    throws UnsupportedContextException
  {
    for (ContextFactory f : factoryLoader)
      {
        if (f.getCalculi().contains(calculus) &&
            f.getChannelImplementations().contains(channelImpl) &&
            f.getLocalityImplementations().contains(localityImpl))
          return f.createInstance(calculus, channelImpl, localityImpl);
      }
    throw new UnsupportedContextException(calculus, channelImpl, localityImpl);
  }

  /**
   * Returns the process calculi this {@code ContextFactory} supports.
   *
   * @return the process calculi supported.
   */
  public abstract Collection<String> getCalculi();

  /**
   * Returns the channel implementations this {@code ContextFactory} supports.
   *
   * @return the channel implementations supported.
   */
  public abstract Collection<String> getChannelImplementations();

  /**
   * Returns the locality implementations this {@code ContextFactory} supports.
   *
   * @return the locality implementations supported.
   */
  public abstract Collection<String> getLocalityImplementations();

  /**
   * Creates an instance of {@code DynamiteContextFactory} for the
   * given process calculus, {@code calculus}.  The channels
   * provided by the calculus should use the channel implementation,
   * {@code channelImpl}, and localities (if applicable) should
   * use the locality implementation, {@code localityImpl}.  By the
   * time this method is called, the implementations are guaranteed
   * to be available as {@code ContextFactory} has checked by calling
   * the {@code getCalculi()}, {@code getChannelImplementations()} and
   * {@code getLocalityImplementations()} methods.
   *
   * @param calculus the process calculus which this {@code ContextFactory}
   *                 should provide a {@code Context} for.
   * @param channelImpl the type of channel implementation to be used by
   *                    the calculus.
   * @param localityImpl the type of locality implementation to be used by
   *                     the calculus.
   * @return an instance of {@code ContextFactory} which meets the specified
   *         requirements.
   *
   * @see #getInstance(String, String, String)
   * @see #getCalculi
   * @see #getChannelImplementations
   * @see #getLocalityImplementations
   */
  public abstract ContextFactory createInstance(String calculus,
                                                String channelImpl,
                                                String localityImpl);

}