/* ReN.java - CCS renaming E[f].
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

import uk.ac.shef.dcs.dynamite.Context;
import uk.ac.shef.dcs.dynamite.NameNotFreeException;
import uk.ac.shef.dcs.dynamite.Process;

import uk.ac.shef.dcs.dynamite.lts.Action;
import uk.ac.shef.dcs.dynamite.lts.Transition;

/**
 * Represents CCS renaming, E[f].
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public class Ren
  implements Process
{

  /**
   * The process to apply {@code func} to.
   */
  private final Process proc;

  /**
   * The renaming function.
   */
  private final Function func;

  /**
   * Defines a renaming function.
   */
  public static interface Function
  {
    /**
     * Apply the function to the specified name.
     *
     * @param name the name to apply the function to.
     * @return the result of the function.
     */
    String apply(String name);
  }

  /**
   * Constructs a new process with a renaming
   * function applied.
   *
   * @param proc the process to which {@code func}
   *             is applied.
   * @param func the renaming function.
   */
  public Ren(Process proc, Function func)
  {
    this.proc = proc;
    this.func = func;
  }

  /**
   * Returns the set of possible transitions from this process.
   * For renaming, the transitions are the same as for the
   * process to which the function is applied, except that
   * the function is applied to the label of these transitions.
   * So, if a function translates 'a' into 'b', then the label
   * 'a' becomes 'b', the label 'a&#772;' becomes 'b&#772;' and
   * &tau; remaines the same.
   *
   * @return the set of possible transitions.
   */
  public Set<Transition> getPossibleTransitions()
  {
    Set<Transition> trans = new HashSet<Transition>();
    for (Transition t : proc.getPossibleTransitions())
      {
        String label = t.getAction().getLabel().getText();
        if (CCSLabel.TAU.equals(label))
          trans.add(t);
        else
          {
            String name = Context.convertLabelToName(label);
            String result = func.apply(name);
            Action newAct;
            try
              {
                if (name == label) // name
                  {
                    newAct = new Name(result);
                  }
                else
                  {
                    newAct = new Coname(result);
                  }
              }
            catch (NameNotFreeException e)
              {
                throw new IllegalArgumentException("Function generated non-free name: "
                                                   + name, e);
              }
            trans.add(new Transition(this,
                                     new Ren((Process) t.getFinish(),
                                             func),
                                     newAct));
          }
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
    return proc + "[" + func + "]";
  }

  /**
   * Returns a version of this process after substitution
   * has been applied.  For Ren, we return the result of
   * applying substitution to {@code proc}.
   *
   * @param var the variable to replace.
   * @param vProc the process to replace it with.
   * @return the process with substitution applied.
   */
  public Process substitute(String var, Process vProc)
  {
    return new Ren(proc.substitute(var, vProc), func);
  }

}
