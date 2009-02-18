dnl configure.ac -- Input for autoconf
dnl Taken from GNU Classpath project
dnl Originally GPLv2, upgraded to AGPLv3 via 'or later' clause.
dnl
dnl Copyright (C) 2003, 2004, 2005, 2006, 2007 Free Software Foundation, Inc.
dnl Copyright (C) 2007 The University of Sheffield
dnl
dnl This file is part of DynamiTE.
dnl
dnl This program is free software: you can redistribute it and/or modify
dnl it under the terms of the GNU Affero General Public License as published by
dnl the Free Software Foundation, either version 3 of the License, or
dnl (at your option) any later version.
dnl
dnl This program is distributed in the hope that it will be useful,
dnl but WITHOUT ANY WARRANTY; without even the implied warranty of
dnl MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
dnl GNU Affero General Public License for more details.
dnl
dnl You should have received a copy of the GNU Affero General Public License
dnl along with this program.  If not, see <http://www.gnu.org/licenses/>.
dnl
dnl Linking this library statically or dynamically with other modules is
dnl making a combined work based on this library.  Thus, the terms and
dnl conditions of the GNU General Public License cover the whole
dnl combination.

dnl Process this file with autoconf to produce a configure script.

dnl Initialise autoconf
AC_INIT([DynamiTE],[0.00+cvs],[cashew-s-editor@nongnu.org],[cashew-s-editor])
AC_CONFIG_SRCDIR(src/uk/ac/shef/dcs/dynamite/Process.java)

dnl Initialise automake
AM_INIT_AUTOMAKE(dynamite, 0.01)

dnl Require at least version 2.59
AC_PREREQ(2.59)

dnl Search out javac and class libraries
CLASSPATH_FIND_JAVAC

dnl Include specified classpath
CLASSPATH_WITH_CLASSLIB

dnl Require a copy of 'find'
AC_PATH_PROG(FIND, find)

dnl Choose zipping methodology
CLASSPATH_WITH_DYNAMITE

dnl Enable API docs?
CLASSPATH_WITH_GJDOC

dnl Generate classlist and other output
AC_CONFIG_FILES([Makefile
	lib/Makefile
	lib/gen-classlist.sh])
AC_CONFIG_COMMANDS([gen-classlist],[chmod 755 lib/gen-classlist.sh])
AC_OUTPUT
