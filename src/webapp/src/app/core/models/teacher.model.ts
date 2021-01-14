import { Department } from './departement.modal';

export interface Teacher {
    userId: string;
    phone: string;
    nbrSurveillance: number;
    nbrHeureSurveillance: number;
    startDate: string;
    endDate: string;
    reason: string;
    department: Department;
}