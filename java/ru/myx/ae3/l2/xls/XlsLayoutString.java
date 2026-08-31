package ru.myx.ae3.l2.xls;

import ru.myx.ae3.base.Base;
import ru.myx.ae3.base.BaseObject;

/** Writes a "string" layout's "value" wherever XlsTargetContext.current currently points - the
 * XLS counterpart to HtmlLayoutString/PdfLayoutString/TextLayoutString. Deliberately minimal
 * (plain text only) - see XlsTargetContext's own header for what's not attempted here yet.
 *
 * @author myx */
final class XlsLayoutString extends XlsLayoutDefinition {

	@Override
	public BaseObject onExecute(final XlsTargetContext context, final BaseObject layout) {

		final String value = Base.getString(layout, "value", "");
		return context.setString(value);
	}
}
