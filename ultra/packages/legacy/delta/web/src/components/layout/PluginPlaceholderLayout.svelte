<script lang="ts">
  import { BlockFooter, Link } from 'framework7-svelte';
  import type { Url } from '../../modules/UIModule';

  export let title: string;
  export let description: string;
  export let link: Partial<Url> | undefined = undefined;
</script>

<style lang="scss">
  .ultra-component-plugin-placeholder-layout {
    text-align: center;

    .link {
      margin-top: 20px;
    }
  }
</style>

<div class="ultra-component-plugin-placeholder-layout">
  <h4>{title}</h4>
  <BlockFooter>
    {description}
    <br />
    <br />
    {#if link}
      <Link href={link.href ?? '#'}>{link.label ?? ''}</Link>
    {/if}
  </BlockFooter>
</div>