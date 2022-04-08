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

// "If you are using Typescript, you will need to add the following block to your global.d.ts (at least until this svelte issue is resolved)"
// https://github.com/isaacHagoel/svelte-dnd-action#typescript
declare type DndEvent = import('svelte-dnd-action').DndEvent;
declare namespace svelte.JSX {
  interface HTMLAttributes<T> {
    onconsider?: (event: CustomEvent<DndEvent> & { target: EventTarget & T }) => void;
    onfinalize?: (event: CustomEvent<DndEvent> & { target: EventTarget & T }) => void;
  }
}