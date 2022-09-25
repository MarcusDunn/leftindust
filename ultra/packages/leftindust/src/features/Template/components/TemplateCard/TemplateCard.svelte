<script lang="ts">
  import { client, CreateSurveyLinkMutationDocument, PartialSurveyTemplateByIdQueryDocument, type PartialTemplateFragmentFragment } from '@/api/server';
  import { Button, Chip, Col, Icon, Row } from 'framework7-svelte';
  import { mutation, operationStore, query } from '@urql/svelte';
  
  import Card from '@/features/Widgets/components/Card/Card.svelte';

  import { _ } from '@/language';
  import type { CardProps } from '@/features/Widgets';
  import type { Popup, Popover } from 'framework7/types';
  import Quicklook from '@/features/View/components/Quicklook/Quicklook.svelte';
  import { AppViews } from '@/features/App';
  import { openPopover, openPopup, openPopupUrl } from '@/features/View';
  import type { MenuItem } from '@/features/UI/components/Menu';
  import Menu from '@/features/UI/components/Menu/Menu.svelte';
  import TemplateTags from '../TemplateTags/TemplateTags.svelte';

  const { data, dragger } = $$props as CardProps;
  
  let quicklookPopup: Popup.Popup;
  let menuPopup: Popover.Popover;
  
  const request = operationStore(PartialSurveyTemplateByIdQueryDocument, {
    surveyTemplateId: { value: data.id },
  });

  const menuItems: MenuItem[] = [{
    title: 'Send Link',
    text: 'Send a link to somebody outside of this clinic',
    icon: {
      f7: 'link_circle_fill',
      color: 'blue',
    },
    onClick: () => {
      openPopupUrl('/assign/template/', {
        data,
      });
    /*
      client.mutation(CreateSurveyLinkMutationDocument, {
        surveyTemplateId: { value: data.id },
      }).toPromise()
        .then(({ data }) => {
          console.log(data);
        }).catch((error) => {
          console.log(error);
        });
        */
    },
  }];

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

<Menu
  items={menuItems}
  bind:instance={menuPopup}
/>

<Card
  title={template?.title}
  color="deeppurple"
  shadow
  {dragger}
  loading={!template}
  menu
  on:menu={({ detail: event }) => openPopover(menuPopup, event)}
>
  <div style="margin-top: 6px" slot="subtitle">
    <TemplateTags {template} />
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