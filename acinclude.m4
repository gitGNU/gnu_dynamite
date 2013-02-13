dnl ainclude.m4 -- Additional macros for autoconf/aclocal
dnl Used by aclocal to generate configure
dnl Taken from GNU Classpath project
dnl Originally GPLv2, upgraded to AGPLv3 via 'or later' clause.
dnl
dnl Copyright (C) 2003, 2004, 2005, 2006, 2007 Free Software Foundation, Inc.
dnl Copyright (C) 2007 The University of Sheffield
dnl Copyright (C) 2009, 2010, 2011 Red Hat, Inc.
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

dnl
dnl -----------------------------------------------------------
dnl Find a java compiler to use
AC_DEFUN([CLASSPATH_FIND_JAVAC],
[
  user_specified_javac=

  CLASSPATH_WITH_GCJ
  CLASSPATH_WITH_ECJ
  CLASSPATH_WITH_JAVAC

  if test "x${user_specified_javac}" = x; then
    AM_CONDITIONAL(FOUND_GCJ, test "x${GCJ}" != x)
    AM_CONDITIONAL(FOUND_ECJ, test "x${ECJ}" != x)
    AM_CONDITIONAL(FOUND_JAVAC, test "x${JAVAC}" != x)
  else
    AM_CONDITIONAL(FOUND_GCJ, test "x${user_specified_javac}" = xgcj)
    AM_CONDITIONAL(FOUND_ECJ, test "x${user_specified_javac}" = xecj)
    AM_CONDITIONAL(FOUND_JAVAC, test "x${user_specified_javac}" = xjavac)
  fi

  if test "x${GCJ}" = x && test "x${ECJ}" = x && test "x${JAVAC}" = x && test "x${user_specified_javac}" != xecj; then
      # FIXME: use autoconf error function
      echo "configure: cannot find javac, try --with-gcj, --with-javac or--with-ecj" 1>&2
      exit 1    
  fi
])

dnl -----------------------------------------------------------
AC_DEFUN([CLASSPATH_WITH_GCJ],
[
  AC_ARG_WITH([gcj],
	      [AS_HELP_STRING(--with-gcj,bytecode compilation with gcj)],
  [
    if test "x${withval}" != x && test "x${withval}" != xyes && test "x${withval}" != xno; then
      CLASSPATH_CHECK_GCJ(${withval})
    else
      if test "x${withval}" != xno; then
        CLASSPATH_CHECK_GCJ
      fi
    fi
    user_specified_javac=gcj
  ],
  [
    CLASSPATH_CHECK_GCJ
  ])
  AC_SUBST(GCJ)
])

dnl -----------------------------------------------------------
AC_DEFUN([CLASSPATH_CHECK_GCJ],
[
  if test "x$1" != x; then
    if test -f "$1"; then
      GCJ="$1"
    else
      AC_PATH_PROG(GCJ, "$1")
    fi
  else
    AC_PATH_PROG(GCJ, "gcj")
  fi  

  if test "x$GCJ" != x; then
    ## GCC version 2 puts out version messages that looked like:
    ##   2.95

    ## GCC version 3 puts out version messages like:
    ##   gcj (GCC) 3.3.3
    ##   Copyright (C) 2003 Free Software Foundation, Inc.
    ##   This is free software; see the source for copying conditions.  There is NO
    ##   warranty; not even for MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
    AC_MSG_CHECKING(gcj version)
    ## Take the output from gcj --version and extract just the version number
    ## into GCJ_VERSION.
    ## (we need to do this to be compatible with both GCC 2 and GCC 3 version
    ##  numbers)
    ## 
    ## First, we get rid of everything before the first number on that line.
    ## Assume that the first number on that line is the start of the
    ## version.
    ##
    ## Second, while we're at it, go ahead and get rid of the first character
    ## that is not part of a version number (i.e., is neither a digit nor
    ## a dot).
    ##
    ## Third, quit, so that we won't process the second and subsequent lines.
    GCJ_VERSION=`$GCJ --version | sed -e 's/^@<:@^0-9@:>@*//' -e 's/@<:@^.0-9@:>@@<:@^.0-9@:>@*//' -e 'q'` 
    GCJ_VERSION_MAJOR=`echo "$GCJ_VERSION" | cut -d '.' -f 1`
    GCJ_VERSION_MINOR=`echo "$GCJ_VERSION" | cut -d '.' -f 2`

    if expr "$GCJ_VERSION_MAJOR" \< 4 > /dev/null; then
      GCJ=""
    fi
    if expr "$GCJ_VERSION_MAJOR" = 4 > /dev/null; then
      if expr "$GCJ_VERSION_MINOR" \< 0; then
        GCJ=""
      fi
    fi
    if test "x$GCJ" != x; then
      AC_MSG_RESULT($GCJ_VERSION)
    else
      AC_MSG_WARN($GCJ_VERSION: gcj 4.0 or higher required)
    fi
  fi 
])


