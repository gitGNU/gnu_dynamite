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
