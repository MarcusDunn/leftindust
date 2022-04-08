<script lang="ts">
  import './QueueBanner.scss';

  export let text = 'Queue';

</script>

<div class="app-queue_banner">
  <h1>{text}</h1>
  <div class="app-queue_banner-container">
    <slot />
  </div>
</div>
