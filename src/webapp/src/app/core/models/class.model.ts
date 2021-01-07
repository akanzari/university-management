import { Speciality } from './speciality.model';

export interface Class {
    classId: string;
    code: string;
    label: string;
    nbrStudents: string;
    nbrGroups: string;
    speciality: Speciality;
}