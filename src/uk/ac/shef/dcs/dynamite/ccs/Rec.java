/* Rec.java - CCS recursion &mu;X.E.
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

import java.util.HashSet;
import java.util.Set;

import uk.ac.shef.dcs.dynamite.Process;
import uk.ac.shef.dcs.dynamite.lts.Transition;

/**
 * Represents CCS recursion, &mu;X.E.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public class Rec
  implements Process
{

  /**
   * The bound variable.
   */
  private final String var;

  /**
   * The process in which {@link #var} is bound.
   */
  private final Process proc;

  /**
   * Constructs a new recursive process, binding
   * the variable {@code var} in {@code proc}.
   *
   * @param var the bound variable.
   * @param proc the process in which {@code var}
   *             is bound.
   */
  public Rec(String var, Process proc)
  {
    this.var = var;
    this.proc = proc;
  }

  /**
   * Returns the set of possible transitions from this process.
   * For recursion, this is all the possible transitions of
   * the bound process, with substitution performed on the
   * end state of the transitions.
   *
   * @return the set of possible transitions.
   */
  public Set<Transition> getPossibleTransitions()
  {
    Set<Transition> trans = new HashSet<Transition>();
    for (Transition t : proc.getPossibleTransitions())
      {
        Process end = (Process) t.getFinish();
        trans.add(new Transition(this,
                                 end.substitute(var, this),
                                 t.getAction()));
      }
    return trans;
  }

  /**
   * Returns a textual representation of the recursion.
   *
   * @return a textual representation.
   */
  public String toString()
  {
    return "\u03BC" + var + "." + proc;
  }

  /**
   * Returns a version of this process after substitution
   * has been applied.  For Rec, we return the result of
   * applying substitution to {@code proc}.
   *
   * @param var the variable to replace.
   * @param vProc the process to replace it with.
   * @return the process with substitution applied.
   */
  public Process substitute(String var, Process vProc)
  {
    return new Rec(var, proc.substitute(var, vProc));
  }

}
