/* Prefix.java - A CCS prefix of the form alpha.P.
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

import java.util.HashSet;
import java.util.Set;

import uk.ac.shef.dcs.dynamite.Process;
import uk.ac.shef.dcs.dynamite.lts.Transition;

/**
 * Represents a CCS prefix.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public class Prefix
    implements Process
{
    /**
     * The action.
     */
    private Action action;

    /**
     * The continuation.
     */
    private Process continuation;

    /**
     * Cache of the output of the toString method
     * as this class is immutable.
     */
    private String toStringCache;

    /**
     * Constructs a new {@link Prefix}.
     *
     * @param action the action to perform.
     * @param continuation the continuing process.
     */
    public Prefix(Action action, Process continuation)
    {
	this.action = action;
	this.continuation = continuation;
    }

    /**
     * There is one possible transition from this process
     * to the continuation via performing the action.
     *
     * @return a singleton set containing the transition.
     */
    public Set<Transition> getPossibleTransitions()
    {
	Set<Transition> trans = new HashSet<Transition>();
	trans.add(new Transition(this, continuation, action.getLabel()));
	return trans;
    }

    /**
     * Returns a textual representation of the prefix.
     *
     * @return a textual representation of the prefix.
     */
    public String toString()
    {
	if (toStringCache == null)
	    toStringCache = action + "." + continuation;
	return toStringCache;
    }

}
