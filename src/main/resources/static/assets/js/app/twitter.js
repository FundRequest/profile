define(["require", "exports", "./alert", "./utils", "jquery"], function (require, exports, alert_1, utils_1, $) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    var Twitter = /** @class */ (function () {
        function Twitter() {
            var _this = this;
            var modal = document.querySelector('#modal-twitter-verify');
            var button = document.querySelector('[data-twitter-verify]');
            var buttonTweet = document.querySelector('[data-twitter-tweet]');
            button.addEventListener('click', function (e) {
                _this._verify(function () { $(modal).modal('hide'); });
            });
            buttonTweet.addEventListener('click', function (e) {
                var text = buttonTweet.dataset.twitterTweet;
                var link = "http://twitter.com/home?status=" + encodeURIComponent(text);
                var twitterWindow = utils_1.Utils.getNewWindow(link, 600, 600);
                /*
                let winTimer = window.setInterval(function () {
                    if (twitterWindow.closed !== false) {
                        window.clearInterval(winTimer);
                        this._verify();
                    }
                }, 200);*/
            });
        }
        Twitter.prototype._verify = function (callback) {
            if (callback === void 0) { callback = null; }
            utils_1.Utils.showLoading();
            $.get('/bounties/twitter/verify', function (data) {
                alert_1.Alert.show(data.message, data.verified ? 'success' : 'danger');
                if (data.verified) {
                    callback != null ? callback() : null;
                }
            }).fail(function () {
                alert_1.Alert.show('Oeps, something went wrong, please try again.', 'danger');
            }).always(function () {
                utils_1.Utils.hideLoading();
            });
        };
        return Twitter;
    }());
    new Twitter();
});
