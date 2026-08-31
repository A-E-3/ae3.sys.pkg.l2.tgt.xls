package ru.myx.ae3.l2.xls;

/** The XLS counterpart to ae3.sys.pkg.l2.tgt.pdf's PdfElementGrid - one real difference from PDF's
 * version is unavoidable, not a simplification: iText's PdfPTable understands column counts
 * natively and wraps rows on its own once cells are added sequentially (see PdfElementGrid,
 * "super(columns)"), so PDF's grid needs no coordinate bookkeeping at all. JExcelAPI has no such
 * concept - every cell is addressed by explicit (x, y) - so this class computes row/column from a
 * running index instead. That is what "map elements to what the library understands" (per the
 * request that led to this file) means concretely for this specific library.
 *
 * @author myx */
final class XlsElementGrid implements XlsElement {

	private final XlsElement parent;

	private final int startX;

	private final int startY;

	private final int width;

	private int index = 0;

	XlsElementGrid(final int width, final int startX, final int startY, final XlsElement parent) {

		this.width = width;
		this.startX = startX;
		this.startY = startY;
		this.parent = parent;
	}

	@Override
	public void addElement(final XlsTargetContext context, final String text) {

		final int x = this.startX + this.index % this.width;
		final int y = this.startY + this.index / this.width;
		context.writeCellText(x, y, text, this.index < this.width);
		++this.index;
	}

	@Override
	public XlsElement doFinishAndGetParent(final XlsTargetContext context) {

		return this.parent;
	}
}
