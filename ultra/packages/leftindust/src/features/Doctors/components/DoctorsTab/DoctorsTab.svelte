<script lang="ts">
  import type { Router } from 'framework7/types';
  
  import DoctorsTabDefault from './DoctorsTabDefault.svelte';
  import DoctorsTabSearch from './DoctorsTabSearch.svelte';
  
  import { clientsSearchQuery } from '@/features/Clients/store';
  import { doctorsSelectedAttachments } from '../../store';
  import type { OperationStore } from '@urql/svelte';
  import type { PartialDoctorsByDoctorIdQueryQuery, PartialDoctorsByRangeQueryQuery, Range } from '@/api/server';
  
  export let f7router: Router.Router;

  export let request: OperationStore<PartialDoctorsByRangeQueryQuery, {
    range: Range;
  }, PartialDoctorsByRangeQueryQuery>;

  export let recentsRequest: OperationStore<PartialDoctorsByDoctorIdQueryQuery, {
    doctorIds: {
      value: string;
    }[];
  }, PartialDoctorsByDoctorIdQueryQuery>;
</script>

<svelte:component 
  this={$clientsSearchQuery || $doctorsSelectedAttachments.length > 0 ? DoctorsTabSearch : DoctorsTabDefault} 
  {f7router}
  {request}
  {recentsRequest}
/>
