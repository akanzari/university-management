export class UpdateRoomRequest {
    public classRoomId: string;
    public label: string;
    public capacity: string;
    public siteIds: string[];

    constructor(classRoomId: string, label: string, capacity: string, siteIds: string[]) {
        this.classRoomId = classRoomId;
        this.label = label;
        this.capacity = capacity;
        this.capacity = capacity;
        this.siteIds = siteIds;
    }
}