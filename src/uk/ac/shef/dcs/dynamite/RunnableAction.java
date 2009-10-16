/* Worker.java - A worker thread that performs Actions.
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

import java.util.LinkedList;
import java.util.Queue;

import uk.ac.shef.dcs.dynamite.lts.Action;

/**
 * An implementation of {@link Runnable} so that
 * an {@link Action} may be executed in a given thread.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public class RunnableAction
  implements Runnable
{

  /**
   * The action to execute.
   */
  private Queue<Action> actions;

  /**
   * Constructs a new {@link RunnableAction}.
   */
  public RunnableAction()
  {
    actions = new LinkedList<Action>();
  }

  /**
   * Adds a new action to execute.
   *
   * @param action the action to execute.
   */
  public void addAction(Action action)
  {
    synchronized (actions)
      {
        actions.add(action);
        actions.notifyAll();
      }
  }

  /**
   * Perform the action.
   */
  public void run()
  {
    Action next;
    try
      {
        while (!Thread.interrupted())
          {
            synchronized (actions)
              {
                next = actions.poll();
                while (next == null)
                  {
                    actions.wait();
                    next = actions.poll();
                  }
              }
            try
              {
                System.out.println("Performing " + next + " on " + Thread.currentThread());
                next.perform();
              }
            catch (Exception e)
              {
                throw new Error(e);
              }
          }
      }
    catch (InterruptedException e)
      {
        /* Shutdown */
      }
  }

  /**
   * Returns a textual representation of this runnable action.
   *
   * @return a textual representation.
   */
  public String toString()
  {
    String actionsString = null;
    synchronized (actions)
      {
        actionsString = actions.toString();
      }
    return getClass().getName() +
      "[actions=" + actionsString +
      "]";
  }

}
