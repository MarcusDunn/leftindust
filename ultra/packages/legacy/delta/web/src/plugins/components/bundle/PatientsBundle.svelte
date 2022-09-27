<script lang="ts">
  import type { Selectable } from '../../../modules/SelectModule';
  import type { Plugin } from '../../index';

  import language from '../../../languages/index';

  import { Row, Col, Button } from 'framework7-svelte';
  import { safeNavigate } from '../../../modules/NavigationModule';
  import { AppViews } from '../../../modules/AppModule';
  import PatientsReferencedEngine from '../../../engines/patients/PatientsReferencedEngine';
  import { pinned, pin } from '../../../modules/PinModule';

  import { ACCOUNT } from '../../../store';
  import PluginBundleUI from '../../../ui/plugin/PluginBundleUI/PluginBundleUI.svelte';
  import PinnableListUI from '../../../ui/list/PinnableListUI/PinnableListUI.svelte';

  import RequestLayout from '../../../components/layout/RequestLayout.svelte';
  import PluginPlaceholderLayout from '../../../components/layout/PluginPlaceholderLayout.svelte';
  import PatientPinnableItem from '../../../components/item/patient/PatientPinnableItem.svelte';

  export let dragger: () => void | undefined;
  export let selectable: Selectable;
  export let properties: NonNullable<Plugin['properties']>;

  const { request, patients } = PatientsReferencedEngine({ selectable });
</script>

{#key $ACCOUNT}
  <PluginBundleUI
    title={properties.title}
    icon={properties.icon}
    color={properties.color}
    {dragger}
    shadow
  >
    <RequestLayout {...$request} refetch={request.refetch} middle>
      {#if $patients.length > 0}
        <PinnableListUI>
          {#each $patients as patient, index}
            {#if index < 4}
              <PatientPinnableItem
                {patient}
                on:click={() => {
                  void safeNavigate(`/patient/${JSON.stringify({
                    id: patient.id.value,
                    type: patient.__typename,
                  })}/`, AppViews.Clients);
                }}
                pinned={pinned({
                  id: patient.id.value,
                  type: patient.__typename,
                }, selectable)}
                on:pin={({ detail }) => pin(detail, {
                  id: patient.id.value,
                  type: patient.__typename,
                }, selectable)}
              />
            {/if}
          {/each}
        </PinnableListUI>
      {:else}
        <div style="margin-top: 40px">
          <PluginPlaceholderLayout
            title={language().clients.patients.placeholders.noPatients.text}
            description={language().clients.patients.placeholders.notFound.text}
            link={{
              label: language().clients.patients.placeholders.notFound.link,
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
          disabled={$patients.length === 0}
          on:click={() => void safeNavigate(`/people/${JSON.stringify($patients.map((patient) => ({
            id: patient.id.value,
            type: patient.__typename,
          })))}/`, AppViews.Clients)}
        >
          {language().buttons.viewAll.text}
        </Button>
      </Col>
    </Row>
  </PluginBundleUI>
{/key}
