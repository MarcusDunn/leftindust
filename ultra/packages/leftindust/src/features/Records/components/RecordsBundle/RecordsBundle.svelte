<script lang="ts">
  import type { BundleProps } from '@/features/Widgets';
  import Bundle from '@/features/Widgets/components/Bundle/Bundle.svelte';
  import { _ } from '@/language';
  import Cells from '@/features/UI/components/Cells/Cells.svelte';
  import { Row, Col, Button } from 'framework7-svelte';
  import RecordCell from '@/features/Record/components/RecordCell/RecordCell.svelte';

  const { data, dragger, quicklook } = $$props as BundleProps;

  const configuration = {
    icon: {
      f7: 'doc',
    },
    color: 'deeppurple',
  };
</script>

<Bundle 
  title={$_('generics.forms')}
  icon={configuration.icon}
  color={configuration.color}
  {dragger}
  shadow
>
  <Cells>
    <RecordCell />
  </Cells>
  <Row slot="controls">
    <Col width="100">
      <Button 
        color={configuration.color}
        outline
        round
      >
        {$_('generics.viewAll')}
      </Button>
    </Col>
  </Row>
</Bundle>