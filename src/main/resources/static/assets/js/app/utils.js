define(["require", "exports"], function (require, exports) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    var Utils = /** @class */ (function () {
        function Utils() {
        }
        Utils.showLoading = function () {
            document.querySelector('[data-page-loader]').classList.remove('d-none');
        };
        Utils.hideLoading = function () {
            document.querySelector('[data-page-loader]').classList.add('d-none');
        };
        return Utils;
    }());
    exports.Utils = Utils;
});
