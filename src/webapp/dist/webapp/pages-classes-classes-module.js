(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["pages-classes-classes-module"],{

/***/ "OB8Z":
/*!****************************************************!*\
  !*** ./src/app/pages/classes/classes.component.ts ***!
  \****************************************************/
/*! exports provided: ClassesComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ClassesComponent", function() { return ClassesComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "fXoL");


class ClassesComponent {
    constructor() { }
}
ClassesComponent.ɵfac = function ClassesComponent_Factory(t) { return new (t || ClassesComponent)(); };
ClassesComponent.ɵcmp = _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineComponent"]({ type: ClassesComponent, selectors: [["classes"]], decls: 0, vars: 0, template: function ClassesComponent_Template(rf, ctx) { }, encapsulation: 2 });
/*@__PURE__*/ (function () { _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵsetClassMetadata"](ClassesComponent, [{
        type: _angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"],
        args: [{
                selector: 'classes',
                templateUrl: './classes.component.html'
            }]
    }], function () { return []; }, null); })();


/***/ }),

/***/ "sT57":
/*!*************************************************!*\
  !*** ./src/app/pages/classes/classes.module.ts ***!
  \*************************************************/
/*! exports provided: ClassesModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ClassesModule", function() { return ClassesModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "fXoL");
/* harmony import */ var _classes_component__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./classes.component */ "OB8Z");



const MODULE_COMPONENT = [
    _classes_component__WEBPACK_IMPORTED_MODULE_1__["ClassesComponent"]
];
class ClassesModule {
}
ClassesModule.ɵmod = _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineNgModule"]({ type: ClassesModule });
ClassesModule.ɵinj = _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineInjector"]({ factory: function ClassesModule_Factory(t) { return new (t || ClassesModule)(); }, imports: [[]] });
(function () { (typeof ngJitMode === "undefined" || ngJitMode) && _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵsetNgModuleScope"](ClassesModule, { declarations: [_classes_component__WEBPACK_IMPORTED_MODULE_1__["ClassesComponent"]] }); })();
/*@__PURE__*/ (function () { _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵsetClassMetadata"](ClassesModule, [{
        type: _angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"],
        args: [{
                declarations: MODULE_COMPONENT,
                imports: []
            }]
    }], null, null); })();


/***/ })

}]);
//# sourceMappingURL=pages-classes-classes-module.js.map