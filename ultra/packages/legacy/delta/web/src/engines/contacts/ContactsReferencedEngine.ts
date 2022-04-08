import type { ReadableQuery } from 'svelte-apollo-client';
import type { Writable } from 'svelte/store';
import { writable } from 'svelte/store';

import type { Selectable } from '../../modules/SelectModule';
import type { DocumentNode } from 'graphql';

import type { PatientContactsQueryResult } from '../../requests/queries';
import type { Patient } from '../../schemas/leftindust.schema';

import { client } from '../../server';

import patientContactsQuery from '../../requests/queries/patientContactsQuery.graphql';

export type ContactsReferencedEngineResult = PatientContactsQueryResult;

export type ContactsReferencedEngine = {
  request: ReadableQuery<ContactsReferencedEngineResult>;
  contacts: Writable<ContactsReferencedEngineResult['patients'][0]['contacts']>;
};

export type ContactsReferencedEngineVariables = {
  selectable: Selectable;
};

export type ContactsReferencedEngineRequestVariables = {
  pids?: [Patient['pid']];
};

const ContactsReferencedEngine = (variables: ContactsReferencedEngineVariables): ContactsReferencedEngine => {
  const { selectable } = variables;

  const contacts = writable<ContactsReferencedEngineResult['patients'][0]['contacts']>([]);

  let query: DocumentNode;
  let key: 'patients';
  let requestVariables: ContactsReferencedEngineRequestVariables;

  switch (selectable.type) {
    case 'Patient':
      requestVariables = {
        pids: [{ id: selectable.id }],
      };
      key = 'patients';
      query = patientContactsQuery;
      break;
    default:
      throw new Error(
        `Incompatible type "${selectable.type}" was passed into the 'referenced' Contacts engine`,
      );
  }

  const request = client.query<ContactsReferencedEngineResult, ContactsReferencedEngineRequestVariables>(
    query,
    {
      variables: requestVariables,
    },
  );

  request.subscribe(({ data }) => {
    if (data?.[key]?.[0]?.contacts)
      contacts.set(data?.[key]?.[0]?.contacts);
  });

  return {
    request,
    contacts,
  };
};

export default ContactsReferencedEngine;
