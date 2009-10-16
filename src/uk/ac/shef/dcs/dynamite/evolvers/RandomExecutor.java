/* RandomExecutor.java - Picks a transition to follow at random.
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
package uk.ac.shef.dcs.dynamite.evolvers;

import java.util.Random;
import java.util.Set;

import uk.ac.shef.dcs.dynamite.Config;
import uk.ac.shef.dcs.dynamite.Context;
import uk.ac.shef.dcs.dynamite.Evolver;
import uk.ac.shef.dcs.dynamite.Process;

import uk.ac.shef.dcs.dynamite.lts.Transition;

/**
 * Picks a transition to follow at random and evolves it,
 * after performing any side effects.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public class RandomExecutor
  implements Evolver
{

  /**
   * Execute the process by performing any side effects
   * and then preceding with the next transition, chosen
   * at random.
   *
   * @param p the process to evolve.
   */
  public void evolve(Process p)
    throws Exception
  {
    Set<Transition> trans = p.getPossibleTransitions();
    if (trans.size() > 0)
      {
        Transition[] ts = new Transition[trans.size()];
        ts = trans.toArray(ts);
        Transition t = ts[new Random().nextInt(ts.length)];
        Config.logger.fine("Selected transition: " + t);
        Context.getContext().perform(t);
        evolve((Process) t.getFinish());
      }
  }

}
