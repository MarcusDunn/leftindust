<script lang="ts">
  import type { BasicEventFragment } from '../../../requests/fragments';
  import type { MappedEvent } from '../../../modules/CalendarModule';
  
  import type { Selectable } from '../../../modules/SelectModule';
  
  import language from '../../../languages';
  
  import CheckboxItemUI from '../../../ui/item/CheckboxItemUI/CheckboxItemUI.svelte';

  export let event: MappedEvent<BasicEventFragment>;
  export let selected: string[];

  export let multiple = false;

  let selectable: Selectable = {
    type: event.__typename,
    id: event.eid.id ?? '',
  };

  let value = JSON.stringify(selectable);

</script>

<CheckboxItemUI
  header={event.date}
  title={event.title}
  text={event.description || language().placeholders.noDescription.text}
  {value}
  {multiple}
  bind:selected={selected}
/>