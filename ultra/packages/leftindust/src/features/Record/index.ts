import type { createForm } from 'felte';

export type RecordForm = {
  form:  ReturnType<typeof createForm>;
  ref: HTMLFormElement | undefined;
};

export type RecordValues = {
  inputs: ({
    value: string | number | boolean | string[] | undefined;
  })[];
}