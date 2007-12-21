/* Evolver.java - Interface for something that evolves processes.
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

package uk.ac.shef.dcs.dynamite;

/**
 * An evolver can take a process and cause it to evolve into
 * a new process via one or more transitions.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public interface Evolver
{

    /**
     * Takes the given process and evolves it one or more
     * times.
     *
     * @param process the process to evolve.
     */
    void evolve(Process process);
}
