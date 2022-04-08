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

  import PluginBundleUI from '../../../ui/plugin/PluginBundleUI/PluginBundleUI.svelte';
  import HealthCard from '../../../components/card/HealthCard.svelte';
  import RequestLayout from '../../../components/layout/RequestLayout.svelte';
  import PluginPlaceholderLayout from '../../../components/layout/PluginPlaceholderLayout.svelte';

  export let selectable: Selectable;
  export let dragger: () => void | undefined;
  export let properties: NonNullable<Plugin['properties']>;

  const { request, insuranceNumber } = InsuranceReferencedEngine({ selectable });

</script>

<style lang="scss">
  .ultra-component-insurance-bundle-health-card-container {
    & :global(.block) {
      height: 170px;
      margin-bottom: 90px;
    }
  }

</style>

<PluginBundleUI
  title={properties.title}
  icon={properties.icon}
  color={properties.color}
  {dragger}
  shadow
>
  <RequestLayout {...$request} refetch={request.refetch} middle>
    {#if $insuranceNumber}
      <div class="ultra-component-insurance-bundle-health-card-container">
        <Block>
          <HealthCard number={$insuranceNumber ?? ''} />
        </Block>
      </div>
    {:else}
      <div style="margin-top: 40px">
        <PluginPlaceholderLayout
          title="No insurance number recorded"
          description={'To add an insurance number, open patient edit and change the "Insurance Number" field'}
          link={{
            label: 'Learn more about billing and insurance...',
            href: '#',
          }}
        />
      </div>
    {/if}
  </RequestLayout>
  <Row class="disabled" slot="controls">
    <Col width="50">
      <Button
        color={properties.color}
        fill
        round
      >
        Statements
      </Button>
    </Col>
    <Col width="50">
      <Button
        color={properties.color}
        round
        outline
      >
        {language().buttons.viewAll.text}
      </Button>
    </Col>
  </Row>
</PluginBundleUI>
