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
  plugins: ['@typescript-eslint', 'svelte3', 'import-newlines', 'autofix'],
  ignorePatterns: ['node_modules', '.eslintrc.js', 'vite.config.js'],
  rules: {
    '@typescript-eslint/no-unsafe-member-access': 'off',
    '@typescript-eslint/restrict-template-expressions': 'off',
    'indent': ['error', 2, {'SwitchCase': 1}],
    '@typescript-eslint/ban-ts-comment': 'off',
    '@typescript-eslint/no-unsafe-assignment': 'off',
    '@typescript-eslint/no-unsafe-call': 'off',
    '@typescript-eslint/no-unsafe-return': 'off',
    'max-len': ['error', { 'code': 125 }],
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
    ]
  }
};
