package ru.myx.ae3.l2.xls;

import ru.myx.ae3.base.BaseObject;
import ru.myx.ae3.l2.ContextHandler;
import ru.myx.ae3.l2.LayoutDefinitionAbstract;

/** Same shape as ae3.sys.pkg.l2.tgt.pdf's PdfLayoutDefinition: onEnter/onLeave/onNest just forward
 * to the context's own defaults - subclasses only implement onExecute().
 *
 * @author myx */
abstract class XlsLayoutDefinition implements LayoutDefinitionAbstract<XlsTargetContext>, ContextHandler<XlsTargetContext, BaseObject> {

	@Override
	public BaseObject onEnter(final XlsTargetContext target, final BaseObject layout) {

		target.dump(this.getClass().getName() + ", onEnter, layout=" + layout.baseGet("layout", BaseObject.UNDEFINED));
		return target.onEnter(target, layout);
	}

	@Override
	public abstract BaseObject onExecute(final XlsTargetContext context, final BaseObject layout);

	@Override
	public void onLeave(final XlsTargetContext target) {

		target.dump(this.getClass().getName() + ", onLeave");
		target.onLeave(target);
	}

	@Override
	public BaseObject onNest(final XlsTargetContext target, final BaseObject layout) {

		target.dump(this.getClass().getName() + ", onNest, layout=" + layout.baseGet("layout", BaseObject.UNDEFINED));
		return target.onNest(target, layout);
	}
}
