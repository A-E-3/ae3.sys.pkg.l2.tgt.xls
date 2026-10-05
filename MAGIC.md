# MAGIC.md — ae3.sys.pkg.l2.tgt.xls

## For keeper-ae3 / magic-tester

**`?___output=xls` never actually reaches `WebContextXls`, 2026-08-26 — found live-verifying
`MakeDataViewReplyFn.js`'s Phase 0 (full detail in `ae3-interfaces.backlog.md`'s own `Context
Facts`).** A real request carrying `?___output=xls` against a real `"grid"` layout returns `200`
with `Content-Type: text/xml` and a generic serialized-object XML dump of the layout — not a real
`.xls` binary, not `application/vnd.ms-excel`. Confirmed via the reply's own
`X-Debug-Origin: WebContextXmlAutoDetect` header that the request fell through all the way to the
framework's tier-4 auto-detect wildcard (`ru.myx.ae3.l2.xml.WebContextXmlAutoDetect`) instead of the
explicit `___output=xls` tier-1 match — meaning `WebContextXls`/`XlsTargetContext`'s own code was
never invoked at all, so nothing in this repo's own already-built rendering path is implicated by
this specific finding.

Root cause traced one level up into `ae3.sys.pkg.i3.web`'s own `WebContextOutputRegistry` (see that
repo's own `MAGIC.md`): its descriptor-loading reducer silently drops a target descriptor (no
logging) on any `Class.forName`/`getConstructor` failure, and `xls.json`'s own descriptor is one
that isn't making it into the live keyword registry — confirmed via a real, isolated local server
(`unit-test/magic-tester/verify-ae3-web-dispatch.test.sh start`), DEBUG-level server logs carrying zero
trace of any xls-related exception. The exact reason `Class.forName("ru.myx.ae3.l2.xls.WebContextXls")`
fails inside the live server (the class, its `.getConstructor(TargetInterface.class,
ServeRequest.class)`, and its own `ae3.pkg.lib.jxl`/`jxl-2.6.12.jar` dependency are all confirmed
present on the classpath used) was not pinned down further without instrumenting already-built
framework code — a stop-and-check-back item, not touched this pass.
