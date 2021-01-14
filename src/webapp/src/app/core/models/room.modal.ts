import { Bloc } from './bloc.model';
import { Site } from './site.modal';

export interface Room {
    classRoomId: string;
    code: string;
    label: string;
    capacity: string;
    startDate: string;
    endDate: string;
    startHour: string;
    endHour: string;
    reason: any;
    site: Site;
    bloc: Bloc;
}