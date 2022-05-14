<script lang="ts">
  import type { CombinedError, OperationContext } from '@urql/svelte';
  import { Preloader, Block } from 'framework7-svelte';
  import { _ } from '@/language';
  import Notice from '@/features/UI/components/Notice/Notice.svelte';
  import ErrorButtons from '@/features/Errors/components/ErrorButtons/ErrorButtons.svelte';

  export let fetching: boolean | undefined = undefined;
  export let data: unknown = {};
  export let error: Error | CombinedError | undefined = undefined;
  export let reexecute: (context?: Partial<OperationContext> | undefined) => void;

  export let large = false;
  export let middle = false;
</script>


{#if fetching}
  <Block class="middle" style="margin-top: 40px; margin-bottom: 40px;">
    <Preloader />
  </Block>
{:else if error}
  <Notice
    title={$_('errors.internalError')}
    text={$_('errors.connectionError')}
    icon={{ f7: 'exclamationmark_triangle_fill', color: 'orange' }}
    {large}
    {middle}
  >
    <ErrorButtons tryAgain={() => reexecute()} />
  </Notice>
{:else if data}
  <div {...$$restProps}> 
    <slot />
  </div>
{/if}


