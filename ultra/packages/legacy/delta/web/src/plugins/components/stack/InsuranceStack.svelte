<script lang="ts">
  import type { Selectable } from '../../../modules/SelectModule';
  import type { Plugin } from '../../';

  import language from '../../../languages/index';
  import InsuranceReferencedEngine from '../../../engines/insurance/InsuranceReferencedEngine';


  import {
    Block,
    Button,
    Col,
    Row,
  } from 'framework7-svelte';

  import HealthCard from '../../../components/card/HealthCard.svelte';
  import RequestLayout from '../../../components/layout/RequestLayout.svelte';
  import PluginStackUI from '../../../ui/plugin/PluginStackUI/PluginStackUI.svelte';

  export let selectable: Selectable;
  export let dragger: () => void | undefined;
  export let properties: NonNullable<Plugin['properties']>;

  const { request, insuranceNumber } = InsuranceReferencedEngine({ selectable });

</script>

<style lang="scss">
  .ultra-component-insurance-stack-health-card-container {
    & :global(.block) {
      height: 190px;
    }
  }

</style>

<PluginStackUI
  title={properties.title}
  color={properties.color}
  {dragger}
  opened
>
  <div class="ultra-component-insurance-stack-health-card-container">
    <RequestLayout {...$request} refetch={request.refetch}>
      <Row style="margin-top: 30px">
        <Col width="50">
          <Block>
            <HealthCard number={$insuranceNumber ?? ''} />
          </Block>
        </Col>
      </Row>
    </RequestLayout>
  </div>
  <Row slot="controls">
    <Col width="50">
      <Button
        color={properties.color}
        fill
        round
        small
      >
        Statements
      </Button>
    </Col>
    <Col width="50">
      <Button
        color={properties.color}
        round
        outline
        small
      >
        {language().buttons.viewAll.text}
      </Button>
    </Col>
  </Row>
</PluginStackUI>
