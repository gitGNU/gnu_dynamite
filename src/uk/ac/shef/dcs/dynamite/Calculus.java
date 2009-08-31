/* Calculus.java - A representation of a process calculus.
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

import java.util.Collection;

import uk.ac.shef.dcs.dynamite.lts.Label;

import uk.ac.shef.dcs.dynamite.plugins.Probeable;

/**
 * Represents a process calculus, and provides
 * the processes that make up its syntax.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public interface Calculus
  extends Probeable
{

  /**
   * Returns the syntax of the process calculus as
   * a set of {@code Process} implementations.
   *
   * @return the syntax of the calculus.
   */
  public Collection<Class<? extends Process>> getSyntax();

  /**
   * Returns a transition label using the given
   * text, if the text is valid for this particular
   * calculus.
   *
   * @param label the label for the transition.
   * @throws IllegalArgumentException if the name is invalid.
   */
  public Label getLabel(String label);

}
