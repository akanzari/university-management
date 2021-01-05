import { Component, Input, ViewEncapsulation, HostListener, Output, EventEmitter } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
    templateUrl: './remove-popup.component.html',
    styleUrls: ['./remove-popup.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class RemovePopupComponent {

    @Input()
    public config: any;

    @Output()
    public sendData: EventEmitter<any> = new EventEmitter();

    constructor(private activeModal: NgbActiveModal) { }

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