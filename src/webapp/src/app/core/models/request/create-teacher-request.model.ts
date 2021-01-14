export class CreateTeacherRequest {
    public userId: string;
    public phone: string;
    public departmentId: string;

    constructor(userId: string, phone: string, departmentId: string) {
        this.userId = userId;
        this.phone = phone;
        this.departmentId = departmentId;
    }
}