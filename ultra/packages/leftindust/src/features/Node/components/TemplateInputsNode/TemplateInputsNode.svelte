<script lang="ts">
  import { TemplateInputItems } from '@/features/Templates/store';

  import type { OutputSocket, OutputSockets } from 'function-junctions/types';
  import { _ } from '@/language';

  export let outputs: OutputSockets<{
    Value: OutputSocket<unknown[]>;
  }>;

  $: inputs = $TemplateInputItems.sections.flatMap((section, index) =>
    section.inputs.map((input, inputIndex) => ({
      ...input,
      sectionIndex: index,
      index: inputIndex,
      label: `${input.label}${
        $TemplateInputItems.sections.length > 1
          // eslint-disable-next-line @typescript-eslint/restrict-plus-operands
          ? ` (${$_('generics.sectionIndexed', { values: { number: index + 1 } })})`
          : ''
      }`,
    })),
  );

</script>