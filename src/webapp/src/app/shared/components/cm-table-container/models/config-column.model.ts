import { PipeTransform } from '@angular/core';
import { TSMap } from 'typescript-map';

export class ConfigColumn {
	id: string;
	value?: any[];
	actions?: Action[];
	conditionActions?: any;
	columns: Column[];
	sortableBy?: string;
	pagination?: Pagination;
}

export class Pagination {
	paginate: boolean = false;
	rowsPerPage?: number; // default = 5
	rowsPerPageOptions?: number[]; // default = [5, 10, 15, 20]
}

export class Action {
	name: ActionEnum;
	icon?: Icon;
	condition?: {};
}

export class Icon {
	class?: string;
	tooltip?: string;
}

export class Column {
	header: string;
	field: string;
	width?: string;
	pipe?: Pipe;
	icon?: Icon;
	conditionClass?: TSMap<string, string>;
	filterable?: boolean = false;
	sortable?: boolean = false;
	link?: Link;
}

export class Link {
	text?: string;
	url?: string;
}

export class Pipe {
	function: PipeTransform;
	params?: any[];
}

export enum ActionEnum {
	DISPLAY = "dispaly",
	CREATE = "create",
	UPDATE = "update",
	DELETE = "delete",
	CLONE = "clone",
	DUPLICATE = "duplicate",
	ARCHIVE = "archive",
	ACTIVATE = "activate",
	DEACTIVATE = "deactivate",
	REATTACH = "reattach",
	DETACH = "detach",
	COPY = "copy",
	DRAG = "drag",
	ALERT = "alert",
	CHECK = "check",
	BASKET = "basket",
	CANCEL = "cancel",
	ENTER = "enter",
	DOWNLOAD = "download",
	EXIT = "exit",
	LINK = "link",
	UNLINK = "unlink",
	LOCK = "lock",
	UNLOCK = "unlock",
	SETTING = "setting",
	SHARE = "share",
	UPLOAD = "upload",
	WARNING = "warning",
	INFO = "info",
	GOTO = "go-to"
};