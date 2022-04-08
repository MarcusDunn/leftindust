<script lang="ts">
  import { f7 } from 'framework7-svelte';

  import { createEventDispatcher } from 'svelte';
  import WizardUI from './WizardUI.svelte';
  
  const dispatch = createEventDispatcher();
  
  export let title: string | undefined = undefined;
  export let subtitle: string | undefined = undefined;
  export let color = 'purple';

  export let disabled = true;

  const scroll = (node: HTMLElement): void => {
    const page = node;

    if (page?.style) {
      // Add navbar shadow highlighting
      f7.$(page)
        .scroll(() => {
          const scrollPos: number = f7.$(page)
            .scrollTop();
          let navbar: Element | null | undefined =
            page.parentNode?.parentNode?.parentNode?.parentNode?.parentNode?.querySelector('.navbars > .navbar-current') ?? null;
            
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

<WizardUI
  class="ultra-wizard-split"
  {title}
  {subtitle}
  {disabled}
  {color}
  overflow={false}
  on:submit={() => dispatch('submit')}
  on:close={() => dispatch('close')}
>
  <svelte:fragment slot="appbar">
    <slot name="appbar" />
  </svelte:fragment>
  <div class="ultra-wizard-split-container">
    <div class="ultra-wizard-split-master" use:scroll>
      <br />
      <br />
      <slot />
      <br />
      <br />
    </div>
    <div class="ultra-wizard-split-detail">
      <slot name="detail" />
    </div>
  </div>
</WizardUI>