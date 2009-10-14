/* Res.java - Represents a CCS restriction E \ a.
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
import uk.ac.shef.dcs.dynamite.Process;
import uk.ac.shef.dcs.dynamite.lts.Transition;

/**
 * Represents a CCS restriction, {@code E \ a}.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public class Res
  implements Process
{

  /**
   * The process to which to apply restriction.
   */
  private final Process process;

  /**
   * The channel name to restrict.
   */
  private final String name;

  /**
   * Constructs a new restriction, which prevents
   * the specified process performing transitions
   * which include the supplied name.
   *
   * @param process the process to which to apply
   *                restriction.
   * @param name the name to restrict.
   * @throws NullPointerException if the name is null.
   * @throws IllegalArgumentException if the name is not
   *                                  registered.
   */
  public Res(Process process, String name)
  {
    if (!Context.getContext().isRegistered(name))
      throw new IllegalArgumentException("The name is not registered.");
    this.process = process;
    this.name = name;
  }

  /**
   * Returns the set of possible transitions from
   * this process.  This is the set of transitions
   * from the process being restricted, minus those
   * that involve the given name.
   *
   * @return the set of possible transitions.
   */
  public Set<Transition> getPossibleTransitions()
  {
    Set<Transition> trans = new HashSet<Transition>();
    for (Transition t : process.getPossibleTransitions())
      {
        String tName =
          Context.convertLabelToName(t.getAction().getLabel().getText());
        if (!tName.equals(name))
          trans.add(new Transition(new Res((Process) t.getStart(), name),
                                   new Res((Process) t.getFinish(), name),
                                   t.getAction(), t));
      }
    return trans;
  }

  /**
   * Returns a textual representation of the restriction.
   *
   * @return a textual representation.
   */
  public String toString()
  {
    return "(" + process + ")\\" + name;
  }

  /**
   * Returns a version of this process after substitution
   * has been applied.  For Res, we return the result of
   * applying substitution to {@code process}.
   *
   * @param var the variable to replace.
   * @param vProc the process to replace it with.
   * @return the process with substitution applied.
   */
  public Process substitute(String var, Process vProc)
  {
    return new Res(process.substitute(var, vProc), name);
  }

}
