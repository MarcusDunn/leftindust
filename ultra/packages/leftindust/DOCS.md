# Ultra Documentation

## Project Structure
The structure of a project only exists to serve one crucial goal: to present its structure in as 
legible and self-explanatory of a manner as possible. A contributor should instantly be able to intuitively
understand the relationship between a feature and it's respective code, without needing to search through
nested directories and files. This is why this project source (./src) has been split up into 5 directories.

### Source
The source file contains all the files for building the frontend.

#### Language
The language folder contains the default module (index.ts) and the locales folder. The purpose of this section
is quite self explanatory – the module uses the i18n standard to enable intelligent multi-lingual support within
the project.
<br />
<br />
[What is i18n?](https://lingoport.com/what-is-i18n/)
<br />
[Svelte i18n Documentation](https://github.com/kaisermann/svelte-i18n/blob/main/docs/Getting%20Started.md)

#### API
The api folder contains 2 folders – bridge and server. Each folder contains a separate and contained API.
`bridge` acts as an API to interface with the respective deployment platform's native APIs without creating unnecessary
fragmentation. `server` contains pre written requests that use the Apollo interface to interface with the backend.
<br />
<br />
[Apollo](https://www.apollographql.com)
<br />
[Electron](https://www.electronjs.org)
<br />
[Capacitor](https://capacitorjs.com)

#### Features
Features are the heart of the project. As the name suggests, the features folder is essentially a collection of components
organized by their respective feature. The features folder is divided into specific sub-sections.

`index.ts`
<br />
The main module. This should export all feature specific functions, types, and svelte files.

`store.ts`
<br />
State management. This should strictly export svelte stores.

`*.svelte`
<br />
Svelte components are divided into 3 types. Page components are loaded via the router, wizards are opened in the wizard
loader, and a component is loaded via an import in markup.

`*.scss`
<br />
SCSS files are scoped specifically to; and always named after their svelte counterpart.

`*.spec.ts, *.test.ts`
<br />
Unit tests for whichever module or component being referenced, and similar to SCSS files, are named after their component
counterpart.

`components`
<br />
A folder that contains sub-components that specifically pertain to the feature. The components folder contains all the files
contained in features directory, with the exception of the components folder.

#### Apps
The apps folder contains entry-points for each app. An app contains a HTML entry (index.html), a router (router.ts), an App entry
(App.svelte), assets, and configuration files (vite.config.ts). Apps are structured in this manner to be
build-able as individual apps or as components within a larger project. (The root directory also follows this structure)
<br />
<br />
[Framework7 Routes](https://framework7.io/docs/routes)
<br />
[manifest.json](https://developer.mozilla.org/en-US/docs/Mozilla/Add-ons/WebExtensions/manifest.json)
<br />
[Vite](https://vitejs.dev)

#### Style
The style folder contains a global stylesheet, SASS helpers, and bundled font families. This does not include component stylesheets.

### Public
The public folder is for all files meant to be accessible non-restricted in the root of the vite server. Assets and manifests are
contained in this folder.