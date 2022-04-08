export type MessageBoxParameters = {
    type?: string;
    message: string;
    detail?: string;
    buttons: string[];
    defaultId?: number;
    cancelId?: number;
};
