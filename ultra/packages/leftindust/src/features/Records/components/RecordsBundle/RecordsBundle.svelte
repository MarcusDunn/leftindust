<script lang="ts">
  import type { BundleProps } from '@/features/Widgets';
  import Bundle from '@/features/Widgets/components/Bundle/Bundle.svelte';
  import { _ } from '@/language';
  import Cells from '@/features/UI/components/Cells/Cells.svelte';
  import { Row, Col, Button } from 'framework7-svelte';
  import RecordCell from '@/features/Record/components/RecordCell/RecordCell.svelte';
  import DescriptivePlaceholder from '@/features/App/components/DescriptivePlaceholder/DescriptivePlaceholder.svelte';
  import { operationStore, query } from '@urql/svelte';
  import { SurveyLinkByPatientIdQueryDocument, type CompleteSurvey, type SurveyLinkByPatientIdQueryQuery, type SurveyLinkFragmentFragment } from '@/api/server';
  import Request from '@/features/Server/components/Request/Request.svelte';
  import RecordPinnableCell from '@/features/Record/components/RecordCell/RecordPinnableCell.svelte';
  import { pin, pinned } from '@/features/Pin';

  const { data, dragger, quicklook } = $$props as BundleProps;

  let completedSurveys: CompleteSurvey[] = [];

  const request = operationStore(SurveyLinkByPatientIdQueryDocument, {
    patientIds: { value: data.id },
  });

  query(request);

  $request.data?.patientsByPatientId[0]?.assignedSurveys;

  const configuration = {
    icon: {
      f7: 'doc',
    },
    color: 'deeppurple',
  };

  // @ts-expect-error
  $: completedSurveys = $request.data?.patientsByPatientId.filter(
    (patient): patient is SurveyLinkByPatientIdQueryQuery['patientsByPatientId'][number] => !!patient,
  )
    .flatMap((patient) => patient?.assignedSurveys.filter(
      (surveyLink) => surveyLink.completedSurvey,
    )
      .map((surveyLink) => surveyLink.completedSurvey) ?? [])

    .filter((completedSurvey) => !!completedSurvey)
    ?? [];
</script>

<Bundle 
  title={$_('generics.forms')}
  icon={configuration.icon}
  color={configuration.color}
  {dragger}
  shadow
>
  <Request {...$request} refetch={request.reexecute} middle>
    {#if completedSurveys.length > 0}
      <Cells>
        {#each completedSurveys as record}
          {#if record}
            <RecordPinnableCell
              {record}
              pinned={pinned({
                id: record.id?.value,
                type: record.__typename,
              }, data)}
              on:pin={({ detail }) => pin(detail, {
                id: record.id?.value,
                type: record.__typename,
              }, data)}
            />
          {/if}
        {/each}
      </Cells>
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

  <Row slot="controls">
    <Col width="100">
      <Button 
        color={configuration.color}
        outline
        round
        disabled
      >
        {$_('generics.viewAll')}
      </Button>
    </Col>
  </Row>
</Bundle>