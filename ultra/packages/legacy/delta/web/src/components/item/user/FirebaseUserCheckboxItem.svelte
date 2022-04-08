<script lang="ts">
  import type { BasicFirebaseUserFragment } from '../../../requests/fragments';
  
  import type { Selectable } from '../../../modules/SelectModule';
  
  import CheckboxItemUI from '../../../ui/item/CheckboxItemUI/CheckboxItemUI.svelte';

  import FirebaseUserTags from '../../../components/tags/FirebaseUserTags.svelte';

  export let user: BasicFirebaseUserFragment;
  export let selected: string[];

  export let multiple = false;

  let selectable: Selectable = {
    type: user.__typename,
    id: user.uid ?? '',
  };

  let value = JSON.stringify(selectable);

</script>

<CheckboxItemUI
  title={user.email}
  {value}
  {multiple}
  bind:selected={selected}
>
  <FirebaseUserTags slot="text" {...user} small outline />
</CheckboxItemUI>
