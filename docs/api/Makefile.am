if ENABLE_DOCS
noinst_DATA = html
endif

sourcepath = $(top_srcdir)/src:$(top_builddir)/src
logobox = "<span class='logo'><a href='https://savannah.nongnu.org/projects/dynamite' target='_top'>DynamiTE</a> ($(VERSION))"

if JAVADOC_SUPPORTS_J_OPTIONS
JAVADOC_MEM_OPTS=-J-Xmx1024m -J-Xms128m -J-XX:PermSize=32m -J-XX:MaxPermSize=160m
endif

JAVADOC_EXTRA_OPTS=

if JAVADOC_SUPPORTS_LICENSETEXT
JAVADOC_EXTRA_OPTS += -licensetext
endif

if JAVADOC_SUPPORTS_VALIDHTML
JAVADOC_EXTRA_OPTS += -validhtml
endif

if ENABLE_DOCS
install-data-local:
	$(mkinstalldirs) $(DESTDIR)$(htmldir)
	(cd html; \
	 for files in $$(find . -type f); \
	 do \
	   ${INSTALL_DATA} -D $${files} $(DESTDIR)$(htmldir)/$${files}; \
	 done)

uninstall-local:
	rm -rf $(DESTDIR)$(htmldir)
endif

html: create_html

clean-local:
	rm -rvf html create_html gjdoc_rawcomment.cache

create_html:
if ENABLE_DOCS
	mkdir html
	$(JAVADOC) \
	$(JAVADOC_MEM_OPTS) \
	$(JAVADOC_EXTRA_OPTS) \
	-use \
	-sourcepath "$(sourcepath)" \
	-encoding UTF-8 \
	-breakiterator \
	-linksource \
	-splitindex \
	-d html \
	-doctitle "DynamiTE $(VERSION)" \
	-windowtitle "DynamiTE $(VERSION) Documentation" \
	-header $(logobox) -footer $(logobox) \
	-subpackages uk
endif
	touch create_html
