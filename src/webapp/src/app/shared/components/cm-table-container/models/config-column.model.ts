import { PipeTransform } from '@angular/core';
import { AbstractControl, ValidationErrors } from "@angular/forms";
import { Observable } from "rxjs";
import { TSMap } from "typescript-map";

export class ConfigColumn {
	id: string;
	value?: any[];
	actions?: Action[];
	columns: Column[];
	globalValidaros?: any[];
	sortableBy?: string;
	initForm?: boolean = false;
	addable?: boolean = false;
	pagination?: Pagination;
	notEditableCondition?: NotEditableCondition; // condition for you cannot modify a specific line
	selectionMode?: "multiple" | "single" = "multiple";
}

export class Pagination {
	paginate: boolean = false;
	rowsPerPage?: number; // default = 5
	rowsPerPageOptions?: number[]; // default = [5, 10, 15, 20]
}

export class NotEditableCondition {
	field: string;
	value: string[];
}

export class Action {
	name: ActionEnum;
	icon?: Icon;
	condition?: ConditionAction;
}

export class ConditionAction {
	min?: number = 0;
	max?: number = 0;
}

export class Icon {
	class?: string;
	tooltip?: string;
}

export class Column {
	header: string;
	field: string;
	width?: string;
	type?: "monoselect" | "multiselect" | "calendar" | "icon" | "label" | "text" | "link" | "number";
	monoselectConfig?: SelectConfig;
	multiselectConfig?: SelectConfig;
	placeholder?: string;
	pipe?: Pipe;
	unique?: boolean | string;
	editable?: boolean = true;
	event?: boolean = false; // add event to monoselect
	disabled?: boolean = false;
	filterable?: boolean = false;
	sortable?: boolean = false;
	icon?: string;
	action?: ActionEnum;
	dispalyCondition?: any; // à voir
	iconFunction?: any; // à voir
	titleFunction?: any; // à voir
	validations?: Validation;
	defaultValue?: DefaultValue | string;
	validationsWithCondition?: ValidationsWithCondition[]; // à voir
	optionsWithCondition?: OptionsWithCondition[];
	link?: Link;
}

export class Link {
	text?: string;
	url?: string;
}

export class Pipe {
	function: PipeTransform;
	params?: any [];
}

export class OptionsWithCondition {
	dependFromColumn: string;
	value: string;
	values: string[];
}

export class ValidationsWithCondition {
	dependFromColumn: string;
	value: string;
	validators: ((control: AbstractControl) => ValidationErrors)[];
	error: string;
}

export class DefaultValue {
	dependFrom?: string;
	dependFromColumn?: string;
	dependFromAPI?: (param: any) => Observable<any>;
	pathVariable?: string;
	value?: string;
	format?: (object: any) => string;
}

export class Validation {
	validators: ((control: AbstractControl) => ValidationErrors)[];
	errors?: TSMap<string, string>;
	error?: string;
	condition?: Condition;
}

export class Condition {
	field: string;
	is: string;
}

export class SelectConfig {
	type: "objects" | "strings" = "objects";
	options: any[];
	bindLabel?: string;
	bindValue?: string;
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