import { ExamTypeEnum } from './exam-type.enum';
import { PeriodEnum } from './period.enum';
import { SemesterEnum } from './semester.enum';
import { Teacher } from './teacher.model';
import { Classs } from './class.model';

export interface Module {
    moduleId: string;
    designation: string;
    nbrHours: number;
    coefficient: number;
    semester: SemesterEnum;
    periods: PeriodEnum[];
    examType: ExamTypeEnum;
    teacher: Teacher;
    classs: Classs;
}