dnl -----------------------------------------------------------
AC_DEFUN([CLASSPATH_WITH_JAVAH],
[
  AC_ARG_WITH([javah],
	      [AS_HELP_STRING(--with-javah,specify path or name of a javah-like program)],
  [
    if test "x${withval}" != x && test "x${withval}" != xyes && test "x${withval}" != xno; then
      CLASSPATH_CHECK_JAVAH(${withval})
    else
      CLASSPATH_CHECK_JAVAH
    fi
  ],
  [ 
    CLASSPATH_CHECK_JAVAH
  ])
  AM_CONDITIONAL(USER_SPECIFIED_JAVAH, test "x${USER_JAVAH}" != x)
  AC_SUBST(USER_JAVAH)
])

dnl -----------------------------------------------------------
dnl Checking for a javah like program 
dnl -----------------------------------------------------------
AC_DEFUN([CLASSPATH_CHECK_JAVAH],
[
  if test "x$1" != x; then
    if test -f "$1"; then
      USER_JAVAH="$1"
    else
      AC_PATH_PROG(USER_JAVAH, "$1")
    fi
  else
    for javah_name in gcjh javah; do
      AC_PATH_PROG(USER_JAVAH, "$javah_name")
      if test "x${USER_JAVAH}" != x; then
        break
      fi
    done
  fi
  
#  if test "x${USER_JAVAH}" = x; then
#    echo "configure: cannot find javah" 1>&2
#    exit 1
#  fi
])

dnl -----------------------------------------------------------
dnl CLASSPATH_WITH_CLASSLIB - checks for user specified classpath additions
dnl -----------------------------------------------------------
AC_DEFUN([CLASSPATH_WITH_CLASSLIB],
[
  AC_ARG_WITH([classpath],
	      [AS_HELP_STRING(--with-classpath,specify path to a classes.zip like file)],
  [
    if test "x${withval}" = xyes; then
      # set user classpath to CLASSPATH from env
      AC_MSG_CHECKING(for classlib)
      USER_CLASSLIB=${CLASSPATH}
      AC_SUBST(USER_CLASSLIB)
      AC_MSG_RESULT(${USER_CLASSLIB})
      conditional_with_classlib=true      
    elif test "x${withval}" != x && test "x${withval}" != xno; then
      # set user classpath to specified value
      AC_MSG_CHECKING(for classlib)
      USER_CLASSLIB=${withval}
      AC_SUBST(USER_CLASSLIB)
      AC_MSG_RESULT(${withval})
      conditional_with_classlib=true
    fi
  ],
  [ conditional_with_classlib=false ])
  AM_CONDITIONAL(USER_SPECIFIED_CLASSLIB, test "x${conditional_with_classlib}" = xtrue)
])

