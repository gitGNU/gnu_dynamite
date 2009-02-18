/* Process.java - General interface for a process.
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
 */package uk.ac.shef.dcs.dynamite;

import java.util.Set;
 
import uk.ac.shef.dcs.dynamite.lts.State;
import uk.ac.shef.dcs.dynamite.lts.Transition;

/**
 * Represents a general evolving process.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public interface Process
    extends State
{

    /**
     * Returns the set of possible transitions from this process.
     *
     * @return the set of possible transitions.
     */
    Set<Transition> getPossibleTransitions();

    /**
     * Return a string representation of the process.
     *
     * @return a string representation.
     */
    String toString();

}
