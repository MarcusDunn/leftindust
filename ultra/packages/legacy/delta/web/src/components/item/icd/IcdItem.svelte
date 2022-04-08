<script lang="ts">
  import type { BasicIcdFragment } from '../../../requests/fragments';

  import { Button, Icon, ListItem } from 'framework7-svelte';
  import sanitizeHtml from 'sanitize-html';

  import { createEventDispatcher } from 'svelte';

  const dispatch = createEventDispatcher();

  export let icd: BasicIcdFragment;
  export let link = true;
</script>

<style lang="scss">
  span {
    & :global(em) {
      font-style: normal;
      color: var(--f7-color-blue);
    }
  }
</style>

<ListItem
  text={icd.code}
  {link}
  on:click
>
  <svelte:fragment slot="content-end">
    <Button
      fill
      small
      color="red"
      round
      on:click={() => dispatch('delete')}
    >
      <Icon f7="trash_fill" />
      Delete
    </Button>
  </svelte:fragment>
  <span slot="title">
    {@html sanitizeHtml(icd.title ?? '')}
  </span>
</ListItem>
