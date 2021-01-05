import { Class } from './class.model';

export interface Student {
    userId: string;
    cin: string;
    classEntity: Class;
}