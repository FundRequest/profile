define(["require", "exports", "clipboard", "jquery", "utils", "instant-edit", "bootstrap"], function (require, exports, Clipboard, $) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    var Alert = /** @class */ (function () {
        function Alert() {
        }
        Alert.show = function (message, options) {
            if (options === void 0) { options = null; }
            var container = this._container;
            var myOptions = options ? $.assign(this._options, options) : this._options;
            var newAlert = this._getAlertElement();
            newAlert.querySelector('.alert-content').innerHTML = message;
            newAlert.classList.add("alert-" + myOptions.type);
            while (this._container.children.length > 3) {
                this._container.removeChild(this._container.lastChild);
            }
            this._container.insertBefore(newAlert, this._container.firstChild);
            setTimeout(function () {
                newAlert.classList.add('show');
            }, 100);
            setTimeout(function () {
                $(newAlert).alert('close'); // need jquery because it used bs
            }, myOptions.timeout);
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
    var Main = /** @class */ (function () {
        function Main() {
            var _clipboard = new Clipboard('[data-clipboard-target]');
            _clipboard.on('success', function (e) {
                Alert.show('Copied to your clipboard! ');
                e.clearSelection();
            });
            _clipboard.on('error', function () {
                Alert.show('This browser doesn\'t allow copying to your clipboard, please do it manually');
            });
            $('.fnd-badge[data-toggle="tooltip"]').tooltip();
            new InstantEdit();
            $('body').bootstrapMaterialDesign();
        }
        return Main;
    }());
    new Main();
});
