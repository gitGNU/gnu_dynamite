/* Action.java - A general CCS action used to unify channels and tau.
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

import java.util.Set;

import uk.ac.shef.dcs.dynamite.Process;
import uk.ac.shef.dcs.dynamite.lts.Transition;

/**
 * Represents a CCS action, unifying the silent action
 * with channel names.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public abstract class Action
{

    /**
     * The label used to represent this action.
     */
    private CCSLabel label;

    /**
     * Constructs a new {@link Action}.
     *
     * @param label the label to use.
     */
    public Action(String label)
    {
	this.label = new CCSLabel(label);
    }

    /**
     * Returns the label of this action.
     */
    public CCSLabel getLabel()
    {
	return label;
    }
}
