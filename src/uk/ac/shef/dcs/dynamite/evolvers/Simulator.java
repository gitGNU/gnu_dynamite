/* Simulator.java - Simulates the behaviour of a process.
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

package uk.ac.shef.dcs.dynamite.evolvers;

import uk.ac.shef.dcs.dynamite.Evolver;
import uk.ac.shef.dcs.dynamite.Process;

import uk.ac.shef.dcs.dynamite.lts.State;
import uk.ac.shef.dcs.dynamite.lts.Transition;

/**
 * Simulates process execution.  From the initial process,
 * each possible transition is made and the transitions.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public class Simulator
  implements Evolver
{
  
  /**
   * Simulate the process evolving by following all
   * possible transitions from the current state.
   *
   * @param p the process to evolve.
   */
  public void evolve(Process p)
  {
    System.out.println("Evolving process: " + p);
    for (Transition t : p.getPossibleTransitions())
      {
	State f = t.getFinish();
	if (f instanceof Process)
	  {
	    System.out.println("Following transition " + t);
	    evolve((Process) f);
	  }
      }
  }

  /**
   * A test harness to run the simulator.
   *
   * @param args the command-line arguments.
   */
  public static void main(String[] args)
  {
    new Simulator().evolve(uk.ac.shef.dcs.dynamite.ccs.Nil.NIL);
  }

}

