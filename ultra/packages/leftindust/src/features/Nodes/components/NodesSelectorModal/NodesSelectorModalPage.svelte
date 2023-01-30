<script lang="ts">
  import Page from '@/features/UI/components/Page/Page.svelte';
  import Appbar from '@/features/UI/components/Appbar/Appbar.svelte';
  import MenuButton from '@/features/UI/components/MenuButton/MenuButton.svelte';
  import { _ } from '@/language';

  import type { MenuNode } from '../..';
  import type { Router } from 'framework7/types';
  import { Block, BlockFooter, Col, Row } from 'framework7-svelte';
  import type { Writable } from 'svelte/store';
  import DemoNode from '@/features/Node/components/DemoNode/DemoNode.svelte';

  export let nodes: MenuNode[];
  export let temporarySelectedNode: Writable<MenuNode[]>;
  export let f7router: Router.Router;
</script>

<Page>
  <Appbar slot="fixed">
    <MenuButton
      title={$_('generics.back')}
      icon={{ f7: 'chevron_left_circle_fill', color: 'gray' }}
      on:click={() => f7router?.back()}
    />
  </Appbar>
  <Block class="no-margin-top">
    <br />
    <Row class="no-gutter align-items-flex-end">
      {#each nodes as node}
        <Col
          class="display-flex"
          width={33}
          style="justify-content: center;flex-direction:column;align-items: center;cursor: pointer;"
          on:click={() => $temporarySelectedNode = [node]}
        >
          <DemoNode title={node.title} {...node.blueprint} />
          {#if node.blueprint?.className == 'node-transparent'}
            <p class="no-margin-bottom">{node.title}</p>
          {/if}
          <BlockFooter class="text-align-center two-line-word-clamp" style="margin: 10px 0 20px">
            {node.description}
          </BlockFooter>
        </Col>
      {/each}
    </Row>
  </Block>
</Page>