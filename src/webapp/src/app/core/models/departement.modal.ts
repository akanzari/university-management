import { Site } from './site.modal';
import { Speciality } from './speciality.model';

export interface Department {
    departmentId: string;
    code: string;
    label: string;
    sites: Site[];
    specialities: Speciality[];
}