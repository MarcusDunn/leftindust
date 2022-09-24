<script lang="ts">
  import { defaultRangeInput, PartialPatientsByRangeQueryDocument, type PartialPatientFragment } from '@/api/server';
  import { operationStore, query } from '@urql/svelte';
  import { Block, Button, Col, Icon, Row } from 'framework7-svelte';
  import { writable } from 'svelte/store';
  import DescriptivePlaceholder from '../App/components/DescriptivePlaceholder/DescriptivePlaceholder.svelte';
  import PatientCheckboxCell from '../Patient/components/PatientCell/PatientCheckboxCell.svelte';
  import Appbar from '../UI/components/Appbar/Appbar.svelte';
  import Cells from '../UI/components/Cells/Cells.svelte';
  import Page from '../UI/components/Page/Page.svelte';

  import { _ } from '@/language';
  import Footer from '../UI/components/Footer/Footer.svelte';

  const selected = writable<string[]>([]);
  let patients: PartialPatientFragment[];

  let generated = false;

  const request = operationStore(PartialPatientsByRangeQueryDocument, {
    range: defaultRangeInput,
  });
  
  query(request);

  $: patients = $request.data?.patientsByRange ?? [];
</script>

<Page>
  <svelte:fragment slot="fixed">
    <Appbar close={{ popup: true }} />
    {#if !generated}
      <Footer>
        <div
          style="
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-top: 25px;
            margin-bottom: 25px;
            padding-left: 10px;
            padding-right: 10px;
          "
        >
          <Button
            disabled={patients.length === 0 || $selected.length === 0}
            large
            fill
            color="deeppurple"
            style="width: 260px;"
            on:click={() => (generated = true)}
          >
            <Icon style="margin-right: 4px" f7="link" /> Generate Link
          </Button>
        </div>
      </Footer>
    {/if}
  </svelte:fragment>
  {#if !generated}
    {#if patients.length > 0}
      <Row class="no-gutter">
        {#each patients as patient}
          <Col width="33" style="margin-bottom: 10px">
            <Cells>
              <PatientCheckboxCell {patient} bind:selected={$selected} />
            </Cells>
          </Col>
        {/each}
      </Row>
    {:else}
      <div
        style="
          display: flex;
          flex-direction: column;
          align-items: center;
          justify-content: center;
          height: calc(100% - 120px);
        "
      >
        <DescriptivePlaceholder 
          title={$_('generics.noPatients')} 
          description="To generate a link for a patient, you must first create a patient"
          link = {{
            label: $_('descriptions.learnMorePatients'),
          }}
        />
      </div>
    {/if}
  {:else}
    <Block>

    </Block>
  {/if}
</Page>