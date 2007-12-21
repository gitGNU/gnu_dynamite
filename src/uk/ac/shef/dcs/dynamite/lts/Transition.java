/* Process.java - Represents a transition from one process to another.
   Copyright (C) 2007 The University of Sheffield

This file is part of DynamiTE.

DynamiTE is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 3, or (at your option)
any later version.

DynamiTE is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License
along with DynamiTE; see the file COPYING.  If not, write to the
Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
02110-1301 USA.

Linking this library statically or dynamically with other modules is
making a combined work based on this library.  Thus, the terms and
conditions of the GNU General Public License cover the whole
combination.
*/

package uk.ac.shef.dcs.dynamite.lts;

/**
 * Represents a transition within a labelled transition system.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public class Transition
{

    /**
     * The start state.
     */
    private State start;
    
    /**
     * The finish state.
     */
    private State finish;

    /**
     * The label.
     */
    private Label label;

    /**
     * Constructs a new {@link Transition}.
     *
     * @param start the start state.
     * @param finish the finish state.
     * @param label the label on the transition.
     */
    public Transition(State start, State finish, Label label)
    {
	this.start = start;
	this.finish = finish;
	this.label = label;
    }

    /**
     * Returns the start state.
     *
     * @return the start state.
     */
    public State getStart()
    {
	return start;
    }

    /**
     * Returns the finish state.
     *
     * @return the finish state.
     */
    public State getFinish()
    {
	return finish;
    }

  /**
   * Returns the transition label.
   *
   * @return the transition label.
   */
  public Label getLabel()
  {
    return label;
  }

}
