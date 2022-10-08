<script lang="ts">
  import { f7 } from 'framework7-svelte';

  import './Footer.scss';

  export let style = '';

  export const scroll = (node: HTMLElement): void => {
    const page = node.parentNode as HTMLElement;

    if (page?.style) {
      const footer = page.querySelector('.ui-footer') as HTMLElement;
      f7.$(footer)
        .addClass('ui-footer-scroll');
    }
  };
</script>

<div class={`ui-footer ${$$restProps.class}`} {style} use:scroll>
  <slot />
</div>