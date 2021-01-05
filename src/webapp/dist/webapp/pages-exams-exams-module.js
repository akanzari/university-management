(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["pages-exams-exams-module"],{

/***/ "NmF6":
/*!************************************************!*\
  !*** ./src/app/pages/exams/exams.component.ts ***!
  \************************************************/
/*! exports provided: ExamsComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ExamsComponent", function() { return ExamsComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "fXoL");


class ExamsComponent {
    constructor() { }
}
ExamsComponent.ɵfac = function ExamsComponent_Factory(t) { return new (t || ExamsComponent)(); };
ExamsComponent.ɵcmp = _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineComponent"]({ type: ExamsComponent, selectors: [["exams"]], decls: 0, vars: 0, template: function ExamsComponent_Template(rf, ctx) { }, encapsulation: 2 });
/*@__PURE__*/ (function () { _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵsetClassMetadata"](ExamsComponent, [{
        type: _angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"],
        args: [{
                selector: 'exams',
                templateUrl: './exams.component.html'
            }]
    }], function () { return []; }, null); })();


/***/ }),

/***/ "szUC":
/*!*********************************************!*\
  !*** ./src/app/pages/exams/exams.module.ts ***!
  \*********************************************/
/*! exports provided: ExamsModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ExamsModule", function() { return ExamsModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "fXoL");
/* harmony import */ var _exams_component__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./exams.component */ "NmF6");



const MODULE_COMPONENT = [
    _exams_component__WEBPACK_IMPORTED_MODULE_1__["ExamsComponent"]
];
class ExamsModule {
}
ExamsModule.ɵmod = _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineNgModule"]({ type: ExamsModule });
ExamsModule.ɵinj = _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineInjector"]({ factory: function ExamsModule_Factory(t) { return new (t || ExamsModule)(); }, imports: [[]] });
(function () { (typeof ngJitMode === "undefined" || ngJitMode) && _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵsetNgModuleScope"](ExamsModule, { declarations: [_exams_component__WEBPACK_IMPORTED_MODULE_1__["ExamsComponent"]] }); })();
/*@__PURE__*/ (function () { _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵsetClassMetadata"](ExamsModule, [{
        type: _angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"],
        args: [{
                declarations: MODULE_COMPONENT,
                imports: []
            }]
    }], null, null); })();


/***/ })

}]);
//# sourceMappingURL=pages-exams-exams-module.js.map