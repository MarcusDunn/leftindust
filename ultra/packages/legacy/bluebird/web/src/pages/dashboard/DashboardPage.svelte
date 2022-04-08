<script lang="ts">
  import { Block } from 'framework7-svelte';
  import AppBarUI from '@framework/ui/controller/AppBarUI/AppBarUI.svelte';
  import PageUI from '@framework/ui/layout/PageUI/PageUI.svelte';

</script>

<PageUI>
  <AppBarUI slot="fixed" />
  <Block class="no-margin-top">
    <h1>Dashboard</h1>
  </Block>
</PageUI>
