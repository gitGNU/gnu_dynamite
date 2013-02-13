dnl configure.ac -- Input for autoconf
dnl Taken from GNU Classpath project
dnl Originally GPLv2, upgraded to AGPLv3 via 'or later' clause.
dnl
dnl Copyright (C) 2003, 2004, 2005, 2006, 2007 Free Software Foundation, Inc.
dnl Copyright (C) 2007 The University of Sheffield
dnl Copyright (C) 2013 Andrew John Hughes
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
AC_INIT([DynamiTE],[0.0.0+git],[dynamite-dev@nongnu.org],[dynamite])
AC_CONFIG_SRCDIR(src/uk/ac/shef/dcs/dynamite/Process.java)

dnl Initialise automake
AM_INIT_AUTOMAKE(dynamite, 0.0.0)

dnl Require at least version 2.59
AC_PREREQ(2.59)

dnl Older automake doesn't generate these correctly
abs_top_builddir=`pwd -P`
AC_SUBST(abs_top_builddir)
abs_top_srcdir=`dirname $0`
cd $abs_top_srcdir
abs_top_srcdir=`pwd`
cd $abs_top_builddir
AC_SUBST(abs_top_srcdir)

dnl Search out javac and class libraries
CLASSPATH_FIND_JAVAC

dnl Include specified classpath
CLASSPATH_WITH_CLASSLIB

dnl Require a copy of 'find'
AC_PATH_PROG(FIND, find)

dnl Choose zipping methodology
CLASSPATH_WITH_DYNAMITE

dnl Enable API docs?
AC_MSG_CHECKING([whether to build documentation])
AC_ARG_ENABLE([docs],
	      [AS_HELP_STRING([--disable-docs],
	      		      [Disable generation of documentation])],
	      [ENABLE_DOCS="${enableval}"], [ENABLE_DOCS='yes'])
AC_MSG_RESULT(${ENABLE_DOCS})
if test "x$ENABLE_DOCS" = "xyes"; then
  AM_CONDITIONAL([ENABLE_DOCS], [true])
  IT_FIND_JAVADOC
  DOCS="docs";
  AC_SUBST(DOCS)
else
  AM_CONDITIONAL([ENABLE_DOCS], [false])
fi

dnl Set debug level
DYNAMITE_WITH_DEBUG_LEVEL

dnl Choose whether or not to build examples
DYNAMITE_ENABLE_EXAMPLES

dnl Generate classlist and other output
AC_CONFIG_FILES([Makefile
	lib/Makefile
	docs/Makefile
	docs/api/Makefile
	lib/gen-classlist.sh
	src/uk/ac/shef/dcs/dynamite/Config.java
	examples/Makefile])
AC_CONFIG_COMMANDS([gen-classlist],[chmod 755 lib/gen-classlist.sh])
AC_OUTPUT
