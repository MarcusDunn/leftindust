<script lang="ts">
  import { Row, Col } from 'framework7-svelte';

  import apps from '@/features/Apps';
  import AppLauncherApp from './AppLauncherApp.svelte';

  import './AppLauncher.scss';

  export let small = false;
</script>

<div class="apps-app_launcher">
  <Row style="max-width:700px;">
    {#each apps as { title, cover, onClick }}
      <Col style="margin-bottom:50px"  width="50" xsmall="50" small="33" large="25">
        <AppLauncherApp
          {title}
          {cover}
          on:click={() => onClick && onClick()}
          {small}
        />
      </Col>
    {/each}
  </Row>
</div>