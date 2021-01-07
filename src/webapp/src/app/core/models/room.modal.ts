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
    reason: string;
    sites: Site[];
}