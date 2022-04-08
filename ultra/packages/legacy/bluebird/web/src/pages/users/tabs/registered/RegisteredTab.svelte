<script lang="ts">
  import type { Router } from 'framework7/types';

  import RegisteredDefaultLayout from './layout/RegisteredDefaultLayout.svelte';

  export let f7router: Router.Router;
  export let f7route: Router.Route;

</script>

<br />
<svelte:component
  this={RegisteredDefaultLayout}
  {f7router}
  {f7route}
/>
