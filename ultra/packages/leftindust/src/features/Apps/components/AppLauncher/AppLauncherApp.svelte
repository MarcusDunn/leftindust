<script lang="ts">
  import './AppLauncherApp.scss';

  export let cover: string;
  export let title: string;

  export let small = false;
</script>

<div class={`apps-app_launcher_app ${small ? 'apps-app_launcher_app-small' : ''}`} on:click>
  <div class="apps-app_launcher_app-container">
    <img alt={title} src={cover} />
    <span>{title}</span>
  </div>
</div>