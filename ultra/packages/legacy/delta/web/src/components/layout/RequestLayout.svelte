<script lang="ts">
  import type { ApolloError, ObservableQuery } from '@apollo/client';
  import { Preloader, Block } from 'framework7-svelte';
  import ErrorRequestController from '../controller/ErrorRequestController.svelte';

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
  <Block class={middle ? 'ultra-middle-of-page' : undefined}>
    <Preloader />
  </Block>
{:else if error}
  <br />
  <ErrorRequestController {refetch} {middle} {large} />
{:else if data}
  <div {...$$restProps} class="ultra-request-layout">
    <slot />
  </div>
{/if}
</div>

