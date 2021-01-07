export class CreateRoomRequest {
    public label: string;
    public capacity: string;
    public siteIds: string[];

    constructor(label: string, capacity: string, siteIds: string[]) {
        this.label = label;
        this.capacity = capacity;
        this.capacity = capacity;
        this.siteIds = siteIds;
    }
}