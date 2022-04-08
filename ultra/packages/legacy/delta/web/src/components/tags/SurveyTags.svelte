<script lang="ts">
  import { Chip, Icon } from 'framework7-svelte';

  export let sectionLength: number | undefined = undefined;
  export let fieldsLength: number | undefined = undefined;

  export let outline = false;
  export let small = false;

</script>

<span class="one-line-word-clamp">
  {#if sectionLength}
    <Chip
      class={`${small ? 'chip-small' : ''}`}
      text={`${sectionLength} section(s)`}
      mediaBgColor="primary"
      {outline}
    >
      <span slot="media"><Icon f7="doc_on_doc_fill" /></span>
    </Chip>
  {/if}
  {#if fieldsLength}
    <Chip
      class={`${small ? 'chip-small' : ''}`}
      text={`${fieldsLength} question(s)`}
      mediaBgColor="blue"
      outline={outline}
    >
      <span slot="media"><Icon f7="question_circle_fill" /></span>
    </Chip>
  {/if}
</span>
