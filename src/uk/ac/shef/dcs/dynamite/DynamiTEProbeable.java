/* DynamiTEProbeable.java - A base class for Probeable implementations.
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
 * An abstract base class for implementations of
 * {@link Probeable} which are part of the DynamiTE
 * framework.  This provides the versioning information
 * using DynamiTE's version information from {@link Config}.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public abstract class DynamiTEProbeable
  implements Probeable
{

  /**
   * Returns the major version of this plugin.
   */
  public int getMajorVersion()
  {
    return Config.MAJOR_VERSION;
  }

  /**
   * Returns the minor version of this plugin.
   */
  public int getMinorVersion()
  {
    return Config.MINOR_VERSION;
  }

  /**
   * Returns the micro version of this plugin.
   */
  public int getMicroVersion()
  {
    return Config.MICRO_VERSION;
  }

  /**
   * Returns any additional information about the
   * version (codenames, etc.)
   *
   * @return any additional information.
   */
  public String getAdditionalInfo()
  {
    return Config.ADDITIONAL_INFO;
  }

}