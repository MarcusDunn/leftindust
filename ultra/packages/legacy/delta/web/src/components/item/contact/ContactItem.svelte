<script lang="ts">
  import type { ContactsFragment } from '../../../requests/fragments';

  import { ListItem } from 'framework7-svelte';

  import ContactTags from '../../../components/tags/ContactTags.svelte';

  export let contact: Partial<ContactsFragment>;
  export let link = true;
</script>

<ListItem
  title={`${contact.firstName} ${contact.lastName}`}
  {link}
  on:click
>
  <svelte:fragment slot="text">
    <ContactTags
      relationship={contact.relationship}
      phone={contact.phones?.[0]?.number}
      small
      outline
    />
  </svelte:fragment>
</ListItem>
