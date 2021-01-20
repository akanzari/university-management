import { Speciality } from './speciality.model';

export interface Classs {
    classId: string;
    code: string;
    label: string;
    description: string;
    nbrStudents: string;
    speciality: Speciality;
    category: string;
    email: string;
}