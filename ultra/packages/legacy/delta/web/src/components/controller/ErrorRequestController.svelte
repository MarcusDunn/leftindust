<script lang="ts">
  import type { ObservableQuery } from '@apollo/client';

  import language from '../../languages';
  import { Button, Icon } from 'framework7-svelte';
  import NoticeUI from '../../ui/layout/NoticeUI/NoticeUI.svelte';

  export let title: string = language().errors.internalError.text;
  export let text: string = language().errors.request.text;

  export let large = false;
  export let middle = false;

  export let refetch: ObservableQuery['refetch'] | undefined = undefined;

</script>

<NoticeUI
  title={title}
  text={text}
  icon={{ f7: 'exclamationmark_triangle_fill', color: 'orange' }}
  large={large}
  middle={middle}
>
  <Button style="width: 100%" round fill on:click={() => refetch ? refetch() : undefined}>{language().buttons.tryAgain.text}</Button>
</NoticeUI>
