define(["require", "exports"], function (require, exports) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    var OpenLinkInPopup = /** @class */ (function () {
        function OpenLinkInPopup() {
            this._document = document;
            var links = [].slice.call(this._document.querySelectorAll('a[data-open-link-in-popup]'));
            links.forEach(function (item) {
                item.addEventListener('click', function (e) {
                    e.preventDefault();
                    var newWindow = OpenLinkInPopup._getNewWindow(item.href, 600, 600);
                    if (window.focus) {
                        newWindow.focus();
                    }
                    return false;
                });
            });
        }
        OpenLinkInPopup._getNewWindow = function (url, widthPopup, heightPopup) {
            var left = (screen.width / 2) - (widthPopup / 2);
            var top = (screen.height / 2) - (heightPopup / 2);
            var newWindow = window.open(null, 'popup', 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width=' + widthPopup + ', height=' + heightPopup + ', top=' + top + ', left=' + left);
            newWindow.opener = null;
            newWindow.location.assign(url);
            return newWindow;
        };
        return OpenLinkInPopup;
    }());
    exports.OpenLinkInPopup = OpenLinkInPopup;
});
