<script lang="ts">
  import { Template } from '../../store';
  import { _ } from 'svelte-i18n';
  import {
    Button,
    Chip,
    Col,
    Icon,
    Row,
  } from 'framework7-svelte';
  import { format } from 'date-fns';
  import Card from '@/features/Widgets/components/Card/Card.svelte';
  import  { type SurveyTemplateInput, SurveyTemplateCategory } from '@/api/server';

  export let shadow = true;

  let date: string;
  let title: string;
  let body: string;

  let inputs: SurveyTemplateInput[] = [];

  $: date = inputs.filter(
    ({ category }) => category === SurveyTemplateCategory.Date,
  )[0]?.label;
  $: title = inputs.filter(
    ({ category }) => category === SurveyTemplateCategory.Title,
  )[0]?.label;
  $: body = inputs.filter(
    ({ category }) => category === SurveyTemplateCategory.Body,
  )[0]?.label;

  $: inputs = $Template.sections.flatMap((section, index) =>
    section.inputs.map((input) => ({
      ...input,
      label: `${input.label}${
        $Template.sections.length > 1
          // eslint-disable-next-line @typescript-eslint/restrict-plus-operands
          ? ` (${$_('generics.sectionIndexed', { values: { number: index + 1 } })})`
          : ''
      }`,
    })),
  );
</script>

<Card color="deeppurple" {shadow}>
  <svelte:fragment slot="header">
    {#if date}
      <Chip
        text={$_('generics.inputOf', { values: { label: date } })}
        outline
        style="padding-right: 15px"
      />
    {:else}
      {format(new Date(), 'MMM dd, yyyy')}
    {/if}
  </svelte:fragment>

  <svelte:fragment slot="title">
    {#if title}
      <Chip
        text={$_('generics.inputOf', { values: { label: title } })}
        outline
        style="padding-right: 15px"
      />
    {:else}
      {$Template.title}
    {/if}
  </svelte:fragment>

  <div style="margin-top: 6px" slot="subtitle">
    {#if !body}
      <Chip text="? Responses" mediaBgColor="blue">
        <span slot="media"><Icon f7="text_cursor" /></span>
      </Chip>
      <Chip text="? Files" mediaBgColor="blue">
        <span slot="media"><Icon f7="paperclip" /></span>
      </Chip>
    {/if}
  </div>

  <svelte:fragment slot="description">
    {#if body}
      <Chip
        text={$_('generics.inputOf', { values: { label: body } })}
        outline
        style="padding-right: 15px"
      />
    {/if}
  </svelte:fragment>

  <svelte:fragment slot="controls">
    <Row>
      <Col width="50">
        <Button round fill>
          {$_('generics.view', {
            values: { label: $Template.title },
          })}
        </Button>
      </Col>

      <Col width="50">
        <Button round outline color="blue">
          <Icon f7="eye_fill" />
          {$_('generics.quicklook')}
        </Button>
      </Col>
    </Row>
  </svelte:fragment>
</Card>
