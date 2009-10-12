/* TerminalProcess.java - a process with no transitions.
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

package uk.ac.shef.dcs.dynamite;

import java.util.Collections;
import java.util.Set;

import uk.ac.shef.dcs.dynamite.lts.Transition;

/**
 * Denotes a process with no transitions.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public abstract class TerminalProcess
    implements Process
{

  /**
   * There are no possible transitions for a terminal process.
   *
   * @return an empty set.
   */
  public Set<Transition> getPossibleTransitions()
  {
    return Collections.emptySet();
  }

  /**
   * Perform substitution on this process, replacing
   * occurrences of the named variable with the supplied
   * process.  Always returns the original process
   * ({@code this}) for a terminal process.
   *
   * @param var the variable to replace.
   * @param proc the process to replace it with.
   * @return the process with substitution applied.
   */
  public Process substitute(String var, Process proc)
  {
    return this;
  }


}
