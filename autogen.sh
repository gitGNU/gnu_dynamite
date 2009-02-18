#!/bin/sh

# autogen.sh -- for autogenerating autotools build infrastructure
# Taken from GNU Classpath project
# Originally GPLv2, upgraded to AGPLv3 via 'or later' clause.
#
# Copyright (C) 2004, 2005, 2006, 2007 Free Software Foundation, Inc.
# Copyright (C) 2007 The University of Sheffield
#
# This file is part of DynamiTE.
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU Affero General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Affero General Public License for more details.
#
# You should have received a copy of the GNU Affero General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.
#
# Linking this library statically or dynamically with other modules is
# making a combined work based on this library.  Thus, the terms and
# conditions of the GNU General Public License cover the whole
# combination.

# Run this to generate all the initial makefiles, etc.

srcdir=`dirname $0`
test -z "$srcdir" && srcdir=.

ORIGDIR=`pwd`
cd $srcdir
PROJECT=DynamiTE
TEST_TYPE=-f
FILE=src/uk/ac/shef/dcs/dynamite/Process.java

DIE=0

have_autoconf=false
if autoconf --version < /dev/null > /dev/null 2>&1 ; then
	autoconf_version=`autoconf --version | sed 's/^[^0-9]*\([0-9.][0-9.]*\).*/\1/'`
	case $autoconf_version in
	    2.59*|2.6*)
		have_autoconf=true
		;;
	esac
fi
if $have_autoconf ; then : ; else
	echo
	echo "You must have autoconf 2.59 installed to compile $PROJECT."
	echo "Install the appropriate package for your distribution,"
	echo "or get the source tarball at http://ftp.gnu.org/gnu/autoconf/"
	DIE=1
fi

have_automake=false
# We know each 1.9.x version works
if automake-1.9 --version < /dev/null > /dev/null 2>&1 ; then
	AUTOMAKE=automake-1.9
	ACLOCAL=aclocal-1.9
	have_automake=true
elif automake --version < /dev/null > /dev/null 2>&1 ; then
	AUTOMAKE=automake
	ACLOCAL=aclocal
	automake_version=`automake --version | sed 's/^[^0-9]*\([0-9.][0-9.]*\).*/\1/'`
	case $automake_version in
	    1.9*)
		have_automake=true
		;;
	esac
fi
if $have_automake ; then : ; else
	echo
	echo "You must have automake 1.9 installed to compile $PROJECT."
	echo "Install the appropriate package for your distribution,"
	echo "or get the source tarball at http://ftp.gnu.org/gnu/automake/"
	DIE=1
fi

if test "$DIE" -eq 1; then
	exit 1
fi

test $TEST_TYPE $FILE || {
	echo "You must run this script in the top-level $PROJECT directory"
	exit 1
}

if test "x$AUTOGEN_SUBDIR_MODE" = "xyes"; then
        if test -z "$*"; then
                echo "I am going to run ./configure with no arguments - if you wish "
                echo "to pass any to it, please specify them on the $0 command line."
        fi
fi

if test -z "$ACLOCAL_FLAGS"; then

	acdir=`$ACLOCAL --print-ac-dir`
        m4list="glib-2.0.m4 glib-gettext.m4"

	for file in $m4list
	do
		if [ ! -f "$acdir/$file" ]; then
			echo "WARNING: aclocal's directory is $acdir, but..."
			echo "         no file $acdir/$file"
			echo "         You may see fatal macro warnings below."
			echo "         If these files are installed in /some/dir, set the ACLOCAL_FLAGS "
			echo "         environment variable to \"-I /some/dir\", or install"
			echo "         $acdir/$file."
			echo ""
		fi
	done
fi

# Use the "-I ." flag in order to include our pkg.m4.  
$ACLOCAL -I . $ACLOCAL_FLAGS || exit $?

$AUTOMAKE --add-missing || exit $?
autoconf || exit $?
cd $ORIGDIR || exit $?

if test "x$AUTOGEN_SUBDIR_MODE" = "xyes"; then
        $srcdir/configure --enable-maintainer-mode $AUTOGEN_CONFIGURE_ARGS "$@" || exit $?

        echo 
        echo "Now type 'make' to compile $PROJECT."
fi
