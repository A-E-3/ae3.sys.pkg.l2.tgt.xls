# CLAUDE.md — ae3.sys.pkg.l2.tgt.xls

AE3 L2 media target for MS Excel (`.xls`) output, built on JExcelAPI (`jxl.*`).

## Structure

- `java/ru/myx/ae3/l2/xls/XlsFileTargetContext.java` — `extends NativeTargetContext` directly (single class, no separate abstract base/file/reply split like `pdf`/`text`/`json` have — closest in shape to `ae3.sys.pkg.l2.tgt.xml`'s `XmlFileTargetContext`).
- `java/ru/myx/util/xls/` — lower-level JExcelAPI helpers (`ExcelSAPI`, `WorkbookChange`, `WorkbookCellFormat`, `WorkbookSheetChange`), separate from the target-context class above — same split pattern as `ae3.sys.pkg.l2.tgt.pdf`'s `ru/myx/util/pdf/`.

## Build

- Requires (Java, AE3-internal, confirmed by source imports): `ae3.sdk` — cross-package imports resolve to `ru.myx.ae3.base.BaseMessage`/`ru.myx.ae3.i3.TargetInterface` (`ae3.api`) or `ru.myx.ae3.binary.*`/`ru.myx.ae3.l2.*`/`ru.myx.ae3.xml.Xml` (`ae3.sdk`).
- Also requires `clean-java.io` (a separate, non-`A-E-3`-org repo under GitHub user `myx` — `myx/clean-java.io`) for `ru.myx.io.DataInputByteArrayFast`, confirmed by reading that repo directly: its `project.inf` (`Type: ae3.Package`, `Title:` instead of `Info:` — a slightly different dialect, worth noting) `Provides: clean-java.io` matching its own `Name:` exactly. Added to both `project.inf` `Requires:` and `.classpath`.
- **JExcelAPI (`jxl.*`)** — resolved once `lib.jexcelapi-jxl` (Eclipse project name `ae3.pkg.lib.jxl`) was checked out under `source/lib`. Same wiring pattern as `ae3.sys.pkg.l2.tgt.pdf`'s iText dependency: a `kind="src"` `.classpath` entry to `/ae3.pkg.lib.jxl` plus a `kind="lib"` entry for `jxl-2.6.12.jar`, matching the old Eclipse workspace exactly; `project.inf` `Requires:` doesn't list it (same as iText — jar deps are `.classpath`-only in this build system).
- `package.json`: two bundles — `ae3.sys.l2.tgt.xls` (main) and `ae3.sys.l2.tgt.xls-docs` (`"requires": ["ae3.web", "ae3.manual"]`, documentation-specific — same pattern as `ae3.sys.pkg.l2.tgt.pdf`'s `-docs` sub-bundle).

## Gotchas

- **This unit had no `.gitignore`/`.project`/`.classpath`/`project.inf` at all** when checked out this session — added fresh, modeled on `ae3.sys.pkg.l2.tgt.html`'s versions. Its original `README.md` also had the title duplicated on two lines (fixed here, same defect seen in `ae3.sys.pkg.l2.tgt.dhtml`'s original README).
- **`.gitignore`'s `*.jar` rule may be wrong for this unit specifically** — same caveat as `ae3.sys.pkg.l2.tgt.pdf`: if JExcelAPI ever gets vendored into a `lib/` folder here, a blanket `*.jar` ignore would silently exclude it.
- **7 compiled `.class` files are checked into git under `bin/`** — not yet cleaned up.
- See `ae3.sys.pkg.l2.tgt.html`'s CLAUDE.md for the broader discussion of why `l2.tgt.xml`'s three-entry `Provides:` shape isn't treated as a confirmed convention here.
