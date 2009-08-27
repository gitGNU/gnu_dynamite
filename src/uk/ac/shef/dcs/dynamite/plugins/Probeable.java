/* Probeable.java - Classes that can be probed for information about themselves.
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

package uk.ac.shef.dcs.dynamite.plugins;

/**
 * A {@link Probeable} class is one that is loaded
 * by the plugin mechanism.  It must implement methods
 * in this class in order to provide information about
 * itself, which is then used to determine whether or not
 * the plugin fits the requirements of the plugin user.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public interface Probeable
{

  /**
   * Returns the name of this plugin.
   */
  public String getName();

  /**
   * Returns the major version of this plugin.
   */
  public int getMajorVersion();

  /**
   * Returns the minor version of this plugin.
   */
  public int getMinorVersion();

  /**
   * Returns the micro version of this plugin.
   */
  public int getMicroVersion();

}
