<script lang="ts">
  import type { Router } from 'framework7/types';

  import PendingDefaultLayout from './layout/PendingDefaultLayout.svelte';

  export let f7router: Router.Router;
  export let f7route: Router.Route;

</script>

<br />
<svelte:component
  this={PendingDefaultLayout}
  {f7router}
  {f7route}
/>
