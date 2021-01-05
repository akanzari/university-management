(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["pages-modules-modules-module"],{

/***/ "+4vo":
/*!****************************************************!*\
  !*** ./src/app/pages/modules/modules.component.ts ***!
  \****************************************************/
/*! exports provided: ModulesComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ModulesComponent", function() { return ModulesComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "fXoL");


class ModulesComponent {
    constructor() { }
}
ModulesComponent.ɵfac = function ModulesComponent_Factory(t) { return new (t || ModulesComponent)(); };
ModulesComponent.ɵcmp = _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineComponent"]({ type: ModulesComponent, selectors: [["modules"]], decls: 0, vars: 0, template: function ModulesComponent_Template(rf, ctx) { }, encapsulation: 2 });
/*@__PURE__*/ (function () { _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵsetClassMetadata"](ModulesComponent, [{
        type: _angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"],
        args: [{
                selector: 'modules',
                templateUrl: './modules.component.html'
            }]
    }], function () { return []; }, null); })();


/***/ }),

/***/ "2Tdg":
/*!*************************************************!*\
  !*** ./src/app/pages/modules/modules.module.ts ***!
  \*************************************************/
/*! exports provided: ModulesModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ModulesModule", function() { return ModulesModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "fXoL");
/* harmony import */ var _modules_component__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./modules.component */ "+4vo");



const MODULE_COMPONENT = [
    _modules_component__WEBPACK_IMPORTED_MODULE_1__["ModulesComponent"]
];
class ModulesModule {
}
ModulesModule.ɵmod = _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineNgModule"]({ type: ModulesModule });
ModulesModule.ɵinj = _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineInjector"]({ factory: function ModulesModule_Factory(t) { return new (t || ModulesModule)(); }, imports: [[]] });
(function () { (typeof ngJitMode === "undefined" || ngJitMode) && _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵsetNgModuleScope"](ModulesModule, { declarations: [_modules_component__WEBPACK_IMPORTED_MODULE_1__["ModulesComponent"]] }); })();
/*@__PURE__*/ (function () { _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵsetClassMetadata"](ModulesModule, [{
        type: _angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"],
        args: [{
                declarations: MODULE_COMPONENT,
                imports: []
            }]
    }], null, null); })();


/***/ })

}]);
//# sourceMappingURL=pages-modules-modules-module.js.map