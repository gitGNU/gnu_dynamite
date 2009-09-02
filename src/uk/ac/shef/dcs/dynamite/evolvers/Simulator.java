/* Simulator.java - Simulates the behaviour of a process.
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
package uk.ac.shef.dcs.dynamite.evolvers;

import java.util.Set;

import uk.ac.shef.dcs.dynamite.Context;
import uk.ac.shef.dcs.dynamite.ContextFactory;
import uk.ac.shef.dcs.dynamite.Evolver;
import uk.ac.shef.dcs.dynamite.NameNotFreeException;
import uk.ac.shef.dcs.dynamite.Process;
import uk.ac.shef.dcs.dynamite.UnsupportedContextException;

import uk.ac.shef.dcs.dynamite.ccs.Coname;
import uk.ac.shef.dcs.dynamite.ccs.Name;
import uk.ac.shef.dcs.dynamite.ccs.Nil;
import uk.ac.shef.dcs.dynamite.ccs.Par;
import uk.ac.shef.dcs.dynamite.ccs.Prefix;
import uk.ac.shef.dcs.dynamite.ccs.Res;
import uk.ac.shef.dcs.dynamite.ccs.Sum;

import uk.ac.shef.dcs.dynamite.lts.State;
import uk.ac.shef.dcs.dynamite.lts.Transition;

/**
 * Simulates process execution.  From the initial process,
 * each possible transition is followed in a depth-first
 * manner.
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
    Set<Transition> trans = p.getPossibleTransitions();
    System.out.println("Possible transitions: " + trans);
    for (Transition t : trans)
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
    throws UnsupportedContextException, NameNotFreeException
  {
    Context.setContext(ContextFactory.getInstance("CCS", "threaded", "dummy").getContext());
    Process a = new Prefix(new Name("a"), Nil.NIL);
    Process aBar = new Prefix(new Coname("a"), Nil.NIL);
    Process sum = new Sum(a, aBar);
    Process par = new Par(a, aBar);
    Simulator s = new Simulator();
    s.evolve(Nil.NIL);
    s.evolve(a);
    s.evolve(aBar);
    s.evolve(sum);
    s.evolve(new Res(sum, "a"));
    s.evolve(par);
    s.evolve(new Res(par, "a"));
    System.out.println("Context: " + Context.getContext());
  }

}

