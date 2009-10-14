# Makefile.am -- Input for automake
# Taken from GNU Classpath project
# Originally GPLv2, upgraded to AGPLv3 via 'or later' clause.
#
# Copyright (C) 2003, 2004, 2005, 2006, 2007 Free Software Foundation, Inc.
# Copyright (C) 2007 The University of Sheffield
# Copyright (C) 2009 Andrew John Hughes
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

COMPILE_CLASSPATH = $(top_builddir)/lib:.:$(USER_CLASSLIB)
JAR_FILE = examples.jar
JAVA_SRCS = $(abs_top_srcdir)/examples/com/fuseyism/dynamite/helloworld/*.java

# handling source to bytecode compiler programs like gcj, jikes  and kjc
if FOUND_JAVAC
JCOMPILER = $(JAVAC) -classpath $(COMPILE_CLASSPATH) -d . @classes
else
if FOUND_ECJ
JCOMPILER = $(ECJ) -1.5 -classpath $(COMPILE_CLASSPATH) -d . @classes
else
if FOUND_GCJ
JCOMPILER = $(GCJ) --classpath $(COMPILE_CLASSPATH) -C -d . @classes
endif # FOUND_GCJ
endif # FOUND_ECJ
endif # FOUND_JAVAC

if INSTALL_ZIP

pkgdata_DATA = $(JAR_FILE)

endif # INSTALL_ZIP

if INSTALL_CLASS_FILES

noinst_DATA = genclasses compile-classes

install-data-local: genclasses compile-classes
	-$(top_srcdir)/mkinstalldirs $(DESTDIR)$(pkgdatadir)
	cp -R com $(DESTDIR)$(pkgdatadir)

# FIXME - should mimic doc/api/Makefile.am instead...
uninstall-local:
	rm -rf $(DESTDIR)$(pkgdatadir)/com

endif # INSTALL_CLASS_FILES

.PHONY: genclasses

$(JAR_FILE): classes compile-classes
	if test "$(JAR)" != ""; then \
	  rm -f $(JAR_FILE); \
	  $(JAR) cf $(JAR_FILE) com > /dev/null; \
	fi
if FOUND_GCJ
	$(GCJ) -shared -fjni -findirect-dispatch -fPIC -o $(JAR_FILE).so $(JAR_FILE)
endif

classes: genclasses

genclasses:
	echo $(JAVA_SRCS) > classes

$(JAVA_DEPEND): genclasses

compile-classes: classes $(JAVA_SRCS) Makefile
	$(JCOMPILER)
	touch compile-classes

CLEANFILES = compile-classes classes $(JAVA_DEPEND)

clean-local:
	-rm -rf com

dist-hook:
	mkdir -p $(distdir)
	cp -dfR $(top_srcdir)/examples $(top_distdir)
