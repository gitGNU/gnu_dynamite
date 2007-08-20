## Input file for automake to generate the Makefile.in used by configure

# lib first, to compile .class files before native code, last examples
SUBDIRS = lib 
DIST_SUBDIRS = lib 

native: lib

EXTRA_DIST = HACKING BUGS THANKYOU

#dist-hook:
#	-$(mkinstalldirs) $(distdir)/include
#	-cp -f $(top_srcdir)/include/*.in $(distdir)/include
#	-cp -f $(top_srcdir)/include/*.h $(distdir)/include

