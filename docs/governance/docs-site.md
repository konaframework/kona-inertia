# Public documentation site (Inertia)

Stack: **Astro + Starlight + `@almasix/starlight-theme`**, **npm** under `apps/docs/` (`package-lock.json`), **Cloudflare Pages** primary at [inertia.konaframework.org](https://inertia.konaframework.org).

| Path | Role |
|------|------|
| `apps/docs/` | Public Starlight site |
| `docs/governance/` | Process locks (this file, handoff) |

## Cloudflare Pages (primary)

**First publish does not require GitHub secrets.** Prefer Cloudflare’s Git integration.

Pages project name: **`kona-inertia-docs`**.

### Path A — Connect to Git (dashboard)

1. **Workers & Pages → Create → Connect to Git** → `konaframework/kona-inertia`.
2. Framework preset: **None** (or leave custom — do not point at the Kona monorepo pnpm settings).
3. Build settings (**npm** — this repo is **not** a pnpm workspace):

   | Setting | Value |
   |---------|--------|
   | Root directory | `apps/docs` |
   | Build command | `npm ci && npm run build` |
   | Build output directory | `dist` |
   | Environment variables | `PUBLIC_SITE_URL=https://inertia.konaframework.org`, `PUBLIC_BASE_PATH=/`, `NODE_VERSION=22` |

4. Custom domain: `inertia.konaframework.org` → CNAME to the `*.pages.dev` host Cloudflare shows.

Do **not** copy Kona’s dashboard settings (`pnpm …` / root `/`). Inertia docs live only under `apps/docs` with `package-lock.json`.

### Path B — optional Wrangler from GitHub Actions

Workflow: [`.github/workflows/cloudflare-pages.yml`](../../.github/workflows/cloudflare-pages.yml). Deploy step runs only when `CLOUDFLARE_API_TOKEN` and `CLOUDFLARE_ACCOUNT_ID` are set.

Local:

```bash
cd apps/docs
npm ci
PUBLIC_SITE_URL=https://inertia.konaframework.org PUBLIC_BASE_PATH=/ npm run build
npx wrangler pages deploy dist --project-name=kona-inertia-docs
```

Config: `apps/docs/wrangler.jsonc`.
