define(["require", "exports", "./utils"], function (require, exports, utils_1) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    var OpenLinkInPopup = (function () {
        function OpenLinkInPopup() {
            this._document = document;
            var links = [].slice.call(this._document.querySelectorAll('a[data-open-link-in-popup]'));
            links.forEach(function (item) {
                item.addEventListener('click', function (e) {
                    e.preventDefault();
                    var newWindow = utils_1.Utils.getNewWindow(item.href, 600, 600);
                    if (window.focus) {
                        newWindow.focus();
                    }
                    return false;
                });
            });
        }
        return OpenLinkInPopup;
    }());
    exports.OpenLinkInPopup = OpenLinkInPopup;
});
