TEX=paper.pdf

all: $(TEX)

%.pdf: %.tex literature.bib
	rm -vf $*.bbl
	latex $<
	bibtex $*
	latex $<
	latex $<
	dvips -Ppdf -G0 $*.dvi -o $*.ps
	ps2pdf -dPDFsettings=/prepress -dAutoFilterColorImages=false -dColorImageFilter=/FlateEncode $*.ps $@
	ps2ascii $*.ps| wc -w

clean:
	rm -f *.{aux,blg,dvi,log,out,ps,pdf}
