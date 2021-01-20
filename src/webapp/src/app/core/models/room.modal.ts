import { Bloc } from './bloc.model';
import { Site } from './site.modal';

export interface Room {
    classRoomId: string;
    label: string;
    capacity: string;
    pole: string;
    bloc: string;
    startDate: string;
    endDate: string;
    startHour: string;
    endHour: string;
    reason: any;
}