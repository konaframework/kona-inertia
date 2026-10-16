# Inertia for Kona

**kona-inertia** lets a Kona JVM app return an Inertia **page object** — component name plus props — that a JavaScript client hydrates. The first visit is a normal HTML document; later navigations send the `X-Inertia` header and receive JSON.

```kotlin
router.get("/") {
    Inertia.render("Pages/Home", mapOf("user" to mapOf("name" to "Ada")))
}
```

- Artifact: `io.konaframework:kona-inertia` (Kotlin internals, first-class Java API).
- Depends on published Kona modules: `kona-core`, `kona-http`, `kona-crema`.
- **Docs (this repo):** https://konaframework.github.io/kona-inertia/ — Astro + Starlight under `apps/docs/`.
- Framework primer also on the [Kona docs](https://konaframework.github.io/kona/) site (brief + link).

## Documentation site

```bash
cd apps/docs
npm ci
npm run dev          # http://127.0.0.1:4323
PUBLIC_SITE_URL=https://konaframework.github.io PUBLIC_BASE_PATH=/kona-inertia/ npm run build
npm run preview
```

GitHub Pages is enabled for this repository (Settings → Pages → **GitHub Actions**).
Workflow [`.github/workflows/docs.yml`](.github/workflows/docs.yml) builds `apps/docs` on pushes to
`main` and deploys to https://konaframework.github.io/kona-inertia/.

## Requirements

- JDK 21
- Node 22 (docs site)
- Kona artifacts in Maven local (until `io.konaframework` is published to Maven Central)

## Build and test

```bash
# 1. Publish Kona to Maven local (once per Kona change)
git clone https://github.com/konaframework/kona ../kona
(cd ../kona && ./gradlew publishToMavenLocal -x test)

# 2. Inertia library: tests + 100% line/branch coverage
./gradlew :kona-inertia:test :kona-inertia:koverVerify

# 3. Samples
./gradlew :inertia-sample-kotlin:test :inertia-sample-java:test

# 4. Publish this artifact for apps / kona new --kit=inertia
./gradlew :kona-inertia:publishToMavenLocal
```

## Run the samples

```bash
./gradlew :inertia-sample-kotlin:run --args="--base=$(pwd)/samples/inertia-kotlin"   # http://127.0.0.1:4000
./gradlew :inertia-sample-java:run --args="--base=$(pwd)/samples/inertia-java"
```

## Scaffold from Kona CLI

```bash
kona new my-app --kit=inertia
# or --lang=java
```

Publish `io.konaframework:kona-inertia` to Maven local before the first generated-app build.
