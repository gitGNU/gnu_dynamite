dnl Used by aclocal to generate configure

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
dnl Enable generation of API documentation, with gjdoc if it
dnl has been compiled to an executable (or a suitable script
dnl is in your PATH) or using the argument as gjdoc executable.
dnl -----------------------------------------------------------
AC_DEFUN([CLASSPATH_WITH_GJDOC],
[
  AC_ARG_WITH([gjdoc],
              AS_HELP_STRING([--with-gjdoc],
			     [generate documentation using gjdoc (default is NO)]),
              [if test "x${withval}" = xno; then
	         WITH_GJDOC=no;
	       elif test "x${withval}" = xyes -o "x{withval}" = x; then
	         WITH_GJDOC=yes;
	         AC_PATH_PROG(GJDOC, gjdoc, "no")
		 if test "x${GJDOC}" = xno; then
		   AC_MSG_ERROR("gjdoc executable not found");
		 fi
	       else
	         WITH_GJDOC=yes
		 GJDOC="${withval}"
		 AC_CHECK_FILE(${GJDOC}, AC_SUBST(GJDOC),
		               AC_MSG_ERROR("Cannot use ${withval} as gjdoc executable since it doesn't exist"))
	       fi],
              [WITH_GJDOC=no])

  AM_CONDITIONAL(CREATE_API_DOCS, test "x${WITH_GJDOC}" = xyes)
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
