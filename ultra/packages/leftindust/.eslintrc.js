module.exports = {
  parser: '@typescript-eslint/parser',
  extends: [
    'eslint:recommended',
    'plugin:@typescript-eslint/recommended',
    'plugin:@typescript-eslint/recommended-requiring-type-checking',
  ],
  parserOptions: {
    ecmaVersion: 2021,
    sourceType: 'module',
    tsconfigRootDir: __dirname,
    project: ['tsconfig.json'],
    extraFileExtensions: ['.svelte'],
  },
  overrides: [
    {
      files: ['*.svelte'],
      processor: 'svelte3/svelte3',
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
  settings: {
    'svelte3/typescript': require('typescript'),
    'svelte3/ignore-styles': () => true,
  },
  plugins: ['@typescript-eslint', 'svelte3', 'import-newlines', 'autofix', 'graphql'],
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
    'comma-dangle': ['error', 'always-multiline'],
    quotes: ['error', 'single'],
    semi: 'error',
    'object-curly-spacing': ['error', 'always'],
    'import-newlines/enforce': [
        'error',
        {
          'items': 3,
          'max-len': 100,
          'semi': true
        }
    ],
  },
};
