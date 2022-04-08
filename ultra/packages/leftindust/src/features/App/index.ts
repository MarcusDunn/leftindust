export enum AppViews {
  Dashboard = 'view-dashboard',
  Calendar = 'view-calendar',
  Clients = 'view-clients',
  Analytics = 'view-analytics',
  Users = 'view-users',
  Templates = 'view-templates',
  Wizard = 'view-wizard',
  Popup = 'view-popup',
  Hub = 'view-hub'
}

export enum AppRootRoutes {
  Home = '/',
  Dashboard = '/dashboard/',
  Calendar = '/calendar/',
  Clients = '/clients/',
  Templates = '/templates/',
  Analytics = '/template/analytics/',
  Users = '/users/',
  LifeCycleError = '/errors/lifecycle/'
}

export enum AppPopups {
  Default = 'popup-default',
  Error = 'popup-error',
  Wizard = 'popup-wizard',
}

export enum Layout {
  Bundled = 'bundled',
  Stacked = 'stacked',
}