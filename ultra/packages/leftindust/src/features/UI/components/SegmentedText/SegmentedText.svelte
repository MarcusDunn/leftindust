<style lang="scss">
  @import './SegmentedText.scss';
</style>

<script lang="ts">
  
  export let text: string[];
  const middleIndex = Math.round((text.length - 1) / 2);

  export let color: string;
  export let index = middleIndex;
  export let style = '';

</script>

<div class="ui-segmented_text">
  {#each text as t, i}
    <div
      class={`ui-segmented_text-text ${index === i ? 'ui-segmented_text-text-selected' : ''}`}
      style={`${index === i ? `color: var(--f7-color-${color});` : ''}${style}`}
    >
      {t}
    </div>
  {/each}
</div>