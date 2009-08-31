/* DynamiTEContext.java - A Context implementation for DynamiTE calculi.
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
 * This class provides a {@link Context} implementation
 * which supports the calculi currently provided by
 * DynamiTE: CCS, CaSE and Nomadic Time.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public class DynamiTEContext
  extends Context
{

  /**
   * Provides a {@link DynamiTEContext} for the specified calculus,
   * using the supplied channel and locality implementations.
   *
   * @param calculus the calculus to support.
   * @param channelImpl the channel implementation to use.
   * @param localityImpl the locality implementation to use.
   */
  public DynamiTEContext(Calculus calculus, ChannelFactory channelImpl,
                         LocalityFactory localityImpl)
  {
    super(calculus, channelImpl, localityImpl);
  }

}