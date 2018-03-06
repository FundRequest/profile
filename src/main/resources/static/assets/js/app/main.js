define(["require", "exports", "jquery", "alert", "instant-edit", "clipboard", "bootstrap"], function (require, exports, $, alert_1, instant_edit_1, Clipboard) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    var Main = /** @class */ (function () {
        function Main() {
            var _clipboard = new Clipboard('[data-clipboard-target]');
            _clipboard.on('success', function (e) {
                alert_1.Alert.show('Copied to your clipboard! ');
                e.clearSelection();
            });
            _clipboard.on('error', function () {
                alert_1.Alert.show('This browser doesn\'t allow copying to your clipboard, please do it manually');
            });
            new instant_edit_1.InstantEdit();
            $('.fnd-badge[data-toggle="tooltip"]').tooltip();
            $('body').bootstrapMaterialDesign();
            alert_1.Alert.show('test', 'danger');
        }
        return Main;
    }());
    new Main();
});
