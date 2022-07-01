<script lang="ts">
  import type { Framework7Icon } from '@/features/UI';
  import { account } from '@/features/Account/store';

  import './Stack.scss';

  import { Block, Icon} from 'framework7-svelte';

  export let title: string;
  export let icon: Framework7Icon | undefined = undefined;
  export let color: string;

  export let dragger: (() => void) = () => undefined;
  export let fill = false;
  export let shadow = false;
  
  let optionsOpen = false;

</script>

{#key $account}
  <Block
    class={`widget-stack
      ${optionsOpen ? 'widget-stack-options-open' : ''}
      ${shadow ? 'elevation-4' : ''}
    `}
    strong
    inset
  >
    <div class="widget-stack-inner">
      <div class="widget-stack-header">
        <h4 class= {`widget-stack-title text-color-${color}`} on:pointerdown={dragger}>
          {#if icon}
            <Icon {...icon} />
          {/if}
          {title}
        </h4>
        <span class="flex-grow" /> 
        <div>
          <slot name="controls" />
        </div>
      </div>  
      <div class="widget-stack-content">
        <slot />
      </div>
    </div>
  </Block>
{/key}