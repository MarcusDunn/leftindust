# @delta/library

Core APIs for Ultra. This package is not meant to be run directly, but referenced in other applications as a shared codebase.

## Usage
Modules in this API can be referenced in two ways. The first and recommended way is to use the `@framework` path. This approach will bring the API module into the calle's project and consume it as if it's a part of the project. This is the only way to use TypeScript and SASS files without compiling the API beforehand.

The second approach is to import the API as a package dependency with Lerna. This will treat the package like an external–production-ready package. This is not recommended to use as of now. Take this approach by importing `@delta/library`

```js
// Consuming as internal dependancy
import '@framework/THE_FILE_YOU_ARE_IMPORTING';

// Consuming as external dependancy
import '@delta/library/src/THE_FILE_YOU_ARE_IMPORTING';
```
