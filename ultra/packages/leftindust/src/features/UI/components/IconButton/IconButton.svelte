<script lang="ts">
  import type { Framework7Icon } from '../..';

  import { Link, Icon } from 'framework7-svelte';

  import { createEventDispatcher } from 'svelte';

  import './IconButton.scss';

  const dispatch = createEventDispatcher();

  export let selectable = true;
  export let active = false;
  export let icon: Framework7Icon;
</script>

<Link
  class={`
    ui-icon_button
    ${active ? `ui-icon_button-active bg-color-${icon.color || 'gray'}` : ''}
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
