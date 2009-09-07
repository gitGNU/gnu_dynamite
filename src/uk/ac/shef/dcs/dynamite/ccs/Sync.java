/* Sync.java - Represents a silent action occurring from synchronisation.
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

import uk.ac.shef.dcs.dynamite.Context;

import uk.ac.shef.dcs.dynamite.lts.Action;
import uk.ac.shef.dcs.dynamite.lts.Label;

/**
 * Represents the silent action occurring through synchronisation.
 * It retains a reference to each of the actions that lead to
 * synchronisation and performs them both when performed itself.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public class Sync
  extends Tau
{

  /**
   * The first action.
   */
  private Action action1;

  /**
   * The second action.
   */
  private Action action2;

  /**
   * Constructs a new synchronisation, resulting from the
   * two supplied actions.
   *
   * @param action1 the first synchronising action.
   * @param action2 the second synchronising action.
   */
  public Sync(Action action1, Action action2)
  {
    this.action1 = action1;
    this.action2 = action2;
  }

  /**
   * Perform the synchronisation by performing
   * both actions.  The context is used to call
   * the actual {@code perform()} method of each
   * action.
   *
   * @throws Exception if a performance fails.
   * @see Context
   */
  public void perform()
    throws Exception
  {
    Context ctx = Context.getContext();
    ctx.perform(action1);
    ctx.perform(action2);
  }

}
