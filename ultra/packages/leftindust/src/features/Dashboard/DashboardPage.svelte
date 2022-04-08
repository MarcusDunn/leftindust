<script lang="ts">
  import { Block } from 'framework7-svelte';
  import AppBar from '@/features/UI/components/Appbar/Appbar.svelte';
  import Page from '@/features/UI/components/Page/Page.svelte';

</script>

<Page>
  <AppBar slot="fixed" />
  <Block class="no-margin-top">
    <h1>Dashboard</h1>
  </Block>
</Page>
