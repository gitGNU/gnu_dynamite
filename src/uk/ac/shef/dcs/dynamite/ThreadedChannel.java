/* ThreadedChannel.java - A channel implementation using a SynchronousQueue.
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

package uk.ac.shef.dcs.dynamite;

import java.io.InterruptedIOException;

import java.util.concurrent.SynchronousQueue;

/**
 * An implementation of {@link InputChannel} and
 * {@link OutputChannel} using a {@link SynchronousQueue}.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public class ThreadedChannel
  implements InputChannel, OutputChannel
{

  /**
   * The queue used for this channel.
   */
  private SynchronousQueue<Object> queue;

  /**
   * The name of this channel.
   */
  private String name;

  /**
   * Constructs a new {@link ThreadedChannel}
   * for the given name.
   *
   * @param name the name of the new channel.
   */
  public ThreadedChannel(String name)
  {
    queue = new SynchronousQueue<Object>();
    this.name = name;
  }

  /**
   * Transmit data over the channel.
   *
   * @param data the item to transmit.
   * @throws InterruptedIOException if the transfer is interrupted.
   */
  public void write(Object data)
    throws InterruptedIOException
  {
    try
      {
        queue.put(data);
      }
    catch (InterruptedException e)
      {
        throw (InterruptedIOException)
          new InterruptedIOException("The write was interrupted.").initCause(e);
      }
  }

  /**
   * Returns data from the channel.
   *
   * @return a data item.
   * @throws InterruptedIOException if the read is interrupted.
   */
  public Object read()
    throws InterruptedIOException
  {
    try
      {
        return queue.take();
      }
    catch (InterruptedException e)
      {
        throw (InterruptedIOException)
          new InterruptedIOException("The read was interrupted.").initCause(e);
      }
  }

  /**
   * Returns true if the channels are both
   * {@link ThreadedChannel}s with the same name.
   *
   * @return true if the channels are both instances
   *         of this class and have the same name.
   */
  public boolean equals(Object obj)
  {
    if (obj == this)
      return true;
    if (obj == null)
      return false;
    if (obj instanceof ThreadedChannel)
      {
        ThreadedChannel tc = (ThreadedChannel) obj;
        return tc.name.equals(name);
      }
    return false;
  }

  /**
   * Returns a hashcode based on the name of the channel.
   *
   * @return the hashcode of this channel.
   */
  public int hashCode()
  {
    return name.hashCode();
  }

  /**
   * Returns a textual representation of this channel.
   *
   * @return a textual representation.
   */
  public String toString()
  {
    return getClass().getName() + "[name=" + name + "]";
  }

}
