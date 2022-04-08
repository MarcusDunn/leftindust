<script lang="ts">
  import { Icon } from 'framework7-svelte';

  import './Notice.scss';

  import type { Framework7Icon } from '../..';

  export let title: string;
  export let text: string;
  export let middle = false;
  export let large = false;
  export let icon: Framework7Icon;

</script>

<div
  class={`ui-notice ${middle ? 'middle-of-page ' : ''} ${large ? 'ui-notice-large' : ''}`}
>
  <div class="ui-notice-content">
    <div class="ui-notice-description">
      <Icon class="ui-notice-icon" {...icon} />
      <div>
        <div class="ui-notice-title">{title || ''}</div>
        <div class="ui-notice-text">{text || ''}</div>
      </div>
    </div>
    <div class="ui-notice-controls">
      <slot />
    </div>
  </div>
</div>
