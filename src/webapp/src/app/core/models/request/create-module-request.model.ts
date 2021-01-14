import { PeriodEnum } from '../period.enum';
import { SemesterEnum } from '../semester.enum';
import { ExamTypeEnum } from '../exam-type.enum';

export class CreateModuleRequest {
    public designation: string;
    public nbrHours: number;
    public coefficient: number;
    public semester: SemesterEnum;
    public periods: PeriodEnum[];
    public examType: ExamTypeEnum;
    public teacherId: string;
    public classId: string;

    constructor(designation: string, nbrHours: number, coefficient: number, semester: SemesterEnum, periods: PeriodEnum[], examType: ExamTypeEnum, teacherId: string, classId: string) {
        this.designation = designation;
        this.nbrHours = nbrHours;
        this.coefficient = coefficient;
        this.semester = semester;
        this.periods = periods;
        this.examType = examType;
        this.teacherId = teacherId;
        this.classId = classId;
    }
}