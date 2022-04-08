<script lang="ts">
  import language from '../../languages';
  import { signOut } from '../../modules/UserModule';
  import { Button, Icon } from 'framework7-svelte';

</script>

<Button color="purple" fill round>
  <Icon f7="exclamationmark_bubble_fill" />
  {language().buttons.report.text}
</Button>
<span class="flex-grow"></span>
<Button color="red" fill round on:click={signOut}>
  <Icon f7="arrow_up_left_square_fill" />
  {language().buttons.signOut.text}
</Button>
