<style lang="scss" global>
  @import './RecordCalculationTags.scss';
</style>

<script lang="ts">
  import { templateCalculationNodes } from '@/features/Template';

  import { Chip } from 'framework7-svelte';
  import type { NodeState } from 'function-junctions/types';

  export let nodes: Record<string, NodeState>;

  const uniqueNodeTypes: string[] = [];

  const entries = Object.entries(nodes).filter(([, node]) => {
    const isDuplicate = uniqueNodeTypes.includes(node.type);

    if (!isDuplicate) {
      uniqueNodeTypes.push(node.type);

      return true;
    }

    return false;
  });
</script>

<div class="record-record_calculation_tags">
  {#each entries as [, node]}
    {#if node.type !== 'output' && node.type !== 'input'}
      <Chip class="record-record-calculation_tags-chip">
        <div class="record-record-calculation_tags-text">
          <div
            class="record-record-calculation_tags-stamp"
            style={`background: ${templateCalculationNodes[node.type].color};`}
          />
          {node.type}
        </div>
      </Chip>
    {/if}
  {/each}
</div>

