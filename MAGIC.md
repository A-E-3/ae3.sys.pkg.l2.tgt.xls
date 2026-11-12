# MAGIC.md — ae3.sys.pkg.l2.tgt.xls

## A second checkout of this repo holds the uncorrected text (measured 2026-11-04)

`/Volumes/ws-2017/myx-work/source/ae3/ae3.sys.pkg.l2.tgt.xls/` is a checkout of this same repo at the
same `HEAD` (`0ed0cec`). Its `MAGIC.md` is the committed 25-line version, while this one carries an
uncommitted staleness note and is 43 lines. `MAGIC.md` is the only dirty path on this side.

- A correction recorded here and uncommitted does not exist there. Reading that copy reports the
  tier-4 attribution below as live.

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

**Staleness, measured 2026-11-04: the tier-4 half of this finding no longer holds.** The finding
stands as written for 2026-08-26, when the wildcard descriptor was still present. `c86495a`
(2026-10-07) renamed `auto-detect.json` to `xslt-xhtml-detect.json` and dropped its
`extensions: ["*", "auto-detect"]`, so the wildcard keyword now matches no descriptor.
`WebContextXmlAutoDetect` is reached at tier 3 instead, by `xslt-xhtml-detect.json`'s
`contentTypes: ["application/xhtml+xml"]` at `priority: 1.0`. The `X-Debug-Origin:
WebContextXmlAutoDetect` header the probe read is still the header that path emits. Do not rewrite the
tier attribution above; it is true of its own date.
