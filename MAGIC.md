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

## This project test entry opens its output on the desktop (measured 2026-11-18)


`TestXls.java:33` calls `Engine.createProcess( file.getName(), null, file.getParentFile() )` unconditionally. The temp file is made at `:23` and `XlsFileTargetContext` renders into it at `:31`.

On macOS that method's own `:326` arm runs `/usr/bin/open`, so one window opens on the human-owner's desktop per run. No output of the run shows it. The handler follows the file's own extension, and this entry names its temp file `xmltest-` with the suffix `.xml`, so what opens is the XML viewer rather than a spreadsheet application. The same two strings give the entry its stdout line, which reads "LayoutEngine2 XML renderer test" for a workbook.

**Do not run this entry as it stands.** Render through a sandbox copy with line 33 removed, or read the written file directly. Change only the sandbox copy.

The two mismatched strings are filed as a defect and not fixed here: `task-20261118T2106Z-testxls-writes-a-workbook-into-an-xml-suffix`, filed by `magic-coordinator` at 21:04 +0200, with `magic-developer` on the language decision.

The file is md5 `fb79f858b4f4e31195afce07d13e85a2` in `/Volumes/workspace/myx` and in `/Volumes/ws-2017/myx-work/source/ae3`. Re-read at 21:15 +0200: `:23` makes the `.xml` temp file, `:24` prints the XML test line, `:31` renders into it, `:33` opens it.

The cross-cutting measurement, over all five render-target entries, is in `ae3.sys.pkg.l2.tgt.html/MAGIC.md`.

## xls.js exports makeDataViewReply and the data-view reduction, measured 2026-11-25

`ae3-packages/ae3.sys.l2.tgt.xls/resources/lib/ae3/xls.js` now carries `reduceDataViewGridXls` and `makeDataViewReply(query, layout)`, exported beside the table and form members.

- The reduction reads `layout.options` as well as `layout.fields` and `layout.values`, because two of the three helpers that switch on `___output` hand over a select-view or sequence layout carrying options and no fields.
- It returns a `grid`, never a `sequence`: skin-standard-xml's own `Sequence.jso` intercepts a layout named sequence and calls `makeSequenceReply`, which expects options.
- `makeDataViewReply` returns `createBinaryFromLayout`'s own reply and does not build a second one. That function creates its own `SimpleServeRequest` and `XlsTargetReplyContext`, so the `query` argument is part of the member's contract and is not read here. The reply's file name comes from that request's `/document.xls`, not from the incoming request.
- A `link`-variant field stays inline text here, since this target registers only `string` and `grid`.

The movement is `magic-coordinator`'s Option B ruling of 2026-11-25 13:20 +0200 on `task-20261124T1826Z-build-per-module-dataviewreply`. The text came from `/Volumes/workspace/myx/ae3.sys.pkg.l2.tgt.xml/ae3-packages/ae3.sys.l2.tgt.xml/resources/lib/ae3.l2.xml/helper/ReduceDataViewGridXlsFn.js`, which the same change removed.

## The table byte path rejects the data-view reduction, measured 2026-11-25

`makeDataViewReply` returns a grid through `createBinaryFromLayout`, and `makeDataTableBinary` in this same module rejects a grid. Measured 2026-11-25 between 16:45 and 16:55 +0200, and its closing claim corrected at 17:57 the same day.

Both halves are in `/Volumes/workspace/myx/ae3.sys.pkg.l2.tgt.xls`:

- `java/ru/myx/ae3/l2/xls/XlsTargetContext.java:42` to `:43` registers exactly two layouts, `string`
  and `grid`, which is why a grid answers at all.
- `ae3-packages/ae3.sys.l2.tgt.xls/resources/lib/ae3/xls.js:65` is `makeDataTableBinary(layout)`, and
  it guards `layout.columns` at `:71` to `:74`, throwing `Column definition is required!`.

A reduction returns `{ layout : "grid", elements : [...] }`, which carries no `columns`, so
`makeDataTableBinary(reduceDataViewGridXls(layout))` throws. The same guard sits in all four modules,
at `csv.js:57` to `:62`, `html.js:53` to `:57`, and `pdf.js:73` by delegation. The fault is a shape
mismatch: a data-table layout passes that guard and a data-view grid does not.

The proof is `/Users/myx/Workspaces/ws-myx.prv-farm/.local/agents/spawned/fa01ded1-4581-4fbf-8757-d005125fc875/output/keeper-ae3/byte-path-probe.sh`, which prints `RESULT CONSTRAINT-HOLDS`.

Its consequence for a reply the module builds itself was wrong as first written, and the section below replaces it: the bytes do leave the target package, only the workbook builder does not.

## The bytes do reach a caller outside this package (measured 2026-11-25)

