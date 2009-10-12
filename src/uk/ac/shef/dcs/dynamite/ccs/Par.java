/* Par.java - A CCS parallel composition of the form E | F.
 * Copyright (C) 2009 The University of Sheffield
 * Copyright (C) 2009 Andrew John Hughes
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
import uk.ac.shef.dcs.dynamite.lts.Action;
import uk.ac.shef.dcs.dynamite.lts.Transition;

/**
 * Represents a CCS parallel composition, {@code E | F}.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public class Par
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
   * Constructs a new parallel composition, composing the
   * left and right processes.
   *
   * @param left the left-hand operand.
   * @param right the right-hand operand.
   */
  public Par(Process left, Process right)
  {
    this.left = left;
    this.right = right;
  }

  /**
   * <p>
   * Returns the set of possible transitions from this process.
   * For parallel composition, either the left-hand or right-hand
   * operand evolves, leaving the next state of that process
   * still composed with the other process.  Additionally, if
   * it is possible to pair up a name transition with its
   * corresponding co-name transition, a &tau; transition
   * involving both processes occurs.  Formally,
   * </p>
   * <ol>
   * <li>If <code>E</code> can perform <code>&alpha;</code> to
   * become <code>E'</code>, then <code>E | F</code> can perform
   * <code>&alpha;</code> to become <code>E' | F</code>.</li>
   * <li>If <code>F</code> can perform <code>&alpha;</code> to
   * become <code>F'</code>, then <code>E | F</code> can perform
   * <code>&alpha;</code> to become <code>E | F'</code>.</li>
   * <li>If <code>E</code> can perform <code>a</code> and
   * <code>F</code> can perform <code>a'</code>, then <code>E | F</code>
   * can perform <code>&tau;</code> to become <code>E' | F'</code>.</li>
   * </ol>
   *
   * @return the set of possible transitions.
   */
  public Set<Transition> getPossibleTransitions()
  {
    Set<Transition> trans = new HashSet<Transition>();
    // Par1
    for (Transition t : left.getPossibleTransitions())
      {
        Process nextLeft = (Process) t.getFinish();
        trans.add(new Transition(this, new Par(nextLeft, right),
                                 t.getAction(), t));
      }
    // Par2
    for (Transition t : right.getPossibleTransitions())
      {
        Process nextRight = (Process) t.getFinish();
        trans.add(new Transition(this, new Par(left, nextRight),
                                 t.getAction(), t));
      }
    // Now find pairs for synchronisation (Par3)
    Set<Transition> syncTrans = new HashSet<Transition>();
    for (Transition t : trans)
      {
        String label = t.getAction().getLabel().getText();
        if (Context.getContext().isRegisteredName(label))
          {
            for (Transition t2 : trans)
              {
                String label2 = t2.getAction().getLabel().getText();
                if (Context.isConame(label2) &&
                    label.equals(Context.convertLabelToName(label2)))
                  {
                    Par finish1 = (Par) t.getFinish();
                    Par finish2 = (Par) t2.getFinish();
                    Action sync = new Sync(t, t2);
                    Transition[] derivs = new Transition[] { t, t2 };
                    if (!finish1.left.equals(left) &&
                        !finish2.right.equals(right))
                      syncTrans.add(new Transition(this,
                                                   new Par(finish1.left, finish2.right),
                                                   sync, derivs));
                    else if (!finish1.right.equals(right) &&
                             !finish2.left.equals(left))
                      syncTrans.add(new Transition(this,
                                                   new Par(finish2.left, finish1.right),
                                                   sync, derivs));
                  }
              }
          }
      }
    trans.addAll(syncTrans);
    return trans;
  }

  /**
   * Returns a textual representation of the summation.
   *
   * @return a textual representation.
   */
  public String toString()
  {
    return left + " | " + right;
  }

  /**
   * Returns a version of this process after substitution
   * has been applied.  For Par, we return the result of
   * applying substitution to the left and right operands.
   *
   * @param var the variable to replace.
   * @param vProc the process to replace it with.
   * @return the process with substitution applied.
   */
  public Process substitute(String var, Process vProc)
  {
    return new Par(left.substitute(var, vProc),
                   right.substitute(var, vProc));
  }

}
