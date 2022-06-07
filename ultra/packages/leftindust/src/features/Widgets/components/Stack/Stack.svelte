<script lang="ts">
  import { Block } from 'framework7-svelte';

  export let title: string;
  export let color: string;

  import './Stack.scss';

  export let dragger: (() => void) = () => undefined;

  export let fill = false;

  let optionsOpen = false;

</script>

<Block
  class={`widget-stack
    ${optionsOpen ? 'widget-stack-options-open' : ''}
    ${fill ? 'widget-stack-fill' : ''}
  `}
>
  <div class="widget-stack-inner">
    <h4 class="widget-stack-title" on:pointerdown={dragger}>
      {title}
      <span class="flex-grow" />
      <slot name="controls" />
    </h4>
    <div class="widget-stack-content">
      <slot />
    </div>
    <div class="widget-stack-options">
      <slot name="options" />
    </div>
  </div>
</Block>
