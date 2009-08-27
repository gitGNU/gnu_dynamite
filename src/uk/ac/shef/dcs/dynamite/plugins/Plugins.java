/* Plugins.java - Utility class for dealing with plugin implementations.
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

package uk.ac.shef.dcs.dynamite.plugins;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import uk.ac.shef.dcs.dynamite.Config;

/**
 * Utility class providing methods for working with plugins.
 *
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 */
public final class Plugins
{

  /**
   * Singleton class.
   */
  private Plugins()
  {
  }

  /**
   * Returns an unmodifiable map of plugins, linking {@link Probeable}
   * implementations to their names, as returned from {@link
   * Probeable#getName()}.
   *
   * @param sl the {@link ServiceLoader} used to obtain the plugin
   *           implementations.
   * @return an unmodifiable of names to implementations.
   */
  public static <T extends Probeable> Map<String,T> probePlugins(ServiceLoader<T> sl)
  {
    Map<String,T> map = new HashMap<String,T>();
    for (T probeable : sl)
      {
        Config.logger.config(String.format("Loaded plugin: %s %d.%d.%d%s", probeable.getName(),
                                           probeable.getMajorVersion(), probeable.getMinorVersion(),
                                           probeable.getMicroVersion(), probeable.getAdditionalInfo()));
        map.put(probeable.getName(), probeable);
      }
    return Collections.unmodifiableMap(map);
  }

}