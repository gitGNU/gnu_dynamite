/* AbstractName.java - Representation of a CCS name or co-name.
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
package uk.ac.shef.dcs.dynamite.ccs;

import uk.ac.shef.dcs.dynamite.lts.Action;

/**
 * An abstraction layer that provides common functionality
 * for both CCS names and conames.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public abstract class AbstractName
  extends Action
{

  /**
   * The name of this name.
   */
  private final String name;

  /**
   * Creates a new name.
   *
   * @param name the name.
   * @throws NullPointerException if the name is null.
   * @throws IllegalArgumentException if the name is reserved.
   * @throws NameNotFreeException if the name is already in use.
   */
  public AbstractName(String name)
  {
    this.name = name;
  }

  /**
   * Returns true if the object is also an {@link AbstractName}
   * with the same value.
   *
   * @param obj the object to compare.
   * @return true if the object is equal to this one.
   */
  public boolean equals(Object obj)
  {
    if (obj == this)
      return true;
    if (obj == null)
      return false;
    if (obj instanceof Name)
      {
        AbstractName n = (AbstractName) obj;
        return n.name.equals(name);
      }
    return false;
  }

  /**
   * Returns a hashcode based on the name.
   *
   * @return the hashcode for this {@link Name}.
   */
  public int hashCode()
  {
    return name.hashCode();
  }

  /**
   * Returns the name of this name.
   *
   * @return the name of this name.
   */
  public String getName()
  {
    return name;
  }

}