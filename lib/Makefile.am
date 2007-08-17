JAVA_DEPEND = java.dep

## silently try to include these, if it fails gnu make
## will remake these 'makefiles' with the rules given in
## this file and restart the make process again
sinclude $(JAVA_DEPEND)

#propertydirs :=  $(shell cd $(top_srcdir)/resource && $(FIND) nongnu -type d ! -name CVS -print)
#propertyfiles :=  $(shell cd $(top_srcdir)/resource && $(FIND) nongnu -name \*\.properties -print)

if FOUND_ECLIPSE
	RUNTIME=`echo $(ECLIPSE)/plugins/org.eclipse.core.runtime*/runtime.jar`
	UI=`echo $(ECLIPSE)/plugins/org.eclipse.ui.workbench*/workbench.jar`
	OSGI=`echo $(ECLIPSE)/plugins/org.eclipse.osgi*/osgi.jar`
	JFACE=`echo $(ECLIPSE)/plugins/org.eclipse.jface*/jface.jar`
	GEF=`echo $(ECLIPSE)/plugins/org.eclipse.gef*/gef.jar`
	SWT=`find $(ECLIPSE) -name 'swt.jar'`
	VIEWS=`find $(ECLIPSE) -name 'views.jar'`
	DRAW2D=`find $(ECLIPSE) -name 'draw2d.jar'`
	RESOURCES=`find $(ECLIPSE) -name 'resources.jar'`
	IDE=`find $(ECLIPSE) -name 'ide.jar'`
	INSTALLDIR=$(ECLIPSE)/plugins/nongnu.cashews.editor.composer_$(VERSION)

	compile_classpath = $(top_srcdir):.:$(RUNTIME):$(UI):$(OSGI):$(JFACE):$(GEF):$(SWT):$(VIEWS):$(DRAW2D):$(RESOURCES):$(IDE):$(USER_CLASSLIB)
else
	compile_classpath = $(top_srcdir):.:$(USER_CLASSLIB)
endif

# handling source to bytecode compiler programs like gcj, jikes  and kjc
if FOUND_ECJ
JAVAC = $(ECJ) -1.5 -classpath $(compile_classpath) -d . @classes
else
if FOUND_GCJ
JAVAC = $(GCJ) --classpath $(compile_classpath) -C -d . @classes
else
if FOUND_JIKES
JAVAC = $(JIKES) +F -extdirs '' -sourcepath '' --classpath $(compile_classpath) -d . @classes
else
if FOUND_KJC
JAVAC = $(KJC) -classpath .:$(USER_CLASSLIB) -d . @classes
else
if FOUND_GCJX
JAVAC = $(GCJX) -classpath .:$(USER_CLASSLIB) -d . @classes
endif # FOUND_GCJX
endif # FOUND_KJC
endif # FOUND_GCJ
endif # FOUND_JIKES
endif # FOUND_ECJ

JAVAH = $(USER_JAVAH) -jni -classpath .:$(USER_CLASSLIB)

if INSTALL_GLIBJ_ZIP

if FOUND_ECLIPSE
pkgdata_DATA = commons.jar xml.jar rdf.jar owls.jar cashews.jar services.jar soap.jar wsdl.jar eclipse.jar
install-data-local: genclasses compile-classes eclipse.jar
	if [ ! -e $(INSTALLDIR) ]; then mkdir $(INSTALLDIR); fi
	cp eclipse.jar $(INSTALLDIR)
	cp $(top_srcdir)/resource/plugin.xml $(INSTALLDIR)
else
pkgdata_DATA = commons.jar xml.jar rdf.jar cashews.jar owls.jar soap.jar wsdl.jar services.jar
endif

endif # INSTALL_GLIBJ_ZIP

if INSTALL_CLASS_FILES

noinst_DATA = genclasses compile-classes # resources

install-data-local: genclasses compile-classes
	-$(top_srcdir)/mkinstalldirs $(DESTDIR)$(pkgdatadir)
	cp -R nongnu $(DESTDIR)$(pkgdatadir)

# FIXME - should mimic doc/api/Makefile.am instead...
uninstall-local:
	rm -rf $(DESTDIR)$(pkgdatadir)/nongnu

endif # INSTALL_CLASS_FILES

