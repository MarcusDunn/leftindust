<script lang="ts">
  import { _ } from '@/language';

  import abstract from './assets/abstract.mp4';
  import './MedIQBanner.scss';

  export let text = $_('generics.app');

</script>

<div class="app-mediq_banner">
  <video autoplay loop muted>
    <source src={abstract} type="video/mp4" />
  </video>
  <h1>{text}</h1>
  <div class="app-mediq_banner-container">
    <slot />
  </div>
</div>
