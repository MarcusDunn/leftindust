<script lang="ts">
  import { f7, Page } from 'framework7-svelte';

  import './Page.scss';

  export let style = '';

  const scroll = (node: HTMLElement): void => {
    const page = (node.parentNode as HTMLElement).getElementsByClassName('ui-page')[0] as HTMLElement;

    if (page?.style) {
      // Add navbar shadow highlighting
      f7.$(page)
        .scroll(() => {
          const scrollPos: number = f7.$(page)
            .scrollTop();
          let navbar: Element | null | undefined;
          // Navbar
          if (f7.$(page)
            .hasClass('page-master')) {
            navbar = page.parentNode?.parentNode?.parentNode?.querySelector('.navbars > .navbar-master') ?? null;
          } else {
            navbar = page.parentNode?.parentNode?.parentNode?.querySelector('.navbars > .navbar-current') ?? null;
          }

          // Subnavbar support
          if (page.parentNode?.parentNode?.parentNode?.querySelector('.navbars > .navbar-current > .navbar-inner > .subnavbar')) {
            navbar = page.parentNode?.parentNode?.parentNode?.querySelector('.navbars > .navbar-current > .navbar-inner > .subnavbar');
          }

          // Add effects to navbar
          if (navbar) {
            if (scrollPos > 0) {
              f7.$(navbar)
                .addClass('navbar-scroll');
            } else {
              f7.$(navbar)
                .removeClass('navbar-scroll');
            }
          }
        });
    }
  };
</script>

<Page {...$$restProps} on:pageAfterIn>
  <slot name="fixed" />
  <div class="ui-page" {style} use:scroll>
    <slot />
  </div>
</Page>

