import type { Fragments } from '../requests/fragments';
import type { Data } from '../';
import type { Writable } from 'svelte/store';
import { writable } from 'svelte/store';
import PatientsSpecificEngine from './patients/PatientsSpecificEngine';
import EventsSpecificEngine from './events/EventsSpecificEngine';
import DoctorsSpecificEngine from './doctors/DoctorsSpecificEngine';
import IcdsSpecificEngine from './icd/IcdsSpecificEngine';
import SurveyTemplatesSpecificEngine from './surveys/SurveyTemplatesSpecificEngine';

export const SpecificEngine = <T = Fragments>(selectables: Data[]): Writable<Record<string, T>> => {
  const results = writable<Record<string, T>>({});

  selectables
    .some((selectable) => {
      let engine, variables, key: string;

      switch (selectable.type) {
        case 'Patient':
          engine = PatientsSpecificEngine;
          variables = { pids: [{ id: selectable.id }] };
          key = 'patients';
          break;

        case 'Doctor':
          engine = DoctorsSpecificEngine;
          variables = { dids: [{ id: selectable.id }] };
          key = 'doctors';
          break;

        case 'Event':
          engine = EventsSpecificEngine;
          variables = { events: [{ id: selectable.id }] };
          key = 'events';
          break;

        case 'GraphQLFormTemplate':
          engine = SurveyTemplatesSpecificEngine;
          variables = { surveys: [{ id: selectable.id }] };
          key = 'surveys';
          break;

        case 'IcdSimpleEntity':
        case 'IcdLinearizationEntity':
          engine = IcdsSpecificEngine;
          variables = { icdCode: selectable.id };
          key = 'icd';
          break;

        default:
          console.warn(
            `Selectable: ${JSON.stringify(variables)}, was not processed because no engine with the corresponding type exists.`,
          );
          return;
      }

      // The entirety of this function is a little bit freaky by nature, so I didn't bother typing
      // the returns for the engine. This may be difficult to debug but I don't see how TS would
      // help in this situation anyways

      // @ts-expect-error
      engine({ ...variables }).request.subscribe(({ data }) => {
        if (data) {
          results.update((result) => ({
            ...result,
            // @ts-expect-error
            [selectable.id]: data[key][0] ?? data[key],
          }));
        }
      });
    });

  return results;
};
