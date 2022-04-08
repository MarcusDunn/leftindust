<script lang="ts">
  import type { Router } from 'framework7/types';
  import DoctorsTabDefaultLayout from './layout/DoctorsTabDefaultLayout.svelte';
  import DoctorsTabSearchLayout from './layout/DoctorsTabSearchLayout.svelte';
  
  import { ClientsSearchQuery, DoctorsSelectedAttachments } from '../../store/ClientsStore';
  
  export let f7router: Router.Router;
  export let f7route: Router.Route;

</script>

<br />
<svelte:component this={$ClientsSearchQuery || $DoctorsSelectedAttachments.length > 0 ? DoctorsTabSearchLayout : DoctorsTabDefaultLayout} {f7router} {f7route} />
