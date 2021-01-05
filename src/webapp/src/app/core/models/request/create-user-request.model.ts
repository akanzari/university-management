export class CreateUserRequest {
    public firstName: string;
    public lastname: string;
    public email: string;
    public role: string;

    constructor(firstName: string, lastname: string, email: string, role: string) {
        this.firstName = firstName;
        this.lastname = lastname;
        this.email = email;
        this.role = role;
    }
}