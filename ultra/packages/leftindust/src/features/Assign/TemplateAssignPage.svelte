<script lang="ts">
  // TODO: Data structure of this file is sort of a mess. We should clean it up.
  import { client, CreateSurveyLinkMutationDocument, defaultRangeInput, PartialPatientsByRangeQueryDocument, PatientsByPatientIdQueryDocument, type Data, type PartialPatientFragment, type PatientFragment } from '@/api/server';
  import { operationStore, query } from '@urql/svelte';
  import { Block, BlockFooter, Button, Col, Icon, Row } from 'framework7-svelte';
  import { writable } from 'svelte/store';
  import DescriptivePlaceholder from '../App/components/DescriptivePlaceholder/DescriptivePlaceholder.svelte';
  import PatientCheckboxCell from '../Patient/components/PatientCell/PatientCheckboxCell.svelte';
  import Appbar from '../UI/components/Appbar/Appbar.svelte';
  import Page from '../UI/components/Page/Page.svelte';

  import { _ } from '@/language';
  import Footer from '../UI/components/Footer/Footer.svelte';
  import Input from '../Input/Input.svelte';
  import getNativeAPI from '@/api/bridge';

  const { Dialog } = getNativeAPI();

  export let data: Data;

  const selected = writable<string[]>([]);

  let patients: PartialPatientFragment[];

  let generated = false;
  let link = '';
  let patient: PatientFragment | undefined;

  let linkInputRef: HTMLInputElement;

  const request = operationStore(PartialPatientsByRangeQueryDocument, {
    range: defaultRangeInput,
  });

  const showErrorDialog = (error: Error) => void Dialog.alert({
    message: $_('errors.internalError'),
    detail: error.message,
    buttons: [$_('generics.ok')],
    defaultId: 0,
  });

  const generateLink = () => {
    const selectedItem: Data = JSON.parse($selected[0]);
    
    if ($selected.length === 0) return;

    client.mutation(CreateSurveyLinkMutationDocument, {
      createSurveyLink: {
        surveyTemplateId: { value: data.id },
        patientId: { value: selectedItem.id },
      },
    }).toPromise()
      .then(({ data: surveyLinkData }) => {
        if (!surveyLinkData) return;
        client.query(PatientsByPatientIdQueryDocument, {
          patientIds: [{ value: selectedItem.id }],
        })
          .toPromise().then(({ data: patientValue }) => {
            if (!patientValue) return;

            link = `${window.location.host}/apps/intake/?id=${surveyLinkData.createSurveyLink.id.value}`;
            patient = patientValue.patientsByPatientId[0];
            generated = true;
          }).catch((error) => {
            showErrorDialog(error);
          });
      }).catch((error) => {
        showErrorDialog(error);
      });
  };

  const goBackToGenerateLink = () => {
    $selected = [];
    patient = undefined;
    link = '';
    generated = false;
  };

  const generateSmsMessageBodyText = (name: string, link: string) => `Hello ${name},\nHere is the link to the medical survey sent by Dr. ___\n\n${link}\n\nIf you have any questions, please contact your clinic.`;
  
  query(request);

  $: patients = $request.data?.patientsByRange ?? [];
</script>

<Page>
  <svelte:fragment slot="fixed">
    <Appbar close={{ popup: true }} />
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
        {#if !generated}
          <Button
            disabled={patients.length === 0 || $selected.length === 0}
            large
            fill
            color="deeppurple"
            style="width: 260px;"
            on:click={generateLink}
          >
            <Icon style="margin-right: 4px" f7="link" /> Send Link
          </Button>
        {:else}
          <Button
            large
            fill
            color="deeppurple"
            style="width: 260px;"
            on:click={goBackToGenerateLink}
          >
            <Icon style="margin-right: 4px" f7="link" /> Send another Link
          </Button>
        {/if}
      </div>
    </Footer>
  </svelte:fragment>
  {#if !generated}
    {#if patients.length > 0}
      <Block style="margin-left: 15px;margin-right: 15px">
        <Row>
          {#each patients as patient}
            <Col width="50" style="margin-bottom: 10px">
              <PatientCheckboxCell {patient} bind:selected={$selected} />
            </Col>
          {/each}
        </Row>
      </Block>
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
    <Block style="margin-left: 15px;margin-right: 15px">
      <h4>Share Link</h4>
      <BlockFooter class="no-margin-top">
        Your link has been successfully generated.
        Share the link below with one of the following methods listed below.
      </BlockFooter>
      <div style="margin-top: 20px">
        <Input style="margin-right: 5px">
          <div class="display-flex" style="align-items: center">
            <input
              type="text"
              placeholder="Generating Link..."
              readonly
              value={link}
              bind:this={linkInputRef}
            />
            <Button
              round
              outline
              color="blue"
              style="margin-right: 10px"
              on:click={() => {
                linkInputRef.select();
                document.execCommand('copy');
              }}
            >
              <Icon f7="doc_on_doc_fill" style="margin-right: 4px" /> Copy
            </Button>
          </div>
        </Input>
        <br />
        {#if patient}
          <div class="display-flex">
            <Button
              round
              fill
              style="margin-right: 10px"
              on:click={() =>(window.location.href = `mailto:${patient?.emails[0].email}`)}
              disabled
            >
              <Icon f7="envelope_fill" style="margin-right: 4px" /> Email
            </Button>
            <Button
              round
              fill
              color="green"
              style="margin-right: 10px"
              on:click={() => (
                window.location.href =
                  `sms:${patient?.phoneNumbers[0].number}
                  &body=${generateSmsMessageBodyText(patient?.firstName ?? '', link)}
                  `)}
              disabled
            >
              <Icon f7="bubble_left_fill" style="margin-right: 4px" /> Text/SMS
            </Button>
          </div>
          <p>Auto-messaging and emailing has been disabled by your administrator</p>
        {/if}
      </div>
    </Block>
  {/if}
</Page>