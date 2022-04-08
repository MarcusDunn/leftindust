<script lang="ts">
import type { Framework7Icon } from '../../../modules/UIModule';

import { Link, Icon } from 'framework7-svelte';

import { createEventDispatcher } from 'svelte';

const dispatch = createEventDispatcher();

export let selectable = true;
export let active = false;
export let icon: Framework7Icon;
</script>

<Link
  class={`
    ultra-icon-button
    ${active ? `ultra-icon-button-active bg-color-${icon.color || 'gray'}` : ''}
 `}
>
  <span
    on:click={(event) => {
      selectable && (active = !active);
      dispatch('click', event);
    }}
  >
    <Icon {...icon} />
  </span>
</Link>
