package ru.myx.ae3.l2.xls;

import ru.myx.ae3.base.Base;
import ru.myx.ae3.base.BaseArray;
import ru.myx.ae3.base.BaseObject;

/** Writes a "grid" layout's "elements" as a block of text cells, "width" columns wide - the XLS
 * counterpart to HtmlLayoutGrid/PdfLayoutGrid. See XlsElementGrid's header for why this computes
 * explicit (x, y) coordinates instead of reusing PDF's "just addCell sequentially, let the
 * library auto-wrap rows" trick - JExcelAPI has no equivalent to iText's PdfPTable auto-wrap.
 *
 * @author myx */
final class XlsLayoutGrid extends XlsLayoutDefinition {

	@Override
	public BaseObject onExecute(final XlsTargetContext context, final BaseObject layout) {

		final long width = Base.getLong(layout, "width", 0);
		if (width <= 0 || width > 256) {
			return null;
		}
		final BaseArray array = layout.baseGet("elements", BaseObject.UNDEFINED).baseArray();
		if (array == null) {
			return null;
		}
		final int length = array.length();
		if (length == 0) {
			return null;
		}
		final int rows = (length + (int) width - 1) / (int) width;
		final int startY = context.reserveRows(rows);
		return context.setSequence(new XlsElementGrid((int) width, 0, startY, context.current), array);
	}
}
