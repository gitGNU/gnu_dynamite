/* Var.java - A CCS variable, X.
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

package uk.ac.shef.dcs.dynamite.ccs;

import java.util.Collections;
import java.util.Set;

import uk.ac.shef.dcs.dynamite.Process;
import uk.ac.shef.dcs.dynamite.lts.Transition;

/**
 * Represents a CCS Var (X).
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public class Var
  implements Process
{

  /**
   * The name of this variable.
   */
  private String name;

  /**
   * Constructs a new {@link Var} with the specified
   * name.
   *
   * @param name the name of the variable.
   */
  public Var(String name)
  {
    this.name = name;
  }

  /**
   * There are no possible transitions for Var.
   *
   * @return an empty set.
   */
  public Set<Transition> getPossibleTransitions()
  {
    return Collections.emptySet();
  }

  /**
   * Returns a textual representation of the Var process.
   *
   * @return the name of the variable.
   */
  public String toString()
  {
    return name;
  }

  /**
   * Returns a version of this process after substitution
   * has been applied.  For Var, we return {@code vProc}
   * if {@code var.equals(name)}.
   *
   * @param var the variable to replace.
   * @param vProc the process to replace it with.
   * @return the process with substitution applied.
   */
  public Process substitute(String var, Process vProc)
  {
    if (var.equals(name))
      return vProc;
    else
      return this;
  }

}
