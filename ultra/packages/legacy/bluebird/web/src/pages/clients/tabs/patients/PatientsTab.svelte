<script lang="ts">
  import type { Router } from 'framework7/types';
  
  import { ClientsSearchQuery, PatientsSelectedAttachments } from '../../store/ClientsStore';
  import PatientsTabDefaultLayout from './layout/PatientsTabDefaultLayout.svelte';
  import PatientsTabSearchLayout  from './layout/PatientsTabSearchLayout.svelte';

  export let f7router: Router.Router;
  export let f7route: Router.Route;

</script>

<br />
<svelte:component
  this={$ClientsSearchQuery || $PatientsSelectedAttachments.length > 0 ? PatientsTabSearchLayout : PatientsTabDefaultLayout}
  {f7router}
  {f7route}
/>
