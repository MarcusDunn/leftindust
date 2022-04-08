<script lang="ts">
  import type { CardProps } from '@/features/Widgets';
  
  import IcdsSpecificEngine from '@/api/server/engines/icd/IcdsSpecificEngine';
  
  import { _ } from '@/language';

  import {
    Button,
    Row,
    Col,
    Icon,
  } from 'framework7-svelte';
  
  import Card from '@/features/Widgets/components/Card/Card.svelte';

  const { dragger, data, attachments } = $$props as CardProps;

  const { icds } = IcdsSpecificEngine({
    icdCode: data.id,
  });

  $: icd = $icds[0];
</script>

<Card
  color="blue"
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
                $attachments = $attachments.filter((attachment) => attachment.id !== data.id);
            }}
          >
            <Icon f7="trash_fill" />
            {$_('generics.delete')}
          </Button>
        {/if}
      </Col>

      <Col width="50">
        <Button
          round
          outline
          color="blue"
        >
          {$_('generics.learnMore')}
        </Button>
      </Col>
    </Row>
  </svelte:fragment>
</Card>
