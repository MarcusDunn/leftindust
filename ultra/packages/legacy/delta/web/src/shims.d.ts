declare module '*.svelte' {
  export { SvelteComponentDev as default } from 'svelte/internal';
}
declare module '*.graphql' {
  import { DocumentNode } from 'graphql';
  const Schema: DocumentNode;

  export = Schema;
}
declare module '*.mp4' {
  export = any;
}
declare module '*.gif' {
  export = any;
}
declare module '*.png' {
  export = any;
}
declare module '*.jpg' {
  export = any;
}