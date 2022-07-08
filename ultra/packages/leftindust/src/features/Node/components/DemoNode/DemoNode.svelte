<script lang="ts">
  import type { NodeBlueprint } from 'function-junctions/types';

  export let title: string;
  const { inputs, outputs, color, className, style } = $$props as NodeBlueprint;
</script>

<div
  class={`function-junctions-node ${className ?? ''}`}
  style={`${style ? `${style};` : ''}opacity: 1 !important;position: relative;width: 100px`}
  on:click
>
  <div class="function-junctions-node-title" style={`background: ${color ?? 'linear-gradient(#228cfd, #007aff)'}`}>{title}</div>
  <div class="function-junctions-node-content">
    {#if outputs}
      <div class="function-junctions-node-outputs">
        {#each Object.keys(outputs) as key}
          <div
            class="function-junctions-socket function-junctions-socket-output"
          >
            <div class="function-junctions-socket-title">{key}</div>
            <div
              class="function-junctions-socket-connection"
              style={outputs[key].color ? `background: ${outputs[key].color}` : ''}
            />
          </div>
        {/each}
      </div>
    {/if}
    <div class="function-junctions-node-content" />
    {#if inputs}
      <div class="function-junctions-node-inputs">
        {#each Object.keys(inputs) as key}
          <div
            class="function-junctions-socket function-junctions-socket-output"
          >
            <div
              class="function-junctions-socket-connection"
              style={inputs[key].color ? `background: ${inputs[key].color}` : ''}
            />
            <div class="function-junctions-socket-title" style="text-align: left">{key}</div>
          </div>
        {/each}
      </div>
    {/if}
  </div>
</div>