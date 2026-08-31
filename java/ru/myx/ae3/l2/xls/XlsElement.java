package ru.myx.ae3.l2.xls;

/** The XLS counterpart to ae3.sys.pkg.l2.tgt.pdf's PdfElement - "whatever XlsTargetContext.current
 * currently points at" during the walk. addElement() receives an already-reduced text value (this
 * unit is deliberately text-only for now, see XlsLayoutString); doFinishAndGetParent() is called
 * from XlsTargetContext.onLeave() to pop back to the parent once a container (e.g. a grid) has
 * consumed the whole "elements" array below it.
 *
 * @author myx */
interface XlsElement {

	void addElement(XlsTargetContext context, String text);

	XlsElement doFinishAndGetParent(XlsTargetContext context);
}
