import { defineConfig } from 'vite';
import { svelte } from '@sveltejs/vite-plugin-svelte';
import sveltePreprocess from 'svelte-preprocess';
import path from 'path';
import { NodeGlobalsPolyfillPlugin } from '@esbuild-plugins/node-globals-polyfill'

const graphql = require('@rollup/plugin-graphql');

const production = process.env.NODE_ENV === 'production';

const root = path.resolve(__dirname, 'src');
const outDir = path.resolve(__dirname, 'build');

// https://vitejs.dev/config/
export default defineConfig({
  root,
  base: production ? '/' : '',
  publicDir: '../public',
  mode: production ? 'production' : 'development',
  build: {
    outDir,
    rollupOptions: {
      input: {
        main: path.resolve(__dirname, 'src/index.html'),
        mediq: path.resolve(__dirname, 'src/apps/mediq/index.html'),
        queue: path.resolve(__dirname, 'src/apps/queue/index.html'),
        intake: path.resolve(__dirname, 'src/apps/intake/index.html'),
        iris: path.resolve(__dirname, 'src/apps/iris/index.html'),
      },
    },
    assetsInlineLimit: 0,
    emptyOutDir: true,
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
      include: ['./**/*'],
    }),
  ],
  optimizeDeps: {
    include: ['fast-json-stable-stringify', 'zen-observable', '@fullcalendar/common'],
    exclude: ['@urql/svelte'],
    esbuildOptions: {
      // Node.js global to browser globalThis
      define: {
          global: 'globalThis'
      },
      // Enable esbuild polyfill plugins
      plugins: [
          NodeGlobalsPolyfillPlugin({
              buffer: true
          })
      ]
  }
  },
  resolve: {
    alias: {
      '@': path.resolve(path.resolve(), './src'),
    },
  },
});
