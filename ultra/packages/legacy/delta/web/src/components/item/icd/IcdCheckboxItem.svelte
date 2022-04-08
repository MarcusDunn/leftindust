<script lang="ts">
  import type { Selectable } from '../../../modules/SelectModule';

  import type { BasicIcdFragment } from '../../../requests/fragments';

  import sanitizeHtml from 'sanitize-html';

  import CheckboxItemUI from '../../../ui/item/CheckboxItemUI/CheckboxItemUI.svelte';

  export let icd: BasicIcdFragment;
  export let selected: string[];

  export let multiple = false;

  let selectable: Selectable = {
    type: icd.__typename,
    id: icd.id ?? '',
  };

  let value = JSON.stringify(selectable);
</script>

<style lang="scss">
  span {
    & :global(em) {
      font-style: normal;
      color: var(--f7-color-blue);
    }
  }
</style>

<CheckboxItemUI
  text={icd.code}
  {value}
  {multiple}
  bind:selected={selected}
>
  <span slot="title">
    {@html sanitizeHtml(icd.title ?? '')}
  </span>
</CheckboxItemUI>