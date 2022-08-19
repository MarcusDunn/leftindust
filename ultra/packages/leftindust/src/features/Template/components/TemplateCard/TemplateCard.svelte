<script lang="ts">
  import { PartialSurveyTemplateByIdQueryDocument, type PartialTemplateFragmentFragment } from '@/api/server';
  import { Button, Chip, Col, Icon, Row } from 'framework7-svelte';
  import { operationStore, query } from '@urql/svelte';
  
  import Card from '@/features/Widgets/components/Card/Card.svelte';

  import { _ } from '@/language';
  import type { CardProps } from '@/features/Widgets';
  import type { Popup } from 'framework7/types';
  import Quicklook from '@/features/View/components/Quicklook/Quicklook.svelte';
  import { AppViews } from '@/features/App';
  import { openPopup } from '@/features/View';

  const { data, dragger, reference, attachments, quicklook } = $$props as CardProps;
  
  let quicklookPopup: Popup.Popup;
  
  const request = operationStore(PartialSurveyTemplateByIdQueryDocument, {
    surveyTemplateId: { value: data.id },
  });

  query(request);

  let template: PartialTemplateFragmentFragment | undefined;

  $: if ($request.data?.surveyTemplateById) template = $request.data?.surveyTemplateById;
  
  const url = `/template/${JSON.stringify(data)}/`;

</script>

<Quicklook
  {url}
  view={AppViews.Templates}
  color="deeppurple"
  bind:instance={quicklookPopup}
/>

<Card
  title={template?.title}
  color="deeppurple"
  shadow
  {dragger}
  loading={!template}
>
  <div style="margin-top: 6px" slot="subtitle">
    <Chip text={`${template?.sections.length} sections`} mediaBgColor="purple">
      <span slot="media"><Icon f7="square_fill_on_square_fill" /></span>
    </Chip>
    <Chip text={`${template?.calculations.length} calculations`} mediaBgColor="teal">
      <span slot="media"><Icon f7="sum" /></span>
    </Chip>
  </div>

  <svelte:fragment slot="controls">
    <Row>
      <Col width="50">
        <Button round fill href={url}>
          {$_('generics.view', {
            values: { label: 'Template' },
          })}
        </Button>
      </Col>

      <Col width="50">
        <Button
          round
          outline
          color="blue"
          on:click={() => {
            openPopup(quicklookPopup);
          }}
        >
          <Icon f7="eye_fill" />
          {$_('generics.quicklook')}
        </Button>
      </Col>
    </Row>
  </svelte:fragment>
</Card>