dnl -----------------------------------------------------------
dnl CLASSPATH_WITH_DYNAMITE - specify what to install
dnl -----------------------------------------------------------
AC_DEFUN([CLASSPATH_WITH_DYNAMITE],
[
  AC_ARG_WITH([glibj],
              [AS_HELP_STRING([--with-dynamite],[define what to install (zip|flat|both|none) [default=zip]])],
              [
                if test "x${withval}" = xyes || test "x${withval}" = xzip; then
      		  AC_PATH_PROG(JAR, jar)
		  install_class_files=no
		elif test "x${withval}" = xboth; then
		  AC_PATH_PROG(JAR, jar)
		  install_class_files=yes
		elif test "x${withval}" = xflat; then
		  JAR=
		  install_class_files=yes
                elif test "x${withval}" = xno || test "x${withval}" = xnone; then
                  JAR=
		  install_class_files=no
                else
		  AC_MSG_ERROR([unknown value given to --with-glibj])
                fi
	      ],
  	      [
		AC_PATH_PROG(JAR, jar)
		install_class_files=no
	      ])
  AM_CONDITIONAL(INSTALL_ZIP, test "x${JAR}" != x)
  AM_CONDITIONAL(INSTALL_CLASS_FILES, test "x${install_class_files}" = xyes)

  AC_ARG_ENABLE([examples],
		[AS_HELP_STRING(--enable-examples,enable build of the examples [default=yes])],
		[case "${enableval}" in
		  yes) EXAMPLESDIR="examples" ;;
		  no) EXAMPLESDIR="" ;;
		  *) AC_MSG_ERROR(bad value ${enableval} for --enable-examples) ;;
		esac],
		[EXAMPLESDIR="examples"])
  if test "x${JAR}" = x && test "x${install_class_files}" = xno; then
    EXAMPLESDIR=""
  fi
  AC_SUBST(EXAMPLESDIR)
])

dnl -----------------------------------------------------------
AC_DEFUN([CLASSPATH_WITH_ECJ],
[
  AC_ARG_WITH([ecj],
	      [AS_HELP_STRING(--with-ecj,bytecode compilation with ecj)],
  [
    if test "x${withval}" != x && test "x${withval}" != xyes && test "x${withval}" != xno; then
      CLASSPATH_CHECK_ECJ(${withval})
    else
      if test "x${withval}" != xno; then
        CLASSPATH_CHECK_ECJ
      fi
    fi
    user_specified_javac=ecj
  ],
  [ 
    CLASSPATH_CHECK_ECJ
  ])
  AC_SUBST(ECJ)
])

dnl -----------------------------------------------------------
AC_DEFUN([CLASSPATH_CHECK_ECJ],
[
  if test "x$1" != x; then
    if test -f "$1"; then
      ECJ="$1"
    else
      AC_PATH_PROG(ECJ, "$1")
    fi
  else
    AC_PATH_PROG(ECJ, "ecj")
  fi
])


dnl -----------------------------------------------------------
AC_DEFUN([CLASSPATH_WITH_JAVAC],
[
  AC_ARG_WITH([javac],
	      [AS_HELP_STRING(--with-javac,bytecode compilation with javac)],
  [
    if test "x${withval}" != x && test "x${withval}" != xyes && test "x${withval}" != xno; then
      CLASSPATH_CHECK_JAVAC(${withval})
    else
      if test "x${withval}" != xno; then
        CLASSPATH_CHECK_JAVAC
      fi
    fi
    user_specified_javac=javac
  ],
  [ 
    CLASSPATH_CHECK_JAVAC
  ])
  AC_SUBST(JAVAC)
])

dnl -----------------------------------------------------------
AC_DEFUN([CLASSPATH_CHECK_JAVAC],
[
  if test "x$1" != x; then
    JAVAC="$1"
  else
    AC_PATH_PROG(JAVAC, "javac")
  fi
])

