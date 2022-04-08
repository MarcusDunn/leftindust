import { defineConfig } from 'vite';
import { svelte } from '@sveltejs/vite-plugin-svelte';
import sveltePreprocess from 'svelte-preprocess';
import path from 'path';

const graphql = require('@rollup/plugin-graphql');

const production = process.env.NODE_ENV === 'production';

// https://vitejs.dev/config/
export default defineConfig({
    base: '',
    build: {
      outDir: './build/public',
    },
    server: {
      port: 5003,
      fs: {
        allow: ['../../../../'],
      },
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
              tsconfigFile: './tsconfig.json',
            },
          }),
        ],
        compilerOptions: {
          dev: !production,
        },
        hot: !production,
      }),
      graphql({
        include: ['./**/*', '../../delta/web/**/*'],
      }),
    ],
    optimizeDeps: {
      include: ['@apollo/client/core', 'fast-json-stable-stringify', 'zen-observable', 'react', '@fullcalendar/common'],
      exclude: ['@apollo/client', 'framework7', 'framework7-svelte'],
    },
    resolve: {
      alias: {
        '@': path.resolve(path.resolve(), './src'),
        '@framework': path.resolve(
            path.resolve(),
            '../../delta/web/src',
        ),
      },
    },
});
