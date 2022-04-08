<script lang="ts">
import { f7 } from 'framework7-svelte';

export const scroll = (node: HTMLElement): void => {
  const page = node.parentNode as HTMLElement;

  if (page?.style) {
    const footer = page.querySelector('.ultra-footer') as HTMLElement;
    f7.$(footer)
      .addClass('footer-scroll');
  }
};

</script>

<div class="ultra-footer" use:scroll>
  <slot />
</div>