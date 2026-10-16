# Handoff — kona-inertia

## Where things live

| Piece | Path |
|-------|------|
| Library | `inertia/` → artifact `dev.kona:kona-inertia` |
| Samples | `samples/inertia-kotlin`, `samples/inertia-java` |
| Docs site | `apps/docs/` → https://konaframework.github.io/kona-inertia/ |
| CI | `.github/workflows/ci.yml` (publishes Kona to mavenLocal first) |
| Docs deploy | `.github/workflows/docs.yml` |

## Dependency on Kona

CI checks out `konaframework/kona` at `main`, runs `publishToMavenLocal -x test`, then builds this repo.
Locally: `(cd ../kona && ./gradlew publishToMavenLocal -x test)` then `./gradlew :kona-inertia:test :kona-inertia:koverVerify`.

## Kona coupling

- Starter kit skeletons remain in Kona CLI (`--kit=inertia`).
- Kona docs keep a brief page that links here.
- Generated apps depend on `dev.kona:kona-inertia:{{konaVersion}}` from mavenLocal/Central.
