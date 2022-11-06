<script lang="ts">
  import { CompleteSurveyByIdQueryDocument, type CompleteSurveyFragmentFragment, type Data, type PartialPatientFragment } from '@/api/server';
  import { operationStore, query } from '@urql/svelte';
  import type { Router } from 'framework7/types';
  import Appbar from '../UI/components/Appbar/Appbar.svelte';
  import Page from '../UI/components/Page/Page.svelte';
  import { _ } from '@/language';
  import Request from '../Server/components/Request/Request.svelte';
  import Profile from '../UI/components/Profile/Profile.svelte';
  import { Block } from 'framework7-svelte';
  import CollapsableContent from '../UI/components/Collapsable/CollapsableContent.svelte';
  import RecordTags from './components/RecordTags/RecordTags.svelte';
  import Input from '../Input/Input.svelte';
  import SpecificGrid from '../Widgets/components/Grid/SpecificGrid.svelte';
  import { WidgetType } from '../Widgets';
  import { clientsSelected, clientsSelectedTab } from '../Clients/store';
  import { ClientsTab } from '../Clients';
  import { wizardOpen } from '../Wizard/store';
  import RecordComputedCalculations from './components/RecordComputedCalculations/RecordComputedCalculations.svelte';
  import type { RecordValues } from '.';

  export let f7router: Router.Router;
  export let f7route: Router.Route;

  export let quicklook = false;

  let record: CompleteSurveyFragmentFragment | undefined;

  const data: { patient: Data; completedSurvey: Data } = JSON.parse(f7route.params.data ?? '{}');

  const request = operationStore(CompleteSurveyByIdQueryDocument, {
    completeSurveyId: { value: data.completedSurvey.id },
  });

  let values: RecordValues;

  query(request);

  $: record = $request.data?.completeSurveyById;

  $: if (record) {
    values = record.surveyTemplate.sections.map(({ inputs: templateInputs, id }, sectionIndex) => ({
      id,
      inputs: templateInputs.map((input, inputIndex) => ({
        id: input.id,
        value: (() => {
          // I'm not sure why the typechecker doesn't see the null evaluation above
          // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
          const input = record!.sections[sectionIndex].inputs[inputIndex];
  
          if ('string' in input) {
            return input.string;
          } else if ('number' in input) {
            return input.number;
          } else if ('stringArray' in input) {
            return input.stringArray;
          }
  
          return;
        })(),
      })),
    }));
  }
</script>

<Page
  on:pageAfterIn={() => {
    if (!$wizardOpen && !quicklook) {
      $clientsSelected = [data.patient];
      $clientsSelectedTab = ClientsTab.Patients;
    }
  }}
>
  <svelte:fragment slot="fixed">
    <Appbar
      close={{ popover: quicklook }}
      history={!quicklook}
      {f7router}
    />
  </svelte:fragment>
  <Request {...$request} refetch={request.reexecute} large middle>
    {#if record}
      <Profile>
        <h2 slot="title">{record.surveyTemplate.title}</h2>
        <RecordTags slot="tags" {...record} />
      </Profile>
      <Block style="margin: 0 40px">
        <SpecificGrid
          props={[{
            id: data.patient.type,
            data: { id: data.patient.id, type: data.patient.type },
            quicklook,
          }]}
          type={WidgetType.Card}
        />
        <br />
        <br />
        {#if values}
          <RecordComputedCalculations
            sections={record.surveyTemplate.sections}
            calculations={record.surveyTemplate.calculations}
            {values}
          />
          <br />
          <br />
        {/if}
        {#each record.sections as section, sectionIndex}
          <CollapsableContent
            title={record.surveyTemplate.sections[sectionIndex].title}
          >
            {#each section.inputs as input, inputIndex}
              <p />
              <Input title={record.surveyTemplate.sections[sectionIndex].inputs[inputIndex].label}>
                <input
                  type="text"
                  placeholder="Value"
                  readonly
                  value={(() => {
                    if ('string' in input) {
                      return input['string'];
                    } else if ('number' in input) {
                      return input['number'];
                    } else if ('stringArray' in input) {
                      return input['stringArray'];
                    } else if ('numberArray' in input) {
                      return input['numberArray'];
                    }
                  })()}
                />
              </Input>
              <br />
            {/each}
          </CollapsableContent>
        {/each}
      </Block>
    {/if}
  </Request>
</Page>