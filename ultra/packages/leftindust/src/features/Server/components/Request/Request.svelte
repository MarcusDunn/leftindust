<script lang="ts">
  import type { ApolloError, ObservableQuery } from '@apollo/client';
  import { Preloader, Block } from 'framework7-svelte';
  import { _ } from '@/language';
  import Notice from '@/features/UI/components/Notice/Notice.svelte';
  import ErrorButtons from '@/features/Errors/components/ErrorButtons/ErrorButtons.svelte';

  type Error = {
    loading: false;
    data?: undefined;
    error: ApolloError | Error;
  }

  export let loading: boolean | undefined = undefined;
  export let data: unknown = {};
  export let error: Error | ApolloError | undefined = undefined;
  export let refetch: ObservableQuery['refetch'];

  export let large = false;
  export let middle = false;
</script>

<div>
  {#if loading}
    <Block class={middle ? 'middle-of-page' : undefined}>
      <Preloader />
    </Block>
  {:else if error}
    <br />
    <Notice
      title={$_('errors.internalError')}
      text={$_('errors.connectionError')}
      icon={{ f7: 'exclamationmark_triangle_fill', color: 'orange' }}
      {large}
      {middle}
    >
      <ErrorButtons tryAgain={() => refetch()} />
    </Notice>
  {:else if data}
    <div {...$$restProps}> 
      <slot />
    </div>
  {/if}
</div>

