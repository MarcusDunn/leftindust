<script lang="ts">
  import './LeftindustBanner.scss';

  export let text = 'leftindust';

</script>

<div class="app-leftindust_banner">
  <h1>{text}</h1>
  <div class="app-leftindust_banner-container">
    <slot />
  </div>
</div>
