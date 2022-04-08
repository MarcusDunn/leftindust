<script lang="ts">
  import type { Selectable } from '../../../modules/SelectModule';
  import type { Plugin } from '../../index';

  import language from '../../../languages/index';
  import { pin, pinned } from '../../../modules/PinModule';
  import { ACCOUNT } from '../../../store';

  import { Row, Col, Button } from 'framework7-svelte';
  import { safeNavigate } from '../../../modules/NavigationModule';
  import { AppViews } from '../../../modules/AppModule';

  import PluginBundleUI from '../../../ui/plugin/PluginBundleUI/PluginBundleUI.svelte';
  import PinnableListUI from '../../../ui/list/PinnableListUI/PinnableListUI.svelte';

  import DoctorsReferencedEngine from '../../../engines/doctors/DoctorsReferencedEngine';
  import RequestLayout from '../../../components/layout/RequestLayout.svelte';
  import PluginPlaceholderLayout from '../../../components/layout/PluginPlaceholderLayout.svelte';
  import DoctorPinnableItem from '../../../components/item/doctor/DoctorPinnableItem.svelte';

  export let dragger: () => void | undefined;
  export let selectable: Selectable;
  export let properties: NonNullable<Plugin['properties']>;

  const { request, doctors } = DoctorsReferencedEngine({ selectable });

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
      {#if $doctors.length > 0}
        <PinnableListUI>
          {#each $doctors as doctor, index}
            {#if index < 4}
              <DoctorPinnableItem
                {doctor}
                pinned={pinned({
                  id: doctor.did.id,
                  type: doctor.__typename,
                }, selectable)}
                on:pin={({ detail }) => pin(detail, {
                  id: doctor.did.id,
                  type: doctor.__typename,
                }, selectable)}
                on:click={() => {
                  void safeNavigate(`/doctor/${JSON.stringify({
                    id: doctor.did.id,
                    type: doctor.__typename,
                  })}/`, AppViews.Clients);
                }}
              />
            {/if}
          {/each}
        </PinnableListUI>
      {:else}
        <div style="margin-top: 40px">
          <PluginPlaceholderLayout
            title={language().clients.doctors.placeholders.noDoctors.text}
            description={language().clients.doctors.placeholders.notFound.text}
            link={{
              label: language().clients.doctors.placeholders.notFound.link,
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
          disabled={$doctors.length === 0}
          on:click={() => void safeNavigate(`/people/${JSON.stringify($doctors.map((doctor) => ({
            id: doctor.did.id,
            type: doctor.__typename,
          })))}/`, AppViews.Clients)}
        >
        {language().buttons.viewAll.text}
        </Button>
      </Col>
    </Row>
  </PluginBundleUI>
{/key}
