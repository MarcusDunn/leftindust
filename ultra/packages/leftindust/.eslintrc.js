module.exports = {
  parser: '@typescript-eslint/parser',
  extends: [
    'eslint:recommended',
    'plugin:@typescript-eslint/recommended',
    'plugin:@typescript-eslint/recommended-requiring-type-checking',
    'plugin:@ota-meshi/svelte/recommended',
  ],
  parserOptions: {
    ecmaVersion: 2022,
    sourceType: 'module',
    tsconfigRootDir: __dirname,
    project: ['tsconfig.json'],
    extraFileExtensions: ['.svelte'],
  },
  overrides: [
    {
      files: ['*.svelte'],
      parser: 'svelte-eslint-parser',
      parserOptions: {
        parser: '@typescript-eslint/parser',
      },
    },
    {
      files: ['*.graphql'],
      extends: 'plugin:@graphql-eslint/schema-recommended',
      parserOptions: {
        schema: './src/api/server/graphql/schema/leftindust.schema.graphql',
      },
    },
  ],
  env: {
    node: true,
    es6: true,
    browser: true,
  },
  plugins: ['@typescript-eslint', 'import-newlines', 'autofix', 'graphql'],
  ignorePatterns: ['node_modules', '.eslintrc.js', 'vite.config.js'],
  rules: {
    '@typescript-eslint/no-unsafe-member-access': 'off',
    '@typescript-eslint/restrict-template-expressions': 'off',
    'indent': ['error', 2, { 'SwitchCase': 1 }],
    '@typescript-eslint/ban-ts-comment': 'off',
    '@typescript-eslint/no-unsafe-assignment': 'off',
    '@typescript-eslint/no-unsafe-call': 'off',
    '@typescript-eslint/no-unsafe-return': 'off',
    '@typescript-eslint/no-unsafe-argument': 'off',
    '@typescript-eslint/unbound-method': 'off',
    '@ota-meshi/svelte/html-quotes': 'error',
    '@ota-meshi/svelte/indent': 'error',
    '@ota-meshi/svelte/mustache-spacing': 'error',
    '@ota-meshi/svelte/shorthand-attribute': 'error',
    '@ota-meshi/svelte/valid-compile': 'off',
    'comma-dangle': ['error', 'always-multiline'],
    quotes: ['error', 'single'],
    semi: 'error',
    'object-curly-spacing': ['error', 'always'],
  },
};
