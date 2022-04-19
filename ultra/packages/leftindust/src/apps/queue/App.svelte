<script lang="ts">
  import type { Framework7Parameters } from 'framework7/types';
  import AppLayout from '@/features/App/components/AppLayout/AppLayout.svelte';

  export let f7params: Framework7Parameters;

</script>

<AppLayout items={[]} {f7params} sidebar={false} />
