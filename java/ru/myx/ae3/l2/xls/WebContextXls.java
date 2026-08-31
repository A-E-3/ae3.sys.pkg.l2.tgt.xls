package ru.myx.ae3.l2.xls;

import ru.myx.ae3.answer.Reply;
import ru.myx.ae3.answer.ReplyAnswer;
import ru.myx.ae3.base.BaseObject;
import ru.myx.ae3.i3.TargetInterface;
import ru.myx.ae3.i3.web.WebContext;
import ru.myx.ae3.sapi.FormatSAPI;
import ru.myx.ae3.serve.ServeRequest;

/** The HTTP-reply-producing adapter, same role as ae3.sys.pkg.l2.tgt.pdf's WebContextPdf -
 * registered for the "xls" output-target keyword via this unit's own
 * settings/system/l3/targets/xls.json descriptor (see ae3.sys.pkg.i3.web's
 * WebContextOutputRegistry for how that gets picked up).
 *
 * @author myx */
public class WebContextXls extends XlsTargetContext implements WebContext<XlsTargetContext> {

	ServeRequest query;

	/** @param target
	 * @param query */
	public WebContextXls(final TargetInterface target, final ServeRequest query) {

		super(target);
		this.query = query;
	}

	@Override
	public ServeRequest getQuery() {

		return this.query;
	}

	@Override
	public ReplyAnswer getResultReply() {

		final BaseObject resultLayout = this.getResultLayout();
		if (resultLayout instanceof ReplyAnswer) {
			return (ReplyAnswer) resultLayout;
		}
		assert resultLayout == null : "ResultLayout is: " + FormatSAPI.jsDescribe(this.getResultLayout());

		try {
			return Reply.binary(this.getClass().getSimpleName(), this.query, this.buildWorkbook()) //
					.setAttribute("Content-Type", "application/vnd.ms-excel") //
					.setContentDisposition("attachment");
		} catch (final Exception e) {
			return Reply.string(this.getClass().getSimpleName(), this.query, ru.myx.ae3.help.Format.Throwable.toText(e)) //
					.setAttribute("Content-Type", "text/plain") //
					.setCode(Reply.CD_EXCEPTION);
		}
	}
}
