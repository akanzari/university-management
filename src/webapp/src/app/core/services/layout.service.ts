import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable()
export class LayoutService {

    public isSideBarDisplay: Boolean = true;
    public isNavBarDisplay: Boolean = true;
    public isFullScreen: Boolean = false;
    private doc = <FullScreenDocument>document;
    sidebarSubject: BehaviorSubject<boolean>;

    constructor() {
        this.sidebarSubject = new BehaviorSubject<boolean>(true);
    }

    public toggleNavBarMenu(): void {
        this.isNavBarDisplay = !this.isNavBarDisplay;
    }

    public toggleSideBarDisplay(): void {
        this.isSideBarDisplay = !this.isSideBarDisplay;
        const event = new CustomEvent("reduce", { "detail": "reduce sidebar" });
        document.dispatchEvent(event);
    }

    public toggleFullScreen(): void {
        this.isFullScreen = this.toggle();
    }

    public showSidedar(show: boolean) {
        this.sidebarSubject.next(show);
    }

    public getSidebarState(): Observable<boolean> {
        return this.sidebarSubject.asObservable();
    }

    public isDesktop() {
        return window.innerWidth > 1024;
    }

    enter() {
        const el = this.doc.documentElement;
        if (el.requestFullscreen) el.requestFullscreen();
        else if (el.msRequestFullscreen) el.msRequestFullscreen();
        else if (el.mozRequestFullScreen) el.mozRequestFullScreen();
        else if (el.webkitRequestFullscreen) el.webkitRequestFullscreen();
    }

    leave() {
        if (this.doc.exitFullscreen) this.doc.exitFullscreen();
        else if (this.doc.msExitFullscreen) this.doc.msExitFullscreen();
        else if (this.doc.mozCancelFullScreen) this.doc.mozCancelFullScreen();
        else if (this.doc.webkitExitFullscreen) this.doc.webkitExitFullscreen();
    }

    toggle(): Boolean {
        if (this.enabled) { this.leave(); return true; }
        else { this.enter(); return false; }
    }

    get enabled() {
        return !!(
            this.doc.fullscreenElement ||
            this.doc.mozFullScreenElement ||
            this.doc.webkitFullscreenElement ||
            this.doc.msFullscreenElement
        );
    }

}

interface FullScreenDocument extends HTMLDocument {
    documentElement: FullScreenDocumentElement;
    mozFullScreenElement?: Element;
    msFullscreenElement?: Element;
    webkitFullscreenElement?: Element;
    msExitFullscreen?: () => void;
    mozCancelFullScreen?: () => void;
    webkitExitFullscreen?: () => void;
}

interface FullScreenDocumentElement extends HTMLElement {
    msRequestFullscreen?: () => void;
    mozRequestFullScreen?: () => void;
    webkitRequestFullscreen?: () => void;
}