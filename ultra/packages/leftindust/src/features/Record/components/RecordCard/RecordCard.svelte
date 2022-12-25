<script lang="ts">
  import { type PartialCompleteSurveyFragmentFragment, CompleteSurveyByIdQueryDocument } from '@/api/server';
  import { Button, Col, Icon, Row } from 'framework7-svelte';
  import { operationStore, query } from '@urql/svelte';
  
  import Card from '@/features/Widgets/components/Card/Card.svelte';

  import { _ } from '@/language';
  import type { CardProps } from '@/features/Widgets';
  import type { Popup, Popover } from 'framework7/types';
  import Quicklook from '@/features/View/components/Quicklook/Quicklook.svelte';
  import { AppViews } from '@/features/App';
  import { openPopover, openPopup, openPopupUrl } from '@/features/View';
  import type { MenuItem } from '@/features/UI/components/Menu';
  import Menu from '@/features/UI/components/Menu/Menu.svelte';
  import RecordTags from '../RecordTags/RecordTags.svelte';
  import PinButton from '@/features/Pin/components/PinButton/PinButton.svelte';
  import { pin, pinned } from '@/features/Pin';

  const { data, dragger } = $$props as CardProps;

  let quicklookPopup: Popup.Popup;
  let menuPopup: Popover.Popover;

  const request = operationStore(CompleteSurveyByIdQueryDocument, {
    completeSurveyId: { value: data.id },
  });

  const menuItems: MenuItem[] = [{
    title: 'Send Link',
    text: 'Send a link to somebody outside of this clinic',
    icon: {
      f7: 'link_circle_fill',
      color: 'blue',
    },
    onClick: () => {
      openPopupUrl('/assign/record/', {
        data,
      });
    },
  }];

  query(request);

  let survey: PartialCompleteSurveyFragmentFragment | undefined;

  $: if ($request.data?.completeSurveyById) survey = $request.data.completeSurveyById;

  const url = `/record/${JSON.stringify(data)}/`;
</script>

<Quicklook
  {url}
  view={AppViews.Popup}
  color="deeppurple"
  bind:instance={quicklookPopup}
/>

<Menu
  items={menuItems}
  bind:instance={menuPopup}
/>


<Card
  title={survey?.surveyTemplate.title}
  color="deeppurple"
  shadow
  {dragger}
  loading={!survey}
  menu
  on:menu={({ detail: event }) => openPopover(menuPopup, event)}
>
  <div style="margin-top: 6px" slot="subtitle">
    <RecordTags sections={survey?.sections} surveyTemplate={survey?.surveyTemplate}/>
  </div>

  <svelte:fragment slot="controls">
    <!-- <PinButton
      pinned={pinned({
        id: survey?.id.value,
        type: 'CompleteSurvey',
      }, reference)}
      on:pin={({ detail }) => reference && pin(detail, {
        id: survey?.id.value,
        type: survey?.__typename,
      }, reference)}
    /> -->
    <Row>
      <Col width="50">
        <Button round fill href={url}>
          {$_('generics.view', {
            values: { label: 'Record' },
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