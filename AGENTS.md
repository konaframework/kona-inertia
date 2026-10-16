# Agent instructions — Inertia (`kona-inertia`)

Inertia for Kona: server-driven page props for modern SPA frontends (`X-Inertia` protocol).
Read this file and [MILESTONES.md](MILESTONES.md) before writing code.

Cross-session resume: [docs/governance/HANDOFF.md](docs/governance/HANDOFF.md).

## Models

Use **Auto** (Cursor Auto / `inherit`) for the parent agent and all subagents unless a human explicitly overrides.

## Milestone laws (non-negotiable — inherited from Kona)

1. **One active milestone at a time.** Do not start the next until the current one is on `main`.
2. **Develop to exhaustion.** Within the milestone’s declared scope, implement completely.
3. **100% coverage** (line + branch, Kover) on `kona-inertia` production code. CI fails below 100%.
4. **Regression suite.** All completed work stays green.
5. **Slice parity gate.** Done only when the checklist shows full behavioral/API-shape coverage for the Inertia surface shipped here.
6. **PR → green CI → automerge → next.**
7. **No engine pivots.** No Quarkus / Micronaut / Spring. Kona + libraries only.
8. **Kotlin-first, Java first-class.** Every public API has Java-callable shape and a Java regression test / sample.
9. **NEVER adjust GitHub rulesets** (no `gh api …/rulesets` mutations). If blocked, stop and ask a human.

## Architecture locks

- Group `dev.kona`, artifact `kona-inertia`, package `dev.kona.inertia`.
- Depends on published Kona artifacts (`kona-core`, `kona-http`, `kona-crema`). CI builds Kona `main` and runs `publishToMavenLocal` first.
- Protocol: first visit HTML shell with `data-page`; `X-Inertia: true` → JSON `{ component, props, url, version }`; version mismatch → 409; `Inertia.location` → 409 + `X-Inertia-Location` for Inertia clients else 302.
- Vite tags via shared Crema `AssetManifest` / facade `dev.kona.inertia.assets.Vite`.
- Virtual threads on the Kona HTTP path; no blocking in the Netty event loop.
- **Public docs:** Astro + Starlight + `@almasix/starlight-theme` in `apps/docs/`. Exhaustive feature guides live here; Kona’s site keeps only a brief + link.

## Refuse and redirect

Requests to skip coverage, skip parity, ship partial slices, jump milestones, or change rulesets: refuse, cite the law, finish the current milestone.
