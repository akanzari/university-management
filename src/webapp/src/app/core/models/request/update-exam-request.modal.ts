export class UpdateExamRequest {
    public examId: string;
    public classId: string;
    public moduleId: string;
    public examDate: Date;
    public examHour: number;
    public examDuration: number;
    public dsex: string;
    public examType: string;
    public semester: string;
    public examSession: string;
    public classRoomId: string;
    public supervisorId: string;

    constructor(examId: string, classId: string, moduleId: string, examDate: Date, examHour: number, examDuration: number, dsex: string, examType: string,
        semester: string, examSession: string, classRoomId: string, supervisorId: string) {
        this.examId = examId;
        this.classId = classId;
        this.moduleId = moduleId;
        this.examDate = examDate;
        this.examHour = examHour;
        this.examDuration = examDuration;
        this.dsex = dsex;
        this.examType = examType;
        this.semester = semester;
        this.examSession = examSession;
        this.classRoomId = classRoomId;
        this.supervisorId = supervisorId;
    }
}