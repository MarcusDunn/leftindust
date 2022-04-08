<script lang="ts">
  import type { Popover as PopoverType } from 'framework7/types';
  import { onMount } from 'svelte/internal';
  import { _ } from '@/language';

  import Appbar from '@/features/UI/components/Appbar/Appbar.svelte';
  import Page from '@/features/UI/components/Page/Page.svelte';
  import {
    Popover,
    View,
    Button,
    Link,
  } from 'framework7-svelte';
  
  import './AppLauncherPopover.scss';
  import AppLauncher from './AppLauncher.svelte';
  
  let ref: Popover;

  export let instance: PopoverType.Popover | undefined = undefined;

  onMount(() => {
    instance = ref.instance();
  });
</script>

<Popover
  class="apps-app_launcher_popover"
  bind:this={ref}
>
  <View>
    <Page>
      <Appbar slot="fixed" close={{ popover: true }} />
      <p />
      <AppLauncher small />
      <h5 class="text-align-center" style="font-size:15px">
        <Link on:click={() => window.location.href = '/'}>{$_('generics.returnToHome')}</Link>
      </h5>
      <br />
    </Page>
  </View>
</Popover>