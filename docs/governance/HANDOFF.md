# Agent handoff — Inertia (`kona-inertia`)

Paste this file (or its path) into a new chat so another agent can continue without the prior transcript.

## Purpose

**kona-inertia** lets a Kona JVM app return an Inertia **page object** (component + props) for SPA hydration. First visit is HTML; later navigations use `X-Inertia` → JSON. Artifact `org.konaframework:kona-inertia`, package `org.konaframework.inertia`. Kotlin-first, Java first-class.

Repo: [konaframework/kona-inertia](https://github.com/konaframework/kona-inertia)  
Docs: https://inertia.konaframework.org/  
Kona monorepo keeps a **brief link-out** + `--kit=inertia` skeletons (extraction PR: [kona#101](https://github.com/konaframework/kona/pull/101)).

## Read first

1. [AGENTS.md](../../AGENTS.md) — laws, architecture locks, Auto model default.
2. [MILESTONES.md](../../MILESTONES.md) — extraction done; next deepen TBD.
3. Parent framework handoff: sibling `/home/smaosa/kona` → [docs/governance/HANDOFF.md](https://github.com/konaframework/kona/blob/main/docs/governance/HANDOFF.md).
4. Brew (separate product): `/home/smaosa/kona-brew` — not required for Inertia work.
5. **Never** mutate GitHub rulesets.

## Where things live

| Piece | Path |
|-------|------|
| Library | `inertia/` → `org.konaframework:kona-inertia` |
| Samples | `samples/inertia-kotlin`, `samples/inertia-java` |
| Docs site | `apps/docs/` → https://inertia.konaframework.org/ |
| CI | `.github/workflows/ci.yml` (publishes Kona to mavenLocal first) |
| Docs deploy | `.github/workflows/docs.yml` → GitHub Pages (Actions) |

## Where we are (2026-10-16)

| Item | Value |
|------|--------|
| Default branch | `main` — initial extraction commit shipped |
| Active milestone | **none** — package extracted; deepen as needed |
| Open PRs | none (as of handoff) |
| Pages | Enabled — https://inertia.konaframework.org/ |
| Extraction | Done in this repo + Kona cutover [#101](https://github.com/konaframework/kona/pull/101) |
| Models | **Auto only** unless human names a model |
| Merge habit | PR → green CI → squash automerge |

### Done vs in-flight (honest)

**Done:**

- Standalone repo with library, Kotlin + Java samples, exhaustive docs site, CI, Pages workflow.
- Kona no longer vendors an in-tree Inertia module; apps depend on `org.konaframework:kona-inertia`.
- Starter kit skeletons remain in Kona CLI (`--kit=inertia`); `kona install` can wire packages (kona #100).

**In-flight / optional:**

- No open feature PRs. Optional: verify Pages deploy green after latest `main`, polish docs depth, or deepen protocol parity when a human sets a milestone checklist.
- Local `~/hello-kona` is a throwaway sample — ignore.

## How to continue

```bash
# 1. Publish Kona to Maven local (once per Kona change)
(cd ../kona && ./gradlew publishToMavenLocal -x test)

# 2. Library: tests + 100% line/branch coverage
./gradlew :kona-inertia:test :kona-inertia:koverVerify

# 3. Samples
./gradlew :inertia-sample-kotlin:test :inertia-sample-java:test

# 4. Publish this artifact for apps / kona new --kit=inertia
./gradlew :kona-inertia:publishToMavenLocal

# Docs
cd apps/docs && npm ci && npm run dev
# Pages build:
# PUBLIC_SITE_URL=https://inertia.konaframework.org PUBLIC_BASE_PATH=/ npm run build
```

Run samples:

```bash
./gradlew :inertia-sample-kotlin:run --args="--base=$(pwd)/samples/inertia-kotlin"   # :4000
./gradlew :inertia-sample-java:run --args="--base=$(pwd)/samples/inertia-java"
```

## Open gaps / next priorities

1. Confirm CI + docs workflow green on `main`.
2. When deepening: open a milestone in `MILESTONES.md` first (one at a time, 100% Kover, Kotlin + Java samples, docs rewrite).
3. Keep Kona coupling thin: skeletons + brief docs + maven coordinate only.
4. Do not re-merge the library into the Kona monorepo.

## Protocol reminders

- First visit: HTML shell with `data-page`.
- `X-Inertia: true` → JSON `{ component, props, url, version }`.
- Version mismatch → 409; `Inertia.location` → 409 + `X-Inertia-Location` (or 302 for non-Inertia).
- Vite tags via Crema `AssetManifest` / `org.konaframework.inertia.assets.Vite`.

## Do not

- Skip coverage or ship partial protocol slices without a checklist.
- Mutate GitHub rulesets.
- Pin named Cursor models unless the human asks.
- Expand full Inertia guides back into the Kona monorepo site.
