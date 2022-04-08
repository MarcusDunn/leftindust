<script lang="ts">
  import CONFIGURATION from '../../configuration';

  import abstract from '../../assets/abstract-2.mp4';

  export let text: string = CONFIGURATION.app.name;

</script>

<style lang="scss">
  .ultra-component-mediq-banner {
    position: relative;
    width: 100%;
    height: 100vh;
    overflow: hidden;

    margin-top: 19vh;
    padding-left: 3px !important;
    padding-right: 3px !important;

    .ultra-component-mediq-banner-container {
      margin-top: 100px;

      .item-content {
        padding-left: 0px !important;
      }
    }

    video {
      position: absolute;
      top: 0;
      left: 0;
      width: 150%;
      height: 85px;
      object-fit: cover;
      filter: saturate(2);

      &::-webkit-media-controls {
        display: none !important;
      }

      &::-webkit-media-controls-start-playback-button {
        display: none !important;
        -webkit-appearance: none;
      }
    }

    h1 {
      position: absolute;
      top: 0;
      left: 0;
      display: flex;
      align-items: center;
      width: 100%;
      height: 90px;
      margin: 0;
      font-weight: 800;
      font-size: 90px;
      text-align: center;
      text-transform: uppercase;
      background-color: #ffffff;
      mix-blend-mode: screen;
    }
  }

  :global(.theme-dark) {
    .ultra-component-mediq-banner {
      video {
        filter: saturate(0) !important;
      }

      h1 {
        background-color: #000000 !important;
      }
    }
  }

</style>

<div class="ultra-component-mediq-banner">
  <video autoplay loop muted>
    <source src={abstract} type="video/mp4" />
  </video>
  <h1>{text}</h1>
  <div class="ultra-component-mediq-banner-container">
    <slot />
  </div>
</div>
