import MedIQCover from '@/apps/mediq/assets/mediq.png';

import FlowCover from '@/apps/flow/assets/flow.png';

import QueueCover from '@/apps/queue/assets/queue.png';

import AdminCover from '@/apps/admin/assets/admin.png';

export type App = {
  title: string;
  cover: string;
  onClick?: () => void;
}

const apps: App[] = [
  {
    title: 'MedIQ',
    cover: MedIQCover,
    onClick: () => window.location.href = '/apps/mediq/',
  },
  {
    title: 'Flow',
    cover: FlowCover,
  },
  {
    title: 'Queue',
    cover: QueueCover,
    onClick: () => window.location.href = '/apps/queue/',
  },
  {
    title: 'Administration',
    cover: AdminCover,
  },
];

export default apps;
