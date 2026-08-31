package ru.myx.ae3.l2.xls;

import ru.myx.ae3.answer.Reply;
import ru.myx.ae3.answer.ReplyAnswer;
import ru.myx.ae3.serve.ServeRequest;
import ru.myx.sapi.FileSAPI;

/** Same role as ae3.sys.pkg.l2.tgt.pdf's PdfTargetReplyContext: a self-contained target context a
 * script can construct directly with an arbitrary layout and a ServeRequest, sending its own
 * reply once the walk finishes - the class ae3/xls.js's createBinaryFromLayout was always meant
 * to use (it referenced "XlsReplyTargetContext", a name that was never actually built).
 *
 * @author myx */
public class XlsTargetReplyContext extends XlsTargetContext {

	private final ServeRequest query;

	/** @param query
	 * @param pass1 unused - JExcelAPI workbook encryption isn't wired up here; kept only to match
	 *              PdfTargetReplyContext's constructor shape
	 * @param pass2 unused, same as pass1 */
	public XlsTargetReplyContext(final ServeRequest query, final String pass1, final String pass2) {

		super(null);
		this.query = query;
	}

	@Override
	public void doFinish() {

		super.doFinish();
		try {
			final String name = FileSAPI.getFileName(this.query.getResourceIdentifier());
			final ReplyAnswer reply = Reply.binary(
					"L2-XLS", //
					this.query,
					this.buildWorkbook(),
					name.endsWith(".xls")
						? name
						: name + ".xls");
			this.query.getResponseTarget().apply(reply);
		} catch (final RuntimeException e) {
			throw e;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
}
