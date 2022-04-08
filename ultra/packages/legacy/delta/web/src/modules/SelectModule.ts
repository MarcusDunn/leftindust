import type { Comparable } from '../requests';

export type Selectable<T = Comparable> = {
  type: T;
  id: string;
};

export type LeftClick = (selectable: Selectable) => Selectable[];
export type CtrlClick = (selectable: Selectable, selected: Selectable[]) => Selectable[];
export type ShiftClick = (
  selectable: Selectable,
  selected: Selectable[],
  reference: Selectable[],
) => Selectable[];

export type Click = (
  event: CustomEvent<MouseEvent | void>,
  options: {
    selectable: Selectable;
    multiselect?: {
      reference: Selectable[];
      selected: Selectable[];
    };
  },
) => Selectable[];

export const leftClick: LeftClick = (selectable) => [selectable];
export const ctrlClick: CtrlClick = (selectable, selected) => {
  const filtered = selected.filter((value) => value.id !== selectable.id);
  if (selected.some((value) => value.id === selectable.id) && selected.length !== 1) {
    return filtered;
  } else {
    return [...filtered, selectable];
  }
};
export const shiftClick: ShiftClick = (selectable, selected, reference) => {
  // Break out of function if no other items are selected
  if (selected.length === 0) return [selectable];

  // Get index of previous selected and newly selected
  const prevIndex = reference.findIndex((value) => value.id === selected[selected.length - 1].id);
  const index = reference.findIndex((value) => value.id === selectable.id);

  // Check to see if the max index is smaller than the newly selected
  if (prevIndex < index) {
    // Get all values from max to new max
    for (let i: number = prevIndex; i <= index; i += 1) {
      selected = [...selected, reference[i]];
    }
    // Check to see if the min index is larger than the newly selected
  } else if (prevIndex > index) {
    // Get all values from min to new min
    for (let i: number = prevIndex; i >= index; i -= 1) {
      selected = [...selected, reference[i]];
    }
    // Check to see if the items are in range
  } else {
    // Get all values between the max value and value selected, descending
    for (let i: number = prevIndex; i >= index; i -= 1) {
      // Check to see if the index has already been selected
      selected = [...selected, reference[i]];
    }
  }

  return selected.filter((v, i, a) => a.findIndex(t => (t.id === v.id)) === i);
};

export const click: Click = (event, options) => {
  if (event.detail && 'ctrlKey' in event.detail) {
    const { selectable, multiselect } = options;
    const { metaKey, ctrlKey, shiftKey } = event.detail;

    // Not passing a list of selected items implies you
    // don't want multi-select
    if (multiselect) {
      if (metaKey || ctrlKey) return ctrlClick(selectable, multiselect.selected);
      if (shiftKey) return shiftClick(selectable, multiselect.selected, multiselect.reference);
    }

    return leftClick(selectable);
  } else {
    throw new Error('Click was called on an event without a click event');
  }
};
