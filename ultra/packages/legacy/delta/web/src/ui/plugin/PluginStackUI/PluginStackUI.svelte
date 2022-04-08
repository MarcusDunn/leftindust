<script lang="ts">
  import { Block } from 'framework7-svelte';
  import IconButtonUI from '../../controller/IconButtonUI/IconButtonUI.svelte';
  import CollapsableContentUI from '../../layout/CollapsableContentUI/CollapsableContentUI.svelte';

  export let title: string;
  export let color: string;

  export let dragger: (() => void) = () => undefined;

  export let fill = false;

  export let opened = false;

  let optionsOpen = false;

</script>

<Block
  class={
    `ultra-plugin-stack
    ${optionsOpen ? 'ultra-plugin-stack-options-open' : ''}
    ${fill ? 'ultra-plugin-stack-fill' : ''}
 `}
>
  <div class="ultra-plugin-stack-inner">
    <CollapsableContentUI
      title={title}
      bind:opened
      on:pointerdown={dragger}
    >
      <svelte:fragment slot="controls">
        <slot name="controls" />
        <IconButtonUI
          icon={{
            f7: 'gear',
            color,
          }}
          bind:active={optionsOpen}
        />
      </svelte:fragment>
      <div class="ultra-plugin-stack-content">
        <slot />
      </div>
      <div class="ultra-plugin-stack-options">
        <slot name="options" />
      </div>
    </CollapsableContentUI>
  </div>
</Block>
