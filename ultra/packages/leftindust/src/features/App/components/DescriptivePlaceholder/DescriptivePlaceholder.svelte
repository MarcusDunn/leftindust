<script lang="ts">
  import { BlockFooter, Link } from 'framework7-svelte';
  import type { Url } from '@/features/UI';

  import './DescriptivePlaceholder.scss';

  export let title: string;
  export let description: string;
  export let link: Partial<Url> | undefined = undefined;
</script>

<div class="app-descriptive_placeholder">
  <slot />
  <h4>{title}</h4>
  <BlockFooter>
    {description}
    <br />
    <p />
    {#if link}
      <Link href={link.href ?? '#'}>{link.label ?? ''}</Link>
    {/if}
  </BlockFooter>
</div>