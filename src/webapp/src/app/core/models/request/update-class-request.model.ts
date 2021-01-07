export class UpdateClassRequest {
    public classId: string;
    public label: string;
    public nbrStudents: string;
    public nbrGroups: string;
    public specialityId: string;

    constructor(classId: string, label: string, nbrStudents: string, nbrGroups: string, specialityId: string) {
        this.classId = classId;
        this.label = label;
        this.nbrStudents = nbrStudents;
        this.nbrGroups = nbrGroups;
        this.specialityId = specialityId;
    }
}