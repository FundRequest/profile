define(["require", "exports"], function (require, exports) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    var Utils = (function () {
        function Utils() {
        }
        Utils.showLoading = function () {
            document.querySelector('[data-page-loader]').classList.remove('d-none');
        };
        Utils.hideLoading = function () {
            document.querySelector('[data-page-loader]').classList.add('d-none');
        };
        Utils.getNewWindow = function (url, widthPopup, heightPopup) {
            var left = (screen.width / 2) - (widthPopup / 2);
            var top = (screen.height / 2) - (heightPopup / 2);
            var newWindow = window.open(null, 'popup', 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width=' + widthPopup + ', height=' + heightPopup + ', top=' + top + ', left=' + left);
            newWindow.opener = null;
            newWindow.location.assign(url);
            return newWindow;
        };
        return Utils;
    }());
    exports.Utils = Utils;
});
