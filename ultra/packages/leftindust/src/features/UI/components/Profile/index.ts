export type ProfileDrawerOptions = {
  name?: string | boolean;
  open?: boolean;
  visible?: boolean;
  tagsButton?: boolean;
};

export const ProfileDrawer: ProfileDrawerOptions = {
  name: false,
  open: false,
  visible: true,
  tagsButton: false,
};