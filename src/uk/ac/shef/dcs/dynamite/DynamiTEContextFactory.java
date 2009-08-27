/* DynamiTEContextFactory.java - Provides instances of Context supported by DynamiTE.
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

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.ServiceLoader;

import uk.ac.shef.dcs.dynamite.plugins.Plugins;

/**
 * Provides implementations of {@code Context} supported
 * by DynamiTE.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public class DynamiTEContextFactory
  extends ContextFactory
{

  /**
   * The service loader providing process calculi.
   */
  private static ServiceLoader<Calculus> calcLoader =
    ServiceLoader.load(Calculus.class);

  /**
   * The service loader providing channel implementations.
   */
  private static ServiceLoader<ChannelFactory> channelLoader =
    ServiceLoader.load(ChannelFactory.class);

  /**
   * The service loader providing locality implementations.
   */
  private static ServiceLoader<LocalityFactory> localityLoader =
    ServiceLoader.load(LocalityFactory.class);

  /**
   * The map of supported process calculi.
   * This is unmodifiable once initialised.
   */
  private static final Map<String,Calculus> calculi;

  /**
   * The map of supported channel implementations.
   * This is unmodifiable once initialised.
   */
  private static final Map<String,ChannelFactory> channelImpls;

  /**
   * The map of supported locality implementations.
   * This is unmodifiable once initialised.
   */
  private static final Map<String,LocalityFactory> localityImpls;

  /**
   * Static initialiser to setup {@code channelImpls} and
   * {@code localityImpls}.
   */
  static
  {
    calculi = Plugins.probePlugins(calcLoader);
    channelImpls = Plugins.probePlugins(channelLoader);
    localityImpls = Plugins.probePlugins(localityLoader);
  }

  /**
   * The calculus supported by contexts produced by this factory.
   */
  private final String calculus;

  /**
   * The channel implementation used by contexts produced by
   * this factory.
   */
  private final String channelImpl;

  /**
   * The locality implementation used by contexts produced by
   * this factory.
   */
  private final String localityImpl;

  /**
   * Default constructor so the factory can be instantiated by
   * the {@link java.util.ServiceLoader} mechanism.
   */
  public DynamiTEContextFactory()
  {
    this.calculus = null;
    this.channelImpl = null;
    this.localityImpl = null;
  }

  /**
   * Constructs a new instance of {@code DynamiteContextFactory}
   * for a given process calculus, channel and locality implementation.
   *
   * @param calculus the process calculus which this {@code DynamiTEContextFactory}
   *                 should provide a {@code Context} for.
   * @param channelImpl the type of channel implementation to be used by
   *                    the calculus.
   * @param localityImpl the type of locality implementation to be used by
   *                     the calculus.
   */
  public DynamiTEContextFactory(String calculus, String channelImpl,
                                String localityImpl)
  {
    this.calculus = calculus;
    this.channelImpl = channelImpl;
    this.localityImpl = localityImpl;
  }

  /**
   * Returns an instance of {@code DynamiteContextFactory} for the
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
   * @throws UnsupportedContextException if an appropriate factory can not
   *                                     be returned.
   *
   * @see ContextFactory#getInstance(String, String, String)
   * @see ContextFactory#getCalculi
   * @see ContextFactory#getChannelImplementations
   * @see ContextFactory#getLocalityImplementations
   */
  public static ContextFactory getInstance(String calculus,
                                           String channelImpl,
                                           String localityImpl)
  {
    return new DynamiTEContextFactory(calculus, channelImpl, localityImpl);
  }

  /**
   * Returns the process calculi DynamiTE supports.
   *
   * @return the process calculi supported.
   */
  public Collection<String> getCalculi()
  {
    return calculi.keySet();
  }

  /**
   * Returns the channel implementations this {@code ContextFactory} supports.
   *
   * @return the channel implementations supported.
   */
  public Collection<String> getChannelImplementations()
  {
    return channelImpls.keySet();
  }

  /**
   * Returns the locality implementations this {@code ContextFactory} supports.
   *
   * @return the locality implementations supported.
   */
  public Collection<String> getLocalityImplementations()
  {
    return localityImpls.keySet();
  }

}