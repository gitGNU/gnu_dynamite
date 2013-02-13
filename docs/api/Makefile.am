# Makefile.am -- Input for automake
# Taken from GNU Classpath project
# Originally GPLv2, upgraded to AGPLv3 via 'or later' clause.
#
# Copyright (C) 2002, 2003, 2004, 2005, 2006, 2008 Free Software Foundation, Inc.
# Copyright (C) 2013 Andrew John Hughes
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

## Input file for automake to generate the Makefile.in used by configure

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
