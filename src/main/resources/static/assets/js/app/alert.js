define(["require", "exports", "jquery"], function (require, exports, $) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    var Alert = (function () {
        function Alert() {
        }
        Alert.show = function (message, type) {
            if (type === void 0) { type = null; }
            this._options.type = type ? type : this._options.type;
            var newAlert = this._getAlertElement();
            newAlert.querySelector('.alert-content').innerHTML = message;
            newAlert.classList.add("alert-" + this._options.type);
            while (this._container.children.length > 3) {
                this._container.removeChild(this._container.lastChild);
            }
            this._container.insertBefore(newAlert, this._container.firstChild);
            setTimeout(function () {
                newAlert.classList.add('show');
            }, 100);
            setTimeout(function () {
                $(newAlert).alert('close');
            }, this._options.timeout);
        };
        Alert._getAlertElement = function () {
            var element = document.createElement('div');
            element.classList.add('alert', 'alert-dismissible', 'fade');
            element.innerHTML = '<span class="alert-content"></span><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
            return element;
        };
        Alert._container = document.getElementById('alert-container');
        Alert._options = {
            type: 'success',
            timeout: 3000
        };
        return Alert;
    }());
    exports.Alert = Alert;
});
