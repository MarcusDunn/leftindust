<script lang="ts">
  import { Link, Icon } from 'framework7-svelte';
  import type { SmartSelect } from 'framework7/types';
  import type { Framework7Icon } from '../..';

  import './MenuButton.scss';

  export let title: string | undefined = undefined;
  export let icon: Framework7Icon | undefined = undefined;
  export let disabled = false;
  export let smartSelect = false;
  export let smartSelectParams: SmartSelect.SmartSelect['params'] | undefined = undefined;

</script>

<div class={`${disabled ? 'disabled' : ''} ui-menu_button`} on:click>
  <Link
    class={`ui-menu_button-link ${title ? 'ui-menu_button-tall' : ''}`}
    {smartSelect}
    smartSelectParams={{ openIn: 'popover', closeOnSelect: true, ...smartSelectParams }}
  >
    {#if icon}
      <Icon {...icon} />
    {/if}
    {#if !icon || smartSelect}
      <slot />
    {/if}
    {#if title}
      <div class="ui-menu_button-title">
        <div class="ui-menu_button-title-label">{title || ''}</div>
      </div>
    {/if}
  </Link>
</div>
