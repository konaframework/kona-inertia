# Inertia milestones

Single source of truth for status and Definition of Done. Working rules: [AGENTS.md](AGENTS.md).

**Active milestone:** none — package extracted from Kona; deepen as needed.

## Global Definition of Done

- [ ] Checklist for the milestone is 100% complete.
- [ ] 100% line + branch coverage on `kona-inertia` (Kover; CI enforced).
- [ ] Full regression suite green (library + Kotlin/Java samples).
- [ ] Kotlin and Java samples (or regression paths) exercise new public API.
- [ ] Public docs under `apps/docs/` updated (guides + sidebar).
- [ ] Milestone PR merged to `main` via required checks + automerge.

## Extraction (done)

- [x] Standalone repo `konaframework/kona-inertia`
- [x] Artifact `org.konaframework:kona-inertia` with Maven publish
- [x] 100% Kover on production code
- [x] Kotlin + Java samples
- [x] Exhaustive docs site + GitHub Pages workflow
- [x] Kona consumes external coordinate (skeletons / mavenLocal); no in-tree module
