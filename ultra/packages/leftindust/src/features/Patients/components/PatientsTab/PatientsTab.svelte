<script lang="ts">
  import type { Router } from 'framework7/types';
  
  import { clientsSearchQuery } from '@/features/Clients/store';
  import { patientsSelectedAttachments } from '../../store';

  import PatientsTabDefault from './PatientsTabDefault.svelte';
  import PatientsTabSearch  from './PatientsTabSearch.svelte';

  export let f7router: Router.Router;
</script>

<svelte:component
  this={$clientsSearchQuery || $patientsSelectedAttachments.length > 0 ? PatientsTabSearch : PatientsTabDefault}
  {f7router}
/>
