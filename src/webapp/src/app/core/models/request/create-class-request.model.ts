export class CreateClassRequest {
    public code: string;
    public label: string;
    public nbrStudents: string;
    public nbrGroups: string;
    public specialityId: string;

    constructor(code: string, label: string, nbrStudents: string, nbrGroups: string, specialityId: string) {
        this.code = code;
        this.label = label;
        this.nbrStudents = nbrStudents;
        this.nbrGroups = nbrGroups;
        this.specialityId = specialityId;
    }
}