/* Label.java - Represents a transition label.
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
package uk.ac.shef.dcs.dynamite.lts;

/**
 * Represents a transition label.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public abstract class Label
{

    /**
     * The label.
     */
    private String label;

    /**
     * Constructs a new label using the given string.
     *
     * @param label the label to use.
     * @throws IllegalArgumentException if the label is invalid.
     */
    public Label(String label)
    {
	if (isValid(label))
	    this.label = label;
	else
	    throw new IllegalArgumentException("The label " + label + " is not a valid label for this transition system.");
    }

    /**
     * Returns true if the given string represents a valid label.
     *
     * @param label the label to check.
     * @return true if the label is valid.
     */
    public abstract boolean isValid(String label);

}
