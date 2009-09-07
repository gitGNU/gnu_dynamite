/* Sum.java - A CCS summation of the form E + F.
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
 * Represents a CCS summation, {@code E + F}.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public class Sum
  implements Process
{

  /**
   * The left-hand operand.
   */
  private final Process left;

  /**
   * The right-hand operand.
   */
  private final Process right;

  /**
   * Constructs a new summation, composing the
   * left and right processes.
   *
   * @param left the left-hand operand.
   * @param right the right-hand operand.
   */
  public Sum(Process left, Process right)
  {
    this.left = left;
    this.right = right;
  }

  /**
   * <p>
   * Returns the set of possible transitions from this process.
   * For a summation, either the left-hand or right-hand operand
   * evolves, leaving only the next state of that process i.e.
   * </p>
   * <ol>
   * <li>If <code>E</code> can perform <code>&alpha;</code> to
   * become <code>E'</code>, then <code>E + F</code> can perform
   * <code>&alpha;</code> to become <code>E'</code>.</li>
   * <li>If <code>F</code> can perform <code>&alpha;</code> to
   * become <code>F'</code>, then <code>E + F</code> can perform
   * <code>&alpha;</code> to become <code>F'</code>.</li>
   * </ol>
   *
   * @return the set of possible transitions.
   */
  public Set<Transition> getPossibleTransitions()
  {
    Set<Transition> trans = new HashSet<Transition>();
    for (Transition t : left.getPossibleTransitions())
      {
        trans.add(new Transition(this, t.getFinish(), t.getAction()));
      }
    for (Transition t : right.getPossibleTransitions())
      {
        trans.add(new Transition(this, t.getFinish(), t.getAction()));
      }
    return trans;
  }

  /**
   * Returns a textual representation of the summation.
   *
   * @return a textual representation.
   */
  public String toString()
  {
    return left + " + " + right;
  }

  /**
   * Returns a version of this process after substitution
   * has been applied.  For Sum, we return the result of
   * applying substitution to the left and right operands.
   *
   * @param var the variable to replace.
   * @param vProc the process to replace it with.
   * @return the process with substitution applied.
   */
  public Process substitute(String var, Process vProc)
  {
    return new Sum(left.substitute(var, vProc),
                   right.substitute(var, vProc));
  }

}
