<script lang="ts">
  import type { Selectable } from '../../../modules/SelectModule';
  import type { Writable } from 'svelte/store';
  import type { Plugin } from '../../';
  
  import IcdsSpecificEngine from '../../../engines/icd/IcdsSpecificEngine';
  
  import language from '../../../languages/index';

  import {
    Button,
    Row,
    Col,
    Icon,
  } from 'framework7-svelte';
  
  import PluginWidgetUI from '../../../ui/plugin/PluginWidgetUI/PluginWidgetUI.svelte';
  
  export let dragger: () => void | undefined;
  export let selectable: Selectable<'IcdLinearizationEntity' | 'IcdSimpleEntity'>;
  export let properties: NonNullable<Plugin['properties']>;
  export let attachments: Writable<Selectable[]> | undefined = undefined;

  const { icds } = IcdsSpecificEngine({
    icdCode: selectable.id,
  });

  $: icd = $icds[0];
</script>


<PluginWidgetUI
  color={properties.color}
  {dragger}
  loading={!icd}
  shadow={!attachments}
>
  <svelte:fragment slot="title">
    <div class="two-line-word-clamp">{icd.title}</div>
  </svelte:fragment>

  <svelte:fragment slot="description">
    {icd.code}
  </svelte:fragment>

  <svelte:fragment slot="controls">
    <Row>
      <Col width="50">
        {#if attachments}
          <Button
            round
            fill
            color="red"
            on:click={() => {
              if (attachments && $attachments)
                $attachments = $attachments.filter((attachment) => attachment.id !== selectable.id);
            }}
          >
            <Icon f7="trash_fill" />
            {language().buttons.delete.text}
          </Button>
        {/if}
      </Col>

      <Col width="50">
        <Button
          round
          outline
          color="blue"
        >
          {language().buttons.learnMore.text}
        </Button>
      </Col>
    </Row>
  </svelte:fragment>
</PluginWidgetUI>
