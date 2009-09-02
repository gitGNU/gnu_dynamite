/* CCS.java - A representation of CCS.
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

package uk.ac.shef.dcs.dynamite.ccs;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import uk.ac.shef.dcs.dynamite.Calculus;
import uk.ac.shef.dcs.dynamite.DynamiTEProbeable;
import uk.ac.shef.dcs.dynamite.Process;

import uk.ac.shef.dcs.dynamite.lts.Label;

/**
 * <p>
 * This class provides an implementation of {@link Calculus}
 * for the Calculus of Communicating Systems (CCS).  CCS
 * has the following syntax:
 * <br /><code>
 * E, F ::= 0 | &alpha;.E | E\a | E + F | (E | F) | X | &mu;X.E | E[f]
 * </code><br />
 * where <code>&alpha;</code> is a name, co-name or silent (&tau;) action,
 * <code>a</code> is a name and and <code>f</code> is a renaming function
 * on names.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public class CCS
  extends DynamiTEProbeable
  implements Calculus
{

  /**
   * Returns the syntax of CCS as a set of {@link Process} implementations.
   *
   * @return the syntax of CCS.
   */
  public Collection<Class<? extends Process>> getSyntax()
  {
    Set<Class<? extends Process>> syntax = new HashSet<Class<? extends Process>>();
    syntax.add(Nil.class);
    syntax.add(Prefix.class);
    syntax.add(Res.class);
    syntax.add(Sum.class);
    syntax.add(Par.class);
    syntax.add(Var.class);
    syntax.add(Rec.class);
    return syntax;
  }

  /**
   * Returns the name of this plugin.
   */
  public String getName()
  {
    return "CCS";
  }

  /**
   * Returns a transition label with the given text,
   * provided it is not using the reserved &tau; label.
   *
   * @param label the label for the transition.
   * @throws IllegalArgumentException if the name is equal to &tau;.
   */
  public Label getLabel(String name)
  {
    return new CCSLabel(name);
  }

}
