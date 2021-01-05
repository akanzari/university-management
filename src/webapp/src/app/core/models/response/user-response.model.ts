export class UserResponse {
    public id: string;
    public username: string;
    public lastName: string;
    public firstName: string;
    public email: string;
    public createdTimestamp: string;
    public roles: string[];

    constructor(id: string, lastName: string, firstName: string, email: string, roles: string[]) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.roles = roles;
    }
}