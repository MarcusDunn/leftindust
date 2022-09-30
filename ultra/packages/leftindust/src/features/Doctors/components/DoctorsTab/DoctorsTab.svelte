<script lang="ts">
  import type { Router } from 'framework7/types';
  
  import DoctorsTabDefault from './DoctorsTabDefault.svelte';
  import DoctorsTabSearch from './DoctorsTabSearch.svelte';
  
  import { clientsSearchQuery } from '@/features/Clients/store';
  import { doctorsSelectedAttachments } from '../../store';
  
  export let f7router: Router.Router;

</script>

<svelte:component 
  this={$clientsSearchQuery || $doctorsSelectedAttachments.length > 0 ? DoctorsTabSearch : DoctorsTabDefault} 
  {f7router} 
/>
