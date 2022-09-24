<script lang="ts">
  import type { Popover as PopoverType, View as ViewType } from 'framework7/types';
  import type { MenuNode, MenuNodes } from '../..';
  import Popover from '@/features/View/components/Popover/Popover.svelte';
  import { Block, Col, Icon, List, ListItem, Row, View } from 'framework7-svelte';
  import Boxed from '@/features/UI/components/Boxed/Boxed.svelte';
  import Page from '@/features/UI/components/Page/Page.svelte';
  import Appbar from '@/features/UI/components/Appbar/Appbar.svelte';
  import MenuButton from '@/features/UI/components/MenuButton/MenuButton.svelte';
  import { _ } from '@/language';
  import NodesSelectorModalPage from './NodesSelectorModalPage.svelte';
  import { createEventDispatcher, onMount } from 'svelte';
  import { isDarkMode } from '@/features/UI/store';
  import { writable, type Writable } from 'svelte/store';

  export let menuNodes: MenuNodes;
  export let instance: PopoverType.Popover | undefined = undefined;

  let temporarySelectedNode: Writable<MenuNode[]> = writable([]);

  let viewRef: View;
  let view: ViewType.View;

  const dispatch = createEventDispatcher();

  onMount(() => {
    view = (<{ instance: () => ViewType.View }>(<unknown>viewRef)).instance();
  });

  $: if ($temporarySelectedNode.length > 0) dispatch('add', $temporarySelectedNode);
</script>

<Popover style="width: 750px; height: 350px" bind:instance>
  <View
    stackPages
    iosSwipeBack={false}
    animate={false}
    routesAdd={menuNodes.map((_, index) => ({
      path: `/${index}/`,
      component: NodesSelectorModalPage,
    }))}
    bind:this={viewRef}
    style="height: 350px"
    class={`${$isDarkMode ? 'function-junctions-appearance-dark' : ''}`}
  >
    <Page>
      <Appbar slot="fixed">
        <MenuButton
          title={$_('generics.close')}
          icon={{ f7: 'xmark_circle_fill', color: 'gray' }}
          on:click={() => instance?.close()}
        />
      </Appbar>
      <Block class="no-margin-top">
        <p />
        <Row class="no-gutter">
          {#each menuNodes as section, index}
            <Col width={50} xlarge={33}>
              <List class="no-margin" mediaList inset>
                <ListItem
                  title={section.title}
                  text={section.description}
                  link
                  on:click={() => view.router.navigate(`/${index}/`, {
                    props: {
                      nodes: section.nodes,
                      temporarySelectedNode,
                    },
                  })}
                >
                  <Boxed
                    slot="media"
                    color={section.color ?? 'gray'}
                    fill
                  >
                    <Icon {...section.icon} />
                  </Boxed>
                </ListItem>
              </List>
            </Col>
          {/each}
        </Row>
      </Block>
    </Page>
  </View>
</Popover>