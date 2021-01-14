import { ExamTypeEnum } from './exam-type.enum';
import { PeriodEnum } from './period.enum';
import { SemesterEnum } from './semester.enum';
import { Teacher } from './teacher.model';
import { Classs } from './class.model';
import { Module } from './module.model';
import { Room } from './room.modal';
import { SessionEnum } from './session.enum';
import { DsexEnum } from './dsex.enum';

export interface Exam {
    examId: string;
    examDate: string;
    examHour: number;
    examDuration: number;
    examType: ExamTypeEnum;
    semester: SemesterEnum;
    examSession: SessionEnum;
    dsex: DsexEnum;
    classs: Classs;
    module: Module;
    classRoom: Room;
    supervisor: Teacher;
}