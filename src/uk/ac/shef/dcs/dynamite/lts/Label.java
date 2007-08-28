/* Label.java - Represents a transition label.
   Copyright (C) 2007 The University of Sheffield

This file is part of DynamiTE.

DynamiTE is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 3, or (at your option)
any later version.

DynamiTE is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License
along with DynamiTE; see the file COPYING.  If not, write to the
Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
02110-1301 USA.

Linking this library statically or dynamically with other modules is
making a combined work based on this library.  Thus, the terms and
conditions of the GNU General Public License cover the whole
combination.
*/

package uk.ac.shef.dcs.dynamite.lts;

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
