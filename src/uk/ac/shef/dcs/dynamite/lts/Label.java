/* Label.java - Represents a transition label.
 * Copyright (C) 2007 The University of Sheffield
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
package uk.ac.shef.dcs.dynamite.lts;

/**
 * Represents a transition label.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public abstract class Label
{

  /**
   * The label.
   */
  private String label;

  /**
   * Constructor for subclasses to create specialised labels.
   */
  protected Label()
  {
  }

  /**
   * Constructs a new label using the given string.
   *
   * @param label the label to use.
   * @throws IllegalArgumentException if the label is invalid.
   */
  public Label(String label)
  {
    this.label = label;
  }

  /**
   * Returns a textual representation of this label.
   *
   * @return a textual representation.
   */
  public String toString()
  {
    return label;
  }

  /**
   * Returns true if the object is either another {@link Label}
   * with the same value or a {@link String} which is equal
   * to the value of this label.
   *
   * @param obj the object to compare.
   * @return true if the object is equivalent to this.
   */
  public boolean equals(Object obj)
  {
    if (obj == this)
      return true;
    if (obj == null)
      return false;
    if (obj instanceof Label)
      {
        Label l = (Label) obj;
        return l.label.equals(label);
      }
    if (obj instanceof String)
      {
        String s = (String) obj;
        return s.equals(label);
      }
    return false;
  }

  /**
   * Returns a hash code based on the value of this label.
   *
   * @return a hashcode for this label.
   */
  public int hashCode()
  {
    return label.hashCode();
  }

  /**
   * Returns the text of the label.
   *
   * @return the text of the label.
   */
  public String getText()
  {
    return label;
  }

}
