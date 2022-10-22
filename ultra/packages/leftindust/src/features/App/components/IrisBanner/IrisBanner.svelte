<script lang="ts">
  import './IrisBanner.scss';

  export let text = 'iris';

</script>

<div class="app-iris_banner">
  <h1>{text}</h1>
  <div class="app-iris_banner-container">
    <slot />
  </div>
</div>
