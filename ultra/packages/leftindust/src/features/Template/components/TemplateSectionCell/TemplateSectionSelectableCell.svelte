<script lang="ts">
  import type { Writable } from 'svelte/types/runtime/store';

  import type { Data, PartialTemplateFragmentFragment } from '@/api/server';

  import { createEventDispatcher, tick } from 'svelte';

  import { click } from '@/features/Select';

  import Cell from '@/features/UI/components/Cell/Cell.svelte';

  import TemplateSectionTags from '../TemplateSectionTags/TemplateSectionTags.svelte';

  const dispatch = createEventDispatcher();
  
  export let section: PartialTemplateFragmentFragment['sections'][number];
  export let sections: PartialTemplateFragmentFragment['sections'];
  export let selected: Writable<Data[]>;

  export let multiselect = true;

</script>

<Cell
  title={section.title}
  selected={$selected.some((selectable) => selectable.id === section.id?.value)}
  on:click={(event) => {
    let selectable = { id: section.id?.value, type: section.__typename };
    let reference = sections.map((item) => ({ id: item.id?.value, type: item.__typename }));

    $selected = click(event, { selectable, multiselect: multiselect ? { selected: $selected, reference } : undefined });

    void tick().then(() => {
      dispatch('selected');
    });
  }}
>
  <TemplateSectionTags slot="text" {section} small outline />
</Cell>
