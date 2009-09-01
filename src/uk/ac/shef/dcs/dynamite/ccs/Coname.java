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
import uk.ac.shef.dcs.dynamite.OutputChannel;
import uk.ac.shef.dcs.dynamite.NameNotFreeException;

import uk.ac.shef.dcs.dynamite.lts.Label;

/**
 * Represents a CCS coname, which are used within
 * DynamiTE as output channels.
 *
 * @see OutputChannel
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */

public class Coname
  extends AbstractName
{

  /**
   * The label used for this name when it appears
   * on transitions.
   */
  private final Label label;

  /**
   * The output channel used by this name.
   */
  private final OutputChannel channel;

  /**
   * Creates a new name.
   *
   * @param name the name.
   * @throws NullPointerException if the name is null.
   * @throws IllegalArgumentException if the name is reserved.
   * @throws NameNotFreeException if the name is already in use.
   */
  public Coname(String name)
    throws NameNotFreeException
  {
    super(name);
    Context ctx = Context.getContext();
    label = ctx.registerConame(name);
    channel = ctx.getOutputChannel(name);
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
   * Perform this name by retrieving the data
   * for the channel from the context's storage
   * repository and writing it to the output channel.
   *
   * @throws IOException if the write fails.
   * @see Context
   */
  public void perform()
    throws IOException
  {
    Object data = Context.getContext().retrieve(getName());
    channel.write(data);
  }

}