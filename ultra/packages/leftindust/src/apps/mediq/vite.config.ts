// @ts-nocheck
import { defineConfig } from 'vite';
import { svelte } from '@sveltejs/vite-plugin-svelte';
import sveltePreprocess from 'svelte-preprocess';
import path from 'path';

import graphql from '@rollup/plugin-graphql';

const production = process.env.NODE_ENV === 'production';

// https://vitejs.dev/config/
export default defineConfig({
  base: '',
  build: {
    outDir: '../../../../build/mediq',
  },
  server: {
    host: true,
    port: 5002,
  },
  rollupDedupe: ['svelte', '@fullcalendar/common'],
  plugins: [
    // Path is relative to './src/app' so I have to
    // navigate up to access the svelte config
    svelte({
      emitCss: !production,
      preprocess: [
        sveltePreprocess({
          typescript: {
            tsconfigFile: '../../../../tsconfig.json',
          },
        }),
      ],
      compilerOptions: {
        dev: !production,
      },
      hot: !production,
    }),
    graphql({
      include: ['./**/*'],
    }),
  ],
  optimizeDeps: {
    include: ['@apollo/client/core', '@apollo/client/cache', 'fast-json-stable-stringify', 'zen-observable', 'react', '@fullcalendar/common'],
    exclude: ['@apollo/client', 'framework7', 'framework7-svelte'],
  },
  resolve: {
    alias: {
      '@': path.resolve(path.resolve(), '../../../../src'),
    },
  },
});
