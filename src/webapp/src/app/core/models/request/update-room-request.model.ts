export class UpdateRoomRequest {
    public classRoomId: string;
    public label: string;
    public capacity: string;
    public siteId: string;
    public blocId: string;
    public startDate: Date;
    public endDate: Date;
    public startHour: number;
    public endHour: number;
    public reasonId: string;

    constructor(classRoomId: string, label: string, capacity: string, blocId: string, siteId: string, startDate: string, endDate: string, startHour: string, endHour: string, reasonId: string) {
        this.classRoomId = classRoomId;
        this.label = label;
        this.capacity = capacity;
        this.blocId = blocId;
        this.siteId = siteId;
        this.startDate = startDate ? new Date(startDate) : null;
        this.endDate = endDate ? new Date(endDate) : null;
        this.startHour = startHour ? +startHour.replace(':', '') : null;
        this.endHour = endHour ? +endHour.replace(':', '') : null;
        this.reasonId = reasonId;
    }
}