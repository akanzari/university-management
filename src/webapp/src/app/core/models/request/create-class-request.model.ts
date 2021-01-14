export class CreateClassRequest {
    public label: string;
    public nbrStudents: string;
    public nbrGroups: string;
    public specialityId: string;

    constructor(label: string, nbrStudents: string, nbrGroups: string, specialityId: string) {
        this.label = label;
        this.nbrStudents = nbrStudents;
        this.nbrGroups = nbrGroups;
        this.specialityId = specialityId;
    }
}