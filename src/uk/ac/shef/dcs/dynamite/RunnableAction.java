/* RunnableAction.java - An action that can be run.
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

import java.util.concurrent.Callable;

import uk.ac.shef.dcs.dynamite.lts.Action;

/**
 * An implementation of {@link Runnable} so that
 * an {@link Action} may be executed in a given thread.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public class RunnableAction
  implements Runnable, Callable<Void>
{

  /**
   * The action to execute.
   */
  private Action action;

  /**
   * The thread associated with this {@link RunnableAction}.
   */
  private Thread thread;

  /**
   * Sets the action to execute.
   *
   * @param action the action to execute.
   */
  public void setAction(Action action)
  {
    this.action = action;
  }

  /**
   * Sets the thread used to run this action.
   *
   * @param thread the thread used to run this action.
   */
  public void setThread(Thread thread)
  {
    this.thread = thread;
  }

  /**
   * Perform the action.
   */
  public void run()
  {
    try
      {
        call();
      }
    catch (Exception e)
      {
        throw new Error(e);
      }
  }

  /**
   * Perform the action.
   */
  public Void call()
    throws Exception
  {
    action.perform();
    return null;
  }

  /**
   * Execute the action in the appropriate thread.
   */
  public void execute()
  {
    thread.run();
  }

  /**
   * Returns a textual representation of this runnable action.
   *
   * @return a textual representation.
   */
  public String toString()
  {
    return getClass().getName() +
      "[action=" + action +
      ",thread=" + thread +
      "]";
  }

}
