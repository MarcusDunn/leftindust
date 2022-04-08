<script lang="ts">
  import { Icon } from 'framework7-svelte';

  import type { Framework7Icon } from '../../../modules/UIModule';

  export let title: string;
  export let text: string;
  export let middle = false;
  export let large = false;
  export let icon: Framework7Icon;

</script>

<div
  class={`ultra-notice ${middle ? 'ultra-middle-of-page' : ''} ${large ? 'ultra-notice-large' : ''}`}
>
  <div class="ultra-notice-content">
    <div class="ultra-notice-description">
      <Icon class="ultra-notice-icon" {...icon} />
      <div>
        <div class="ultra-notice-title">{title || ''}</div>
        <div class="ultra-notice-text">{text || ''}</div>
      </div>
    </div>
    <div class="ultra-notice-controls">
      <slot />
    </div>
  </div>
</div>