AC_DEFUN([DYNAMITE_WITH_DEBUG_LEVEL],
[
  AC_MSG_CHECKING(which debugging level to use)
  AC_ARG_WITH([debug-level],
	      [AS_HELP_STRING(--with-debug-level,output level for messages (SEVERE,WARNING,INFO,CONFIG) (default=WARNING))],
  [
    if test "x${withval}" != x && test "x${withval}" != xyes && test "x${withval}" != xno; then
       if test "x${withval}" != "xSEVERE" && test "x${withval}" != "xWARNING" &&
          test "x${withval}" != "xINFO" && test "x${withval}" != "xCONFIG" &&
	  test "x${withval}" != "xFINE" && test "x${withval}" != "xFINER" &&
	  test "x${withval}" != "xFINEST"; then
	       AC_MSG_ERROR([Invalid debug level: ${withval}]);
       else
	       DEBUG_LEVEL=${withval};
       fi
    else
       DEBUG_LEVEL="WARNING";
    fi
  ],
  [ 
    DEBUG_LEVEL="WARNING";
  ])
  AC_MSG_RESULT([$DEBUG_LEVEL]);
  AC_SUBST(DEBUG_LEVEL)
])

AC_DEFUN([DYNAMITE_ENABLE_EXAMPLES],
[
  AC_MSG_CHECKING([whether to disable the examples])
  AC_ARG_ENABLE([examples],
                [AS_HELP_STRING(--disable-examples,don't build the examples [[default=no]])],
  [
    case "${enableval}" in
      no)
        disable_examples=yes
        ;;
      *)
        disable_examples=no
        ;;
    esac
  ],
  [
    disable_examples=no
  ])
  AC_MSG_RESULT([$disable_examples])
  if test "x${disable_examples}" = "xno";  then
    EXAMPLES="examples";
  fi
  AC_SUBST(EXAMPLES)
])

dnl Taken & modified from IcedTea7 2.4.0.
AC_DEFUN([IT_CHECK_FOR_JDK],
[
  AC_MSG_CHECKING([for a JDK home directory])
  AC_ARG_WITH([jdk-home],
	      [AS_HELP_STRING([--with-jdk-home[[=PATH]]],
                              [jdk home directory (default is first predefined JDK found)])],
              [
                if test "x${withval}" = xyes
                then
                  SYSTEM_JDK_DIR=
                elif test "x${withval}" = xno
                then
	          SYSTEM_JDK_DIR=
	        else
                  SYSTEM_JDK_DIR=${withval}
                fi
              ],
              [
	        SYSTEM_JDK_DIR=
              ])
  if test -z "${SYSTEM_JDK_DIR}"; then
    BOOTSTRAP_VMS="/usr/lib/jvm/java-gcj /usr/lib/jvm/gcj-jdk /usr/lib/jvm/cacao";
    ICEDTEA6_VMS="/usr/lib/jvm/icedtea6 /usr/lib/jvm/java-6-openjdk"
    ICEDTEA7_VMS="/usr/lib/jvm/icedtea7 /usr/lib/jvm/java-1.7.0-openjdk"
    FALLBACK_VMS="/usr/lib/jvm/java-openjdk /usr/lib/jvm/openjdk /usr/lib/jvm/java-icedtea"
    for dir in ${BOOTSTRAP_VMS} ${ICEDTEA7_VMS} ${ICEDTEA6_VMS} ${FALLBACK_VMS} ; do
       if test -d $dir; then
         SYSTEM_JDK_DIR=$dir
	 break
       fi
    done
  fi
  AC_MSG_RESULT(${SYSTEM_JDK_DIR})
  if ! test -d "${SYSTEM_JDK_DIR}"; then
    AC_MSG_ERROR("A JDK JDK home directory could not be found.")
  fi
  AC_SUBST(SYSTEM_JDK_DIR)
])

