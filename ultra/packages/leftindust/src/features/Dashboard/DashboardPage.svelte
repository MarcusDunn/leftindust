<script lang="ts">
  import AppBar from '@/features/UI/components/Appbar/Appbar.svelte';
  import Page from '@/features/UI/components/Page/Page.svelte';
  import NoAccessBanner from '../Permissions/components/NoAccessBanner/NoAccessBanner.svelte';
</script>

<Page>
  <AppBar slot="fixed" />
  <NoAccessBanner />
</Page>
