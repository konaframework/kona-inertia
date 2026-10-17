# Inertia for Kona

**kona-inertia** lets a Kona JVM app return an Inertia **page object** — component name plus props — that a JavaScript client hydrates. The first visit is a normal HTML document; later navigations send the `X-Inertia` header and receive JSON.

```kotlin
router.get("/") {
    Inertia.render("Pages/Home", mapOf("user" to mapOf("name" to "Ada")))
}
```

- Artifact: `org.konaframework:kona-inertia` (Kotlin internals, first-class Java API).
- Depends on published Kona modules: `kona-core`, `kona-http`, `kona-crema`.
- **Docs (this repo):** https://inertia.konaframework.org/ — Astro + Starlight under `apps/docs/` (coffee brand: caramel `#DF8723` / latte `#C9A57B` / accent `#74453F`).
- Framework primer also on the [Kona docs](https://docs.konaframework.org/) site (brief + link).

## Documentation site

```bash
cd apps/docs
npm ci
npm run dev          # http://127.0.0.1:4323
PUBLIC_SITE_URL=https://inertia.konaframework.org PUBLIC_BASE_PATH=/ npm run build
npm run preview
```

Primary host is Cloudflare Workers Assets (`kona-inertia-docs`). Optional mirror: GitHub Pages via [`.github/workflows/docs.yml`](.github/workflows/docs.yml). Branding SVGs: `apps/docs/public/branding/`.

## Requirements

- JDK 21
- Node 22 (docs site)
- Kona artifacts in Maven local (until `org.konaframework` is published to Maven Central)

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

Publish `org.konaframework:kona-inertia` to Maven local before the first generated-app build.

## Docs hosting

Primary: [inertia.konaframework.org](https://inertia.konaframework.org) (Cloudflare Worker `kona-inertia-docs`, static Workers Assets).

```bash
cd apps/docs
PUBLIC_SITE_URL=https://inertia.konaframework.org PUBLIC_BASE_PATH=/ npm run build
```

Deploy: Cloudflare **Workers Builds** Git integration for project `kona-inertia-docs` (no GitHub secrets, no `CLOUDFLARE_API_TOKEN` build variable). Deploy with `npx wrangler deploy` — never `wrangler pages deploy`. Dashboard settings (Framework preset **None**; npm — not Kona’s pnpm monorepo):

| Setting | Value |
|---------|--------|
| Root directory | `apps/docs` (or `/` with build command `npm ci --prefix apps/docs && npm run build --prefix apps/docs`) |
| Build command | `npm ci && npm run build` |
| Deploy command | `npx wrangler deploy` |
| Non-production (preview) command | `npx wrangler preview` |
| Build variables | `PUBLIC_SITE_URL=https://inertia.konaframework.org`, `PUBLIC_BASE_PATH=/`, `NODE_VERSION=22` |

Full notes: [docs/governance/docs-site.md](docs/governance/docs-site.md). Optional later: `CLOUDFLARE_API_TOKEN` + `CLOUDFLARE_ACCOUNT_ID` so `.github/workflows/cloudflare-pages.yml` can Wrangler-deploy. Mirror: GitHub Pages at `/kona-inertia/` via `.github/workflows/docs.yml`. Central publish order: see [kona publishing.md](https://github.com/konaframework/kona/blob/main/docs/governance/publishing.md).
