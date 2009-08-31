/* Name.java - Representation of a CCS name.
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

import java.io.IOException;

import uk.ac.shef.dcs.dynamite.Context;
import uk.ac.shef.dcs.dynamite.InputChannel;
import uk.ac.shef.dcs.dynamite.NameNotFreeException;

import uk.ac.shef.dcs.dynamite.lts.Label;

/**
 * Represents a CCS name, which are used within
 * DynamiTE as input channels.
 *
 * @see InputChannel
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */

public class Name
  extends Action
{

  /**
   * The name of this name.
   */
  private final String name;

  /**
   * The label used for this name when it appears
   * on transitions.
   */
  private final Label label;

  /**
   * The input channel used by this name.
   */
  private final InputChannel channel;

  /**
   * Creates a new name.
   *
   * @param name the name.
   * @throws IllegalArgumentException if the name is either
   *                                  null or reserved.
   * @throws NameNotFreeException if the name is already in use.
   */
  public Name(String name)
    throws NameNotFreeException
  {
    this.name = name;
    Context ctx = Context.getContext();
    label = ctx.registerName(name);
    channel = ctx.getInputChannel(name);
  }

  /**
   * Returns true if the object is also a {@link Name}
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
        Name n = (Name) obj;
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
   * Returns the label for this name.
   *
   * @return a transition label.
   */
  public Label getLabel()
  {
    return label;
  }

  /**
   * Perform this name by reading from the
   * input channel.  The input is stored using
   * the {@link Context}.
   *
   * @throws IOException if the read fails.
   * @see Context
   */
  public void perform()
    throws IOException
  {
    Object data = channel.read();
    Context.getContext().store(name, data);
  }

}