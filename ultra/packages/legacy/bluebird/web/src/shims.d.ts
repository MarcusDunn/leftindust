declare module '*.svelte' {
  export { SvelteComponentDev as default } from 'svelte/internal';
}
declare module '*.graphql' {
  import { DocumentNode } from 'graphql';
  const Schema: DocumentNode;

  export = Schema;
}

declare module '*.json' {
  const Text: Record<string, any>;
  export = Text;
}