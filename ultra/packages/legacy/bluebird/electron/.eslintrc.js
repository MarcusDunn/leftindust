module.exports = {
  parser: '@typescript-eslint/parser', // Specifies the ESLint parser
  parserOptions: {
    ecmaVersion: 2021, // Allows for the parsing of modern ECMAScript features
    sourceType: 'module', // Allows for the use of imports
  },
  extends: [
    'plugin:@typescript-eslint/recommended',
  ],
  ignorePatterns: ['node_modules', '.eslintrc.js'],
  plugins: ['@typescript-eslint', 'import-newlines', 'autofix'],
  rules: {
    '@typescript-eslint/no-unsafe-member-access': 'off',
    '@typescript-eslint/restrict-template-expressions': 'off',
    'comma-dangle': ['error', 'always-multiline'],
    quotes: ['error', 'single'],
    semi: 'error',
    'object-curly-spacing': ['error', 'always'],
    'import-newlines/enforce': [
      'error',
      {
        'items': 3,
        'max-len': 100,
        'semi': false,
      },
    ],
  },
};