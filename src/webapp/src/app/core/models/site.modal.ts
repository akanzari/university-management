import { Bloc } from './bloc.model';

export interface Site {
    siteId: string;
    code: string;
    label: string;
    blocs: Bloc[];
}