# Makefile.am -- Input for automake
# Taken from GNU Classpath project
# Originally GPLv2, upgraded to AGPLv3 via 'or later' clause.
#
# Copyright (C) 2003, 2004, 2005, 2006, 2007 Free Software Foundation, Inc.
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

JAVA_DEPEND = java.dep

## silently try to include these, if it fails gnu make
## will remake these 'makefiles' with the rules given in
## this file and restart the make process again
sinclude $(JAVA_DEPEND)

#propertydirs :=  $(shell cd $(top_srcdir)/resource && $(FIND) nongnu -type d ! -name CVS -print)
#propertyfiles :=  $(shell cd $(top_srcdir)/resource && $(FIND) nongnu -name \*\.properties -print)

compile_classpath = $(top_srcdir):.:$(USER_CLASSLIB)

# handling source to bytecode compiler programs like gcj, jikes  and kjc
if FOUND_JAVAC
JCOMPILER = $(JAVAC) -classpath $(compile_classpath) -d . @classes
else
if FOUND_ECJ
JCOMPILER = $(ECJ) -1.5 -classpath $(compile_classpath) -d . @classes
else
if FOUND_GCJ
JCOMPILER = $(GCJ) --classpath $(compile_classpath) -C -d . @classes
endif # FOUND_GCJ
endif # FOUND_ECJ
endif # FOUND_JAVAC

JAVAH = $(USER_JAVAH) -jni -classpath .:$(USER_CLASSLIB)

if INSTALL_ZIP

install-data-local: genclasses compile-classes eclipse.jar
	if [ ! -e $(INSTALLDIR) ]; then mkdir $(INSTALLDIR); fi
	cp eclipse.jar $(INSTALLDIR)
	cp $(top_srcdir)/resource/plugin.xml $(INSTALLDIR)

pkgdata_DATA = dynamite.jar

endif # INSTALL_ZIP

if INSTALL_CLASS_FILES

noinst_DATA = genclasses compile-classes # resources

install-data-local: genclasses compile-classes
	-$(top_srcdir)/mkinstalldirs $(DESTDIR)$(pkgdatadir)
	cp -R uk $(DESTDIR)$(pkgdatadir)

# FIXME - should mimic doc/api/Makefile.am instead...
uninstall-local:
	rm -rf $(DESTDIR)$(pkgdatadir)/uk

endif # INSTALL_CLASS_FILES

.PHONY: genclasses

dynamite.jar: classes compile-classes # resources
	if test "$(JAR)" != ""; then rm dynamite.jar; $(JAR) cf dynamite.jar `find uk -name '*class'` > /dev/null; fi
if FOUND_GCJ
	$(GCJ) -shared -fjni -findirect-dispatch -o dynamite.jar.so dynamite.jar
endif

#resources:
#	if ! [ -e gnu ]; then mkdir gnu; fi
#	if ! [ -e gnu/java ]; then mkdir gnu/java; fi
#	if ! [ -e gnu/java/locale ]; then mkdir gnu/java/locale; fi
#	@list='$(propertydirs)'; for p in $$list; do \
#	  if ! [ -e $$p ]; then mkdir $$p; fi; \
#	done
#	@list='$(propertyfiles)'; for p in $$list; do \
#	  cp $(top_srcdir)/resource/$$p $$p; \
#	done
#	touch resources

classes: genclasses

genclasses: gen-classlist.sh standard.omit 
	top_builddir=$(top_builddir) $(SHELL) ./gen-classlist.sh standard

$(JAVA_DEPEND): genclasses

compile-classes: classes $(JAVA_SRCS) Makefile
	$(JCOMPILER)
	touch compile-classes

EXTRA_DIST = standard.omit 
CLEANFILES = compile-classes resources classes \
	cashews.jar classes.1 \
	$(JAVA_DEPEND)

clean-local:
	-rm -rf uk

dist-hook:
	mkdir -p $(distdir)
	cp -dfR $(top_srcdir)/src $(top_distdir)
# Delete not wanted files.
	$(FIND) $(top_distdir) -name CVS -print | xargs rm -fr
	$(FIND) $(top_distdir) -name .cvsignore -print | xargs rm -f
	$(FIND) $(top_distdir) -name class-dependencies.conf -print | xargs rm -f
#	rm -f $(top_distdir)/src/nongnu/cashews/commons/Configuration.java
# Omit files listed in standard.omit.
	( cd $(top_distdir)/lib ; cat $(top_builddir)/lib/standard.omit | sed -e 's/\$$//' | sed -e 's/\/\.\*//' | xargs rm -fr )

