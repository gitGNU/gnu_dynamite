/* App.java - Example application.
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
package com.fuseyism.dynamite.helloworld;

import uk.ac.shef.dcs.dynamite.Context;
import uk.ac.shef.dcs.dynamite.ContextFactory;
import uk.ac.shef.dcs.dynamite.Process;

import uk.ac.shef.dcs.dynamite.ccs.Coname;
import uk.ac.shef.dcs.dynamite.ccs.Name;
import uk.ac.shef.dcs.dynamite.ccs.Nil;
import uk.ac.shef.dcs.dynamite.ccs.Par;
import uk.ac.shef.dcs.dynamite.ccs.Prefix;
import uk.ac.shef.dcs.dynamite.ccs.Res;

import uk.ac.shef.dcs.dynamite.evolvers.Simulator;

/**
 * A DynamiTE 'Hello World' application which sends
 * a message and receives a message.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public class App
{

  public static void main(String[] args)
    throws Exception
  {
    Context.setContext(ContextFactory.getInstance("CCS", "threaded", "dummy").getContext());
    Process sender = new Prefix(new Betty(),
                                new Prefix(new Coname("sally"), Nil.NIL)); // tau.sally'.0
    Process receiver = new Prefix(new Name("sally"),
                                  new Prefix(new Bob(), Nil.NIL)); // sally.tau.0
    Process app = new Par(sender, receiver); // tau.sally'.0 | sally.tau.0
    new Simulator().evolve(new Res(app, "sally")); // (taul.sally'.0 | sally.tau.0) / sally
  }

}
