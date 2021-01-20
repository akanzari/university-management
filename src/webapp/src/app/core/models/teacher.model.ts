import { Department } from './departement.modal';

export interface Teacher {
    teacherId: string;
    cin: number;
    sex: string;
    fullName: string;
    email: string;
    phones: string[];
    nbrSurveillance: number;
    nbrHeureSurveillance: number;
    departement: Department;
}