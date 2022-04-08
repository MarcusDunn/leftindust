import { client } from '../../server';
import type { Event, EventInput, EventEditInput } from '../../schema/leftindust.schema';
import eventMutation from '../../requests/mutations/eventMutation.graphql';
import eventEditMutation from '../../requests/mutations/eventEditMutation.graphql';
import { sendTrigger } from '../../modules/TriggerModule';

const EventMutateEngine = async (event: EventInput | EventEditInput, edit = false): Promise<boolean> => {
  const result = await client.mutate<Record<'addEvent' | 'editEvent', Partial<Event>>>(edit ? eventEditMutation : eventMutation, {
    variables: {
      event,
    },
  });

  if (result.data?.editEvent?.eid?.id || result.data?.addEvent?.eid?.id) {
    sendTrigger('refreshEvents');
    return true;
  }

  if (result.errors && result.errors.length > 0) {
    console.error(result.errors);
  }

  return false;

};

export default EventMutateEngine;