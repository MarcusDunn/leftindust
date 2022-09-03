<script lang="ts">
  import type { SurveyTemplateCalculation, SurveyTemplateSection } from '@/api/server';
  
  import { Block, BlockFooter, Icon } from 'framework7-svelte';
  import type { RecordValues } from '../..';
  import RecordComputedCalculations from '../RecordComputedCalculations/RecordComputedCalculations.svelte';

  export let calculations: SurveyTemplateCalculation[];
  export let sections: SurveyTemplateSection[];
  
  export let values: RecordValues[];
</script>

<Block class="no-margin-top" style="margin-bottom: 100px;padding: 0 45px;">
  <div class="display-flex">
    <div>
      <h2><Icon f7="checkmark_circle_fill" color="green" style="font-size: 36px;margin-bottom:5px" /> Complete</h2>
      <BlockFooter>
        Your results have been successfully recorded and sent back to your referer.
        You can now close this window.
      </BlockFooter>
    </div>
  </div>
  <br />
  <br />

  <RecordComputedCalculations
    {sections}
    {calculations}
    {values}
  />
</Block>
