define(["require", "exports", "./alert", "./utils", "jquery"], function (require, exports, alert_1, utils_1, $) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    var Twitter = /** @class */ (function () {
        function Twitter() {
            var button = document.querySelector('[data-verify-twitter="button"]');
            var modal = document.querySelector('#modal-verify-twitter');
            button.addEventListener('click', function (e) {
                utils_1.Utils.showLoading();
                $.get('/bounties/twitter/validated', function (data) {
                    alert_1.Alert.show(data.message, data.verified ? 'success' : 'danger');
                    if (data.verified) {
                        $(modal).modal('hide');
                    }
                }).fail(function () {
                    alert_1.Alert.show('Oeps, something went wrong, please try again.', { type: 'danger' });
                }).always(function () {
                    utils_1.Utils.hideLoading();
                });
            });
        }
        return Twitter;
    }());
    exports.Twitter = Twitter;
});
