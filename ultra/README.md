# MedIQ Ultra

Monorepo containing all front-end design for MedIQ.

## Mission
Ultra was designed with 2 goals in mind: mass scalability, and
clarity in design without the compromise of features. These 2 fundamental principles: despite sounding
so seemingly simple, have enabled us to take an unorthodox approach to medical technologies–or 
enterprise software in general, where the end user's experience is at the forefront of our software
design.

With these simple goals in mind, we believe that Ultra has the capacity to change
medical technologies for the better, and provide medical professionals with a product that
fundamentally alters the way they think of, and use EMR technologies forever.

## Todo
See the [MedIQ Ultra Taskboard](./TODO.md)

## Roadmap
See what's in store for Ultra at the [MedIQ Ultra Roadmap](./ROADMAP.md)

## Setup
To set up Ultra for development, simply clone this repo and run `yarn install` in the root directory (Make sure to use yarn, as NPM lacks support for workspaces). This will install all the necessary dependencies for the packages to properly function.  

## Docs
General documentation and guidelines outlining API and architectural design.
See the [MedIQ Ultra Documentation](./packages/leftindust/DOCS.md)

## Scripts
* 🚀 `yarn start` - Deploy all available instances of Ultra

### Legacy
* 🌎 Web (Desktop) 
  * `yarn run start:bluebird:web` - Deploy web project in browser
  * `yarn run build:bluebird:web` - Build web app for production in browser
* 🖥 Electron
  * `yarn run electron:start` - Deploy electron project in browser
* 🗺 `yarn run schema:generate` - Generate GraphQL Schema from connected server (Configuration is available in the schema field in [@delta/web/codegen.yml](./packages/delta/library/codegen.yml))
