<script lang="ts">
  import './Section.scss';

  export let title: string;

  export let active = false;
</script>

<div class={`ui-section ${active ? 'ui-section-active' : ''}`}>
  <h6 class="ui-section-title">{title}</h6>
  <div class="ui-section-container">
    <slot />
  </div>
  <slot name="footer" />
</div>
