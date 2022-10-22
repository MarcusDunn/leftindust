<script lang="ts">
  import { Icon, Row, Col, Button } from 'framework7-svelte';
  import type { DialogParams } from '.';

  import './DialogContent.scss';

  export let dialog: DialogParams;

  export let callback: () => void = () => undefined;
</script>

<div class="ui-dialog-content-auto">
  <div style="width: 400px">
    <div class="display-flex">
      <Icon
        f7="exclamationmark_triangle_fill"
        style="font-size: 40px; margin-top: 20px"
        color="orange"
        {...dialog.icon}
      />
      <div style="margin-left: 30px">
        <h3 class="no-margin-bottom" style="font-weight: 600; font-size: 18px">{dialog.title}</h3>
        <p class="text-color-gray">{dialog.text}</p>
        <Row color={dialog.color ?? 'blue'} style="margin-top: 40px; min-width: 250px">
          {#each dialog.buttons ?? [] as button}
            <Col width={(1 / (dialog.buttons ?? []).length) * 100}>
              <Button
                fill={button.primary}
                outline={!button.primary}
                round
                on:click={() => {
                  callback();
                  button.onClick?.();
                }}
              >
                {button.label}
              </Button>
            </Col>
          {/each}
        </Row>
        {#if dialog.link}
          <div style="margin-top: 50px">
            <a
              href="#"
              on:click={dialog.link.onClick}
            >
              {dialog.link.label}
            </a>
          </div>
        {/if}
      </div>
    </div>
  </div>
</div>