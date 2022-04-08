<script lang="ts">
  import type { Color } from '../..';
  
  import type { BoxedDimensions } from '.';
  import { BoxedSizes } from '.';

  import './Boxed.scss';

  export let dimensions: BoxedDimensions = BoxedSizes.Medium;
  export let round = false;
  export let color: Color;
  export let outline = false;
  export let fill = false;

  if (outline) {
    dimensions.width -= 4;
    dimensions.height -= 4;
  }

</script>

<div
  class={`
  	ui-boxed
  	${round ? 'ui-boxed-round' : ''}
  	${fill ? 'bg-color-' + color : ''}
  	${outline ? 'text-color-' + color : ''}
  `}
  style={`
		width: ${dimensions.width}px;
		height: ${dimensions.height}px;
		font-size: ${dimensions.fontSize}px;
		${outline ? 'border: 2px solid var(--f7-color-' + color + ');' : ''}
  `}
>
  <slot />
</div>
