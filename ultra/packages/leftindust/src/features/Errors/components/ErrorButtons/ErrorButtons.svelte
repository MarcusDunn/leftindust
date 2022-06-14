<script lang="ts">
  import { Button, Icon } from 'framework7-svelte';
  import { _ } from '@/language';

  export let tryAgain: (() => void) | undefined = undefined;
  export let reload: (() => void) | undefined = undefined;
  export let report: (() => void) | undefined = undefined;
  export let signOut: (() => void) | undefined = undefined;

</script>

<style lang="scss">
  .component {
    display: flex;
  }
</style>

<div class="component">
  {#if tryAgain}
    <Button
      color="primary"
      fill
      round
      on:click={() => tryAgain && tryAgain()}
    >
      {$_('generics.tryAgain')}
    </Button>
  {/if}
  {#if reload}
    <Button
      color="blue"
      fill
      round
      on:click={() => reload && reload()}
    >
      <Icon f7="arrow_clockwise" />
      {$_('generics.reload')}
    </Button>
  {/if}
  {#if report}
    <Button
      color="purple"
      fill
      round
      on:click={() => report && report()}
    >
      <Icon f7="exclamationmark_bubble_fill" />
      {$_('generics.report')}
    </Button>
  {/if}
  {#if signOut}
    <span class="flex-grow" />
    <Button color="red"
      fill
      round
      on:click={() => signOut && signOut()}
    >
      <Icon f7="arrow_up_left_square_fill" />
      {$_('generics.signOut')}
    </Button>
  {/if}
</div>
