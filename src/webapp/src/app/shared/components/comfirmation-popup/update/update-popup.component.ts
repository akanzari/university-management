import { DatePipe } from '@angular/common';
import { Component, Input, ViewEncapsulation, HostListener, Output, EventEmitter } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
    templateUrl: './update-popup.component.html',
    styleUrls: ['./update-popup.component.scss'],
    encapsulation: ViewEncapsulation.None,
    providers: [DatePipe]
})
export class UpdatePopupComponent {

    @Input()
    public config: any;

    @Output()
    public sendData: EventEmitter<any> = new EventEmitter();

    constructor(private activeModal: NgbActiveModal, ) { }

    public confirm(): void {
        this.sendData.emit(true);
    }

    @HostListener('document:keydown.escape', ['$event']) onKeydownHandler(event: KeyboardEvent) {
        this.closeModal();
    }

    @HostListener('document:keypress', ['$event']) handleKeyboardEvent(event: KeyboardEvent) {
        this.confirm();
    }

    public closeModal(): void {
        this.activeModal.close();
    }

}