The bytes do reach a caller outside this package. Measured 2026-11-25 between 17:44 and 17:56 +0200,
by running four probes, with a control in each.

An earlier version of the section above said neither target context hands out bytes, and that the
copier never escapes `XlsTargetReplyContext`. That sentence is false, and nothing in it is needed by a
module that wants its own reply.

- `SimpleServeRequest.getResult()` returns the reply the walk applied. That reply is a
  `ru.myx.ae3.answer.ReplyCopier`, which implements `ru.myx.ae3.common.BodyAccessBinary`, so
  `getBinary()` answers a `TransferCopier` from outside `ru.myx.ae3.l2.xls`.
- `XlsTargetReplyContext.doFinish()` builds that reply with
  `Reply.binary("L2-XLS", query, this.buildWorkbook(), name)` and applies it through
  `query.getResponseTarget().apply(reply)`. The scratch request is its own response target, so the
  copier leaves the method inside the reply.
- The copier outlives the walk: `Reply.binary("L2-XLS", second, copier, "hosts.xls")` over it answers
  `Content-Name: hosts.xls` and the same byte count as the collected reply.
- What does not escape is `buildWorkbook()`, declared at `XlsTargetContext.java:92` with no modifier.
  A module needs neither that nor a peer accessor, since it can read the reply its own walk produced.
- Still unmeasured, and unchanged by the correction: a path that reads the reply the module already
  builds never reads `layout.name`, so the download is named `document.xls` whatever the layout is
  called.

Reproduce with `output/keeper-ae3/xlsgrid/NameProbe.java`, its classpath in `cp.txt` beside it. The
printed lines were `AS-BUILT name=document.xls code=200 length=13824`, `REWRAPPED name=hosts.xls
length=13824` and `REWRAPPED-NULL name=(none)`. The control, a layout naming no registered layout,
threw `ExceptionInInitializerError`. JDK 25.0.2 over the `bin` trees of `ae3.api`, `ae3.sdk`, `ae3.sys`,
`acm-base-api`, `acm-base-sdk`, the four `clean-java.*` and this project, plus
`acm-cvs/sys-current/axiom/jxl-2.6.12.jar`.

The produced workbook was read back with jxl, a path separate from the walk, by
`output/keeper-ae3/xlsgrid/SheetProbe.java`. A select-view grid answered `rows=2 columns=2
cells=[0,0]Enter query description [0,1]1 [1,0]Pick items from the list [1,1]2`, and the data-form
control answered `cells=[0,0]Host [0,1]ae3.local [1,0]Port [1,1]8080`. So the reduction's output does
become worksheet rows.

The PDF leg stays unmeasured. `PdfTargetReplyContext` throws `ClassNotFoundException` on
`org.bouncycastle.tsp.TimeStampToken` in that classpath, since `bcprov-jdk16-143.jar` carries 6
`org/bouncycastle/tsp/` entries and no `TimeStampToken`, and no jar under `acm-cvs/sys-current/axiom/`
carries it. `PdfTargetReplyContext.java:40` to `:73` is read, not run.

## A script reaches getBinary on the reply the walk produced (measured 2026-11-25)

The check magic-architect asked for at 17:35, and its answer: it holds, so the cheaper shape is
available. Measured 2026-11-25 at 18:10 to 18:12 +0200, by calling, not by reading.

A script reaches the copier through the same dispatch a method call resolves to,
`BaseObject.baseCall(name, soft)`:

- `reply.baseCall("getBinary", true)` answered a `ru.myx.ae3.binary.WrapCopier` of 13824 bytes, the
  same length the Java call returns.
- The control, `reply.baseCall("nosuchmethod", true)`, answered `undefined`, so the call is dispatch
  and not a fallback.
- Property reads agree: `getBinary`, `getBinaryContentLength`, `isBinary` and `getAttributes` all
  resolve as callable members, and `nosuchmember` is absent.
- `length`, `nextDirectArray` and `toStringUtf8` resolve callable on the copier itself.

So a module can build `ae3.Reply.binary(type, query, reply.getBinary(), layout.name || "data-view")`
with no Java change at all. What that buys is the download's own name: today the reply's
`Content-Name` is `document.xls`, and rewrapping the same bytes under `hosts.xls` answers
`hosts.xls` and the same byte count.

Reproduce with `output/keeper-ae3/xlsgrid/JsCallProbe.java` and `JsPropertyProbe.java`, classpath in
`cp.txt` beside them. `baseCall` is the engine's own dispatch, so this measures the mechanism a
compiled module uses, not a reflection shim. It is still not a run of a `.js` module: a bare
`Exec.createProcess` context installs no host globals, so `require`, `Format` and even `java` read
`undefined` there, and a module needs the boot's class order to load.
