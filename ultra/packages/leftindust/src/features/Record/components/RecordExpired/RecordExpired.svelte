<script lang="ts">  
  import { Block, BlockFooter, Icon } from 'framework7-svelte';
</script>

<Block class="no-margin-top" style="margin-bottom: 100px;padding: 0 45px;">
  <div class="display-flex">
    <div>
      <h2><Icon f7="exclamationmark_circle" color="orange" style="font-size: 36px;margin-bottom:5px" /> Expired</h2>
      <BlockFooter>
        This link has expired. Contact your referer for a new link.
      </BlockFooter>
    </div>
  </div>
</Block>
