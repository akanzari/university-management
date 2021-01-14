export class CreateStudentRequest {
    public userId: string;
    public cin: string;
    public classId: string;

    constructor(userId: string, cin: string, classId: string) {
        this.userId = userId;
        this.cin = cin;
        this.classId = classId;
    }
}