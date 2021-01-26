export class UpdateRoomRequest {
    public classRoomId: string;
    public label: string;
    public capacity: string;
    public siteId: string;
    public blocId: string;
    constructor(classRoomId: string, label: string, capacity: string, blocId: string, siteId: string) {
        this.classRoomId = classRoomId;
        this.label = label;
        this.capacity = capacity;
        this.blocId = blocId;
        this.siteId = siteId;
    }
}