<script lang="ts">
  import AppBar from '@/features/UI/components/Appbar/Appbar.svelte';
  import Page from '@/features/UI/components/Page/Page.svelte';
  import { _ } from 'svelte-i18n';
  import { openWizard } from '../Wizard';

</script>

<Page>
  <AppBar
    slot="fixed"
    right={[{
      title: $_('generics.create'),
      icon: { f7: 'plus_circle_fill', color: 'deeppurple' },
      onClick: () => openWizard('/wizard/template/'),
    }]}
  />
</Page>
