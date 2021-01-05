export class StudentTable {

    public cin: string;
    public fullName: string;
    public email: string;
    public classe: string;

    constructor(cin: string, lastName: string, firstName: string, email: string, classe: string) {
        this.cin = cin;
        this.fullName = firstName + " " + lastName;
        this.email = email;
        this.classe = classe;
    }
}