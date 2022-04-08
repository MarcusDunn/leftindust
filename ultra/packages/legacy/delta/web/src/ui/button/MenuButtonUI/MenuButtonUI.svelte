<script lang="ts">
  import { Link, Icon } from 'framework7-svelte';
  import type { SmartSelect } from 'framework7/types';
  import type { Framework7Icon } from '../../../modules/UIModule';

  export let title: string | undefined = undefined;
  export let icon: Framework7Icon | undefined = undefined;
  export let disabled = false;
  export let smartSelect = false;
  export let smartSelectParams: SmartSelect.SmartSelect['params'] | undefined = undefined;

</script>

<div class={`${disabled ? 'disabled' : ''} ultra-menubutton`} on:click>
  <Link
    class={`ultra-menubutton-link ${title ? 'ultra-menubutton-tall' : ''}`}
    smartSelect={smartSelect}
    smartSelectParams={{ openIn: 'popover', closeOnSelect: true, ...smartSelectParams }}
  >
    <Icon {...icon} />
    {#if !icon || smartSelect}
      <slot />
    {/if}
    <div class="ultra-menubutton-title"><span>{title || ''}</span></div>
  </Link>
</div>