.PHONY: genclasses

commons.jar: classes compile-classes # resources
	if test "$(JAR)" != ""; then rm commons.jar; $(JAR) cf commons.jar `find nongnu -path '*commons\/*' -name '*class'` > /dev/null; fi
if FOUND_GCJ
$(GCJ) -shared -fjni -findirect-dispatch -o commons.jar.so commons.jar
endif

xml.jar: classes compile-classes # resources
	if test "$(JAR)" != ""; then rm xml.jar; $(JAR) cf xml.jar `find nongnu -path '*xml\/*' -name '*class'` > /dev/null; fi
if FOUND_GCJ
$(GCJ) -shared -fjni -findirect-dispatch -o xml.jar.so xml.jar
endif

rdf.jar: classes compile-classes # resources
	if test "$(JAR)" != ""; then rm rdf.jar; $(JAR) cf rdf.jar `find nongnu -path '*rdf*' -name '*class'` > /dev/null; fi
if FOUND_GCJ
$(GCJ) -shared -fjni -findirect-dispatch -o rdf.jar.so rdf.jar
endif

owls.jar: classes compile-classes # resources
	if test "$(JAR)" != ""; then rm owls.jar; $(JAR) cf owls.jar `find nongnu -path '*owls*' -name '*class'` > /dev/null; fi
if FOUND_GCJ
$(GCJ) -shared -fjni -findirect-dispatch -o owls.jar.so owls.jar
endif

cashews.jar: classes compile-classes # resources
	if test "$(JAR)" != ""; then rm cashews.jar; $(JAR) cf cashews.jar `find nongnu -path '*language*' -name '*class'` > /dev/null; fi
if FOUND_GCJ
$(GCJ) -shared -fjni -findirect-dispatch -o cashews.jar.so cashews.jar
endif

services.jar: classes compile-classes # resources
	if test "$(JAR)" != ""; then rm services.jar; $(JAR) cf services.jar `find nongnu -path '*services*' -name '*class'` > /dev/null; fi
if FOUND_GCJ
$(GCJ) -shared -fjni -findirect-dispatch -o services.jar.so services.jar
endif

soap.jar: classes compile-classes # resources
	if test "$(JAR)" != ""; then rm soap.jar; $(JAR) cf soap.jar `find nongnu -path '*soap*' -name '*class'` > /dev/null; fi
if FOUND_GCJ
$(GCJ) -shared -fjni -findirect-dispatch -o soap.jar.so soap.jar
endif

wsdl.jar: classes compile-classes # resources
	if test "$(JAR)" != ""; then rm wsdl.jar; $(JAR) cf wsdl.jar `find nongnu -path '*wsdl*' -name '*class'` > /dev/null; fi
if FOUND_GCJ
$(GCJ) -shared -fjni -findirect-dispatch -o wsdl.jar.so wsdl.jar
endif

if FOUND_ECLIPSE

eclipse.jar: classes compile-classes # resources
	if test "$(JAR)" != ""; then rm eclipse.jar; $(JAR) cf eclipse.jar `find nongnu -path '*eclipse*' -name '*class'` > /dev/null; fi
if FOUND_GCJ
$(GCJ) -shared -fjni -findirect-dispatch -o eclipse.jar.so eclipse.jar
endif

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
	$(JAVAC)
	touch compile-classes

EXTRA_DIST = standard.omit 
CLEANFILES = compile-classes resources classes \
	cashews.jar classes.1 \
	$(JAVA_DEPEND)

clean-local:
	-rm -rf nongnu

dist-hook:
	mkdir -p $(distdir)
	cp -dfR $(top_srcdir)/src $(top_distdir)
# Delete not wanted files.
	$(FIND) $(top_distdir) -name CVS -print | xargs rm -fr
	$(FIND) $(top_distdir) -name .cvsignore -print | xargs rm -f
	$(FIND) $(top_distdir) -name class-dependencies.conf -print | xargs rm -f
	rm -f $(top_distdir)/src/nongnu/cashews/commons/Configuration.java
# Omit files listed in standard.omit.
	( cd $(top_distdir)/lib ; cat $(top_builddir)/lib/standard.omit | sed -e 's/\$$//' | sed -e 's/\/\.\*//' | xargs rm -fr )

