// @ts-check
import { defineConfig } from 'astro/config';
import starlight from '@astrojs/starlight';
import almasixTheme from '@almasix/starlight-theme';
import { buildSidebar } from './sidebar.mjs';

// GitHub Pages: PUBLIC_BASE_PATH=/kona-inertia/ → https://konaframework.github.io/kona-inertia/
const base = process.env.PUBLIC_BASE_PATH || '/';
const site = process.env.PUBLIC_SITE_URL || 'https://konaframework.github.io';
const origin = site.replace(/\/$/, '');
const normalizedBase = base === '/' ? '/' : `/${base.replace(/^\/|\/$/g, '')}/`;
const ogImage = `${origin}${normalizedBase}og.svg`;
const ogImageAlt = 'Inertia for Kona — server-driven page props for modern frontends';
const sidebar = await buildSidebar();

export default defineConfig({
  site,
  base,
  trailingSlash: 'ignore',
  integrations: [
    starlight({
      title: 'Inertia',
      description:
        'Inertia for Kona — serve modern single-page frontends with server-driven page props. Kotlin-first, Java first-class.',
      favicon: '/favicon.svg',
      social: [
        {
          icon: 'github',
          label: 'GitHub',
          href: 'https://github.com/konaframework/kona-inertia',
        },
      ],
      plugins: [
        almasixTheme({
          github: 'konaframework/kona-inertia',
          lightbox: false,
        }),
      ],
      customCss: ['./src/styles/custom.css'],
      components: {
        SiteTitle: './src/components/SiteTitle.astro',
      },
      expressiveCode: {
        themes: ['monokai'],
        styleOverrides: {
          codeBackground: '#272822',
          codeForeground: '#f8f8f2',
          frames: {
            editorBackground: '#272822',
            terminalBackground: '#272822',
          },
        },
      },
      editLink: {
        baseUrl: 'https://github.com/konaframework/kona-inertia/edit/main/apps/docs/',
      },
      head: [
        {
          tag: 'meta',
          attrs: {
            name: 'theme-color',
            content: '#4F46E5',
          },
        },
        {
          tag: 'meta',
          attrs: {
            name: 'robots',
            content: 'index, follow, max-image-preview:large, max-snippet:-1, max-video-preview:-1',
          },
        },
        {
          tag: 'meta',
          attrs: {
            property: 'og:image',
            content: ogImage,
          },
        },
        {
          tag: 'meta',
          attrs: {
            property: 'og:image:alt',
            content: ogImageAlt,
          },
        },
        {
          tag: 'meta',
          attrs: {
            name: 'twitter:card',
            content: 'summary_large_image',
          },
        },
      ],
      sidebar,
    }),
  ],
});