dnl Taken from IcedTea-Web
AC_DEFUN([IT_FIND_JAVADOC],
[
  AC_REQUIRE([IT_CHECK_FOR_JDK])
  JAVADOC_DEFAULT=${SYSTEM_JDK_DIR}/bin/javadoc
  AC_MSG_CHECKING([if a javadoc executable is specified])
  AC_ARG_WITH([javadoc],
              [AS_HELP_STRING(--with-javadoc[[=PATH]],specify location of Java documentation tool (javadoc))],
  [
    if test "x${withval}" = "xyes"; then
      JAVADOC=no
    else
      JAVADOC="${withval}"
     fi
  ],
  [
    JAVADOC=no
  ])
  AC_MSG_RESULT([${JAVADOC}])
  if test "x${JAVADOC}" == "xno"; then
    JAVADOC=${JAVADOC_DEFAULT}
  fi
  AC_MSG_CHECKING([if $JAVADOC is a valid executable file])
  if test -x "${JAVADOC}" && test -f "${JAVADOC}"; then
    AC_MSG_RESULT([yes])
  else
    AC_MSG_RESULT([no])
    JAVADOC=""
    AC_PATH_PROG(JAVADOC, "javadoc")
    if test -z "${JAVADOC}"; then
      AC_PATH_PROG(JAVADOC, "gjdoc")
    fi
    if test -z "${JAVADOC}" && test "x$ENABLE_DOCS" = "xyes"; then
      AC_MSG_ERROR("No Java documentation tool was found.")
    fi
  fi
  AC_MSG_CHECKING([whether javadoc supports -J options])
  CLASS=pkg/Test.java
  mkdir tmp.$$
  cd tmp.$$
  mkdir pkg
  cat << \EOF > $CLASS
[/* [#]line __oline__ "configure" */
package pkg;

public class Test
{
  /**
   * Does stuff.
   *
   *
   * @param args arguments from cli.
   */
  public static void main(String[] args)
  {
    System.out.println("Hello World!");
  }
}
]
EOF
  if $JAVADOC -J-Xmx896m pkg >&AS_MESSAGE_LOG_FD 2>&1; then
    JAVADOC_KNOWS_J_OPTIONS=yes
  else
    JAVADOC_KNOWS_J_OPTIONS=no
  fi
  AC_MSG_RESULT([${JAVADOC_KNOWS_J_OPTIONS}])
  AC_MSG_CHECKING([whether javadoc supports -licensetext])
  if $JAVADOC -licensetext pkg >&AS_MESSAGE_LOG_FD 2>&1; then
    JAVADOC_KNOWS_LICENSETEXT=yes
  else
    JAVADOC_KNOWS_LICENSETEXT=no
  fi
  AC_MSG_RESULT([${JAVADOC_KNOWS_LICENSETEXT}])
  AC_MSG_CHECKING([whether javadoc supports -validhtml])
  if $JAVADOC -validhtml pkg >&AS_MESSAGE_LOG_FD 2>&1; then
    JAVADOC_KNOWS_VALIDHTML=yes
  else
    JAVADOC_KNOWS_VALIDHTML=no
  fi
  AC_MSG_RESULT([${JAVADOC_KNOWS_VALIDHTML}])
  cd ..
  rm -rf tmp.$$
  AC_SUBST(JAVADOC)
  AC_SUBST(JAVADOC_KNOWS_J_OPTIONS)
  AC_SUBST(JAVADOC_KNOWS_LICENSETEXT)
  AC_SUBST(JAVADOC_KNOWS_VALIDHTML)
  AM_CONDITIONAL([JAVADOC_SUPPORTS_J_OPTIONS], test x"${JAVADOC_KNOWS_J_OPTIONS}" = "xyes")
  AM_CONDITIONAL([JAVADOC_SUPPORTS_LICENSETEXT], test x"${JAVADOC_KNOWS_LICENSETEXT}" = "xyes")
  AM_CONDITIONAL([JAVADOC_SUPPORTS_VALIDHTML], test x"${JAVADOC_KNOWS_VALIDHTML}" = "xyes")
])
