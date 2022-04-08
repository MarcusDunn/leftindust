<script lang="ts">
  import type { Plugin } from '../../';
  import type { Selectable } from '../../../modules/SelectModule';
  
  import ContactsReferencedEngine from '../../../engines/contacts/ContactsReferencedEngine';
  
  import language from '../../../languages/index';
  import { Row, Col, Button } from 'framework7-svelte';
  import PluginBundleUI from '../../../ui/plugin/PluginBundleUI/PluginBundleUI.svelte';
  import PinnableListUI from '../../../ui/list/PinnableListUI/PinnableListUI.svelte';
  
  import RequestLayout from '../../../components/layout/RequestLayout.svelte';
  import PluginPlaceholderLayout from '../../../components/layout/PluginPlaceholderLayout.svelte';
  import ContactItem from '../../../components/item/contact/ContactItem.svelte';

  export let dragger: () => void | undefined;
  export let properties: NonNullable<Plugin['properties']>;
  export let selectable: Selectable;

  const { request, contacts } = ContactsReferencedEngine({ selectable });

</script>

<PluginBundleUI
  title={properties.title}
  icon={properties.icon}
  color={properties.color}
  {dragger}
  shadow
>
  <RequestLayout {...$request} refetch={request.refetch} middle>
    {#if $contacts.length > 0}
      <PinnableListUI>
        {#each $contacts as contact}
          <ContactItem {contact} />
        {/each}
      </PinnableListUI>
    {:else}
      <div style="margin-top: 40px">
        <PluginPlaceholderLayout
          title={language().clients.emergencyContacts.placeholders.noEmergencyContacts.text}
          description={language().clients.emergencyContacts.placeholders.notFound.text}
          link={{
            label: language().clients.emergencyContacts.placeholders.notFound.link,
            href: '#',
          }}
        />
      </div>
    {/if}
  </RequestLayout>

  <Row slot="controls">
    <Col width="100">
      <Button
        color={properties.color}
        outline
        round
        disabled={$contacts.length === 0}
      >
        {language().buttons.viewAll.text}
      </Button>
    </Col>
  </Row>
</PluginBundleUI>
