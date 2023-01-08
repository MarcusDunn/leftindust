<script lang="ts">
  import type { Router } from 'framework7/types';
  import { PatientsByPatientIdQueryDocument, type Data, type PatientFragment, SurveyLinkByPatientIdQueryDocument, type SurveyLinkByPatientIdQueryQuery } from '@/api/server';
    
  import { account } from '../Account/store';
  
  import { Block } from 'framework7-svelte';
  import Page from '../UI/components/Page/Page.svelte';
  import { wizardOpen } from '../Wizard/store';
  import { clientsSelected, clientsSelectedTab } from '../Clients/store';
  import { ClientsTab } from '../Clients';
  import Appbar from '../UI/components/Appbar/Appbar.svelte';
  import Profile from '../UI/components/Profile/Profile.svelte';
  import { WidgetType } from '../Widgets';
  import { _ } from '@/language';
  import Request from '../Server/components/Request/Request.svelte';
  import Boxed from '../UI/components/Boxed/Boxed.svelte';
  import { BoxedSizes } from '../UI/components/Boxed';
  import { ProfileDrawer } from '../UI/components/Profile';
  import Clamp from '../UI/components/Clamp/Clamp.svelte';
  import PatientTags from './components/PatientTags/PatientTags.svelte';
  import EntityTable from '../Entity/components/EntityTable/EntityTable.svelte';
  import DescriptivePlaceholder from '../App/components/DescriptivePlaceholder/DescriptivePlaceholder.svelte';
  import SpecificGrid from '../Widgets/components/Grid/SpecificGrid.svelte';
  import { operationStore, query } from '@urql/svelte';
  import type { WidgetGridProps } from '../Widgets/components/Grid';

  export let f7router: Router.Router;
  export let f7route: Router.Route;
  export let quicklook = false;
  
  let patient: PatientFragment | undefined;
  let completedSurveys: (WidgetGridProps | undefined)[] = [];
  
  const data: Data = JSON.parse(f7route.params.data ?? '{}');

  const request = operationStore(PatientsByPatientIdQueryDocument, {
    patientIds: [{ value: data.id }],
  });

  const surveyRequest = operationStore(SurveyLinkByPatientIdQueryDocument, {
    patientIds: { value: data.id },
  });

  $: patient = $request.data?.patientsByPatientId[0];
  $: surveyRequest.data?.patientsByPatientId[0]?.assignedSurveys;

  query(request);
  query(surveyRequest);
  
  $: completedSurveys = $surveyRequest.data?.patientsByPatientId
    .filter((patientSurvey): patientSurvey is SurveyLinkByPatientIdQueryQuery['patientsByPatientId'][number] => !!patientSurvey)
    .flatMap((patientSurvey) => patientSurvey?.assignedSurveys.map((surveyLink) => surveyLink.completedSurvey) ?? [])
    .reduce<WidgetGridProps[]>((surveyGridProps, completeSurvey) => {
      if (completeSurvey != null) {
        const typeName = completeSurvey.__typename;
        if (typeName == null) throw new Error('Could not resolve record type');
        else return [...surveyGridProps, {
          id: typeName,
          data: {
            id: completeSurvey.id.value,
            type: typeName,
            patient,
          },
          reference: {
            id: patient?.id.value,
            type: patient?.__typename,
          },
        }];
      }
      else return surveyGridProps;
    }, []) ?? [];
</script>
  
<Page
  on:pageAfterIn={() => {
    if (!$wizardOpen && !quicklook) {
      $clientsSelected = [data];
      $clientsSelectedTab = ClientsTab.Patients;
    }
  }}
>
  <svelte:fragment slot="fixed">
    <Appbar
      close={{ popup: quicklook }}
      history={!quicklook}
      {f7router}
      right={!quicklook ? [
        {
          title: $_('generics.edit'),
          icon: { f7: 'pencil_outline', color: 'gray' },
          condense: true,
        },
      ] : []}
    >
      <svelte:fragment slot="left" />
    </Appbar>
  </svelte:fragment>
  <Request {...$request} refetch={request.reexecute} large middle>
    <Profile drawer={{ ...ProfileDrawer, name: $_('generics.pinned') }}>
      <h2 slot="title">
        {patient?.firstName}
        {#if patient?.middleName}
          <Clamp text={patient?.middleName} />
        {/if}
        {patient?.lastName}
      </h2>
      <Boxed slot="media" dimensions={BoxedSizes.Large} color="gray" round fill>
        <span>{`${patient?.firstName.charAt(0)}${patient?.lastName.charAt(0)}`}</span>
      </Boxed>
      <PatientTags slot="tags" {...patient} />
      <EntityTable
        phones={patient?.phoneNumbers}
        emails={patient?.emails}
        addresses={patient?.addresses}
      />
      <div slot="drawer">
        {#key $account}
          {#if $account.database.layout.pinned['Patient']?.[patient?.id.value]?.length ?? 0 > 0}
            <SpecificGrid
              props={$account.database.layout.pinned['Patient']?.[patient?.id.value]?.map(({ type, id }) => {
                if (type) {
                  return {
                    id: type,
                    data: { id, type },
                    reference: {
                      id: patient?.id.value,
                      type: 'Patient',
                    },
                    quicklook,
                  };
                }
              }) ?? []}
              type={WidgetType.Card}
            />
          {:else}
            <div style="margin-top: 40px">
              <DescriptivePlaceholder
                title={$_('generics.noPinned')}
                description={$_('descriptions.addPinned')}
                link={{
                  label: $_('descriptions.learnMorePinning'),
                  href: '#',
                }}
              />
            </div>
            <br />
          {/if}
        {/key}
      </div>
    </Profile>
    {#if completedSurveys.length > 0}
      <Block>
        <SpecificGrid 
          props={completedSurveys}
          type={WidgetType.Card}
        />
      </Block>
    {:else}
      <div style="margin-top: 40px">
        <DescriptivePlaceholder
          title="No forms or surveys have been recorded"
          description="No forms or surveys found..."
          link = {{
            label: 'Learn more about adding forms and surveys...',
          }}
        />
      </div>
    {/if}
  </Request>
</Page>