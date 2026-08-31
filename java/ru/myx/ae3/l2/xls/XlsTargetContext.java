package ru.myx.ae3.l2.xls;

import java.util.Map;
import java.util.TreeMap;

import ru.myx.ae3.base.BaseArray;
import ru.myx.ae3.base.BaseObject;
import ru.myx.ae3.binary.TransferCopier;
import ru.myx.ae3.i3.TargetInterface;
import ru.myx.ae3.l2.LayoutDefinition;
import ru.myx.ae3.l2.TargetContextAbstract;
import ru.myx.util.xls.ExcelSAPI;
import ru.myx.util.xls.WorkbookCellFormat;
import ru.myx.util.xls.WorkbookChange;
import ru.myx.util.xls.WorkbookSheetChange;

/** AE3 L2 media target for MS Excel (.xls) output, participating in the same
 * Skin.getLayoutDefinition(name)-driven walk (see ae3.sdk's TargetContextAbstract) that
 * WebContextHtml/WebContextPdf/WebContextText already do - unlike the older, disconnected
 * ae3/xls.js script (a standalone ExcelSAPI-based generator never wired into this walk), this
 * lets any page that already produces a generic "grid"/"string"-shaped layout (e.g.
 * ae3.sys.pkg.l2.tgt.xml's ReduceDataViewGrid*Fn.js siblings, or even a plain {layout:"intl",...}
 * from an about.jsld, which the shared skin-failover Intl.jslt already reduces to a bare
 * "string") render as a real workbook with no per-format code on the page side.
 *
 * Deliberately minimal, matching the pdf/html/text precedent's own "string"/"grid" primitives -
 * no per-column type/format inference (dates, currency, hyperlinks, the way ae3/xls.js's
 * formatterDate/Integer/Price/Href do): every cell is written as a plain text label. A real,
 * documented gap, not a hidden one - a natural next step once this base is confirmed working.
 *
 * Simplified from the pdf/html precedent in one way: this class is its own XlsElement root sink
 * (PdfTargetContext instead uses a separate PdfElementDocument for that role) - there's no
 * equivalent "document" object in JExcelAPI's model worth splitting out separately for this.
 *
 * @author myx */
public abstract class XlsTargetContext extends TargetContextAbstract<XlsTargetContext> implements XlsElement {

	private static final Map<String, XlsLayoutDefinition> LAYOUTS;

	static {
		LAYOUTS = new TreeMap<>();
		LAYOUTS.put("string", new XlsLayoutString());
		LAYOUTS.put("grid", new XlsLayoutGrid());
	}

	private final WorkbookCellFormat headerFormat;

	private final WorkbookCellFormat labelFormat;

	private final WorkbookSheetChange sheet;

	private final WorkbookChange workbook;

	/** "whatever XlsTargetContext.current currently points at" - see XlsElement. Starts as
	 * "this" (the root sink) and gets swapped to a container (e.g. XlsElementGrid) and back by
	 * setSequence()/onLeave() while the walk descends into/returns from nested content. */
	XlsElement current;

	/** next free row for a bare top-level element - i.e. one that isn't inside a "grid" */
	private int rootNextY = 0;

	/** @param iface */
	protected XlsTargetContext(final TargetInterface iface) {

		super(iface);
		try {
			this.workbook = ExcelSAPI.createWorkbook();
			this.sheet = this.workbook.createSheet("data");
			this.labelFormat = this.workbook.createCellFormatText();
			this.headerFormat = this.workbook.createCellFormat();
			this.headerFormat.setFont("Arial", 10, true, false, 0);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
		this.current = this;
	}

	@Override
	public void addElement(final XlsTargetContext context, final String text) {

		this.writeCellText(0, this.rootNextY++, text, false);
	}

	/** Only valid after the walk (transform()) has fully completed - see
	 * WebContextXls.getResultReply(). Unlike PDF's streaming PdfWriter, JExcelAPI's
	 * WritableWorkbook builds entirely in memory and only serializes on write()/close()
	 * (wrapped by WorkbookChange.buildWorkbook()), so this must happen exactly once, after the
	 * walk, not from doFinish() (which the walk itself calls, mid-lifecycle).
	 *
	 * @return the built .xls binary
	 * @throws Exception propagated from JExcelAPI's write()/close() */
	TransferCopier buildWorkbook() throws Exception {

		return this.workbook.buildWorkbook();
	}

	@Override
	public XlsElement doFinishAndGetParent(final XlsTargetContext context) {

		return this;
	}

	@Override
	public void doFinish() {

		this.current = null;
	}

	@Override
	protected LayoutDefinition<XlsTargetContext> getLayoutForContext(final String name) {

		return XlsTargetContext.LAYOUTS.get(name);
	}

	/** Atomically reserves the next "count" rows for a container (e.g. XlsLayoutGrid) about to
	 * write into them, returning the first reserved row - keeps a subsequent sibling (a bare
	 * top-level string after a grid, say) from overwriting cells the grid is still going to use.
	 *
	 * @param count how many rows to reserve
	 * @return the first reserved row */
	int reserveRows(final int count) {

		final int start = this.rootNextY;
		this.rootNextY += count;
		return start;
	}

	@Override
	public void onLeave(final XlsTargetContext target) {

		this.current = this.current.doFinishAndGetParent(this);
	}

	BaseObject setSequence(final XlsElement block, final BaseArray array) {

		this.current = block;
		return super.enterSequence(this, array);
	}

	BaseObject setString(final String s) {

		this.current.addElement(this, s);
		return null;
	}

	void writeCellText(final int x, final int y, final String text, final boolean header) {

		try {
			this.sheet.setCellLabel(x, y, text, header ? this.headerFormat : this.labelFormat);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
}
