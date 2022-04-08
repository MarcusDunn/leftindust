<script lang="ts" context="module">
  import type { Keyable } from '../../../types/helpers';

  export type BoxedUIConstraints = {
    width: number;
    height: number;
    fontSize: number;
  };

  export const BoxedUISizes: Keyable<BoxedUIConstraints> = {
    Smallest: {
      width: 17,
      height: 17,
      fontSize: 14,
    },
    Small: {
      width: 30,
      height: 30,
      fontSize: 17,
    },
    Medium: {
      width: 44,
      height: 44,
      fontSize: 18,
    },
    Large: {
      width: 55,
      height: 55,
      fontSize: 24,
    },
    Largest: {
      width: 90,
      height: 90,
      fontSize: 40,
    },
  };

</script>

<script lang="ts">
  export let constraints: BoxedUIConstraints = BoxedUISizes.Medium;
  export let round = false;
  export let color: string;
  export let outline = false;
  export let fill = false;

  if (outline) {
    constraints.width -= 4;
    constraints.height -= 4;
  }

</script>

<div
  class={`
  	ultra-boxed
  	${round ? 'ultra-boxed-round' : ''}
  	${fill ? 'bg-color-' + color : ''}
  	${outline ? 'text-color-' + color : ''}
  `}
  style={`
		width: ${constraints.width}px;
		height: ${constraints.height}px;
		font-size: ${constraints.fontSize}px;
		${outline ? 'border: 2px solid var(--f7-color-' + color + ');' : ''}
  `}
>
  <slot />
</div>
