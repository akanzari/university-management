import { Directive, ElementRef, OnInit, Renderer2, Input } from '@angular/core';

@Directive({
    selector: '[columnWidth]'
})
export class WidthDirective implements OnInit {

    @Input('columnWidth') columnWidth: string;
    @Input('columnsLength') columnsLength: number;
    @Input('selectionMode') selectionMode!: boolean;

    constructor(
        private el: ElementRef,
        private renderer: Renderer2
    ) { }

    ngOnInit() {
        if (this.columnWidth) {
            this.renderer.setStyle(this.el.nativeElement, 'width', this.columnWidth + '%');
        } else {
            if (!this.selectionMode) {
                this.renderer.setStyle(this.el.nativeElement, 'width', 92 / this.columnsLength + '%');
            } else {
                this.renderer.setStyle(this.el.nativeElement, 'width', 87 / this.columnsLength + '%');
            }
        }
    }

}