define(["require", "exports", "./alert", "./utils", "jquery"], function (require, exports, alert_1, utils_1, $) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    var Twitter = (function () {
        function Twitter() {
            var _this = this;
            var modal = document.querySelector('#modal-twitter-verify');
            var button = document.querySelector('[data-twitter-verify]');
            var buttonTweet = document.querySelector('[data-twitter-tweet]');
            if (button != null) {
                button.addEventListener('click', function (e) {
                    _this._verify(function () {
                        $(modal).modal('hide');
                    });
                });
                buttonTweet.addEventListener('click', function (e) {
                    var text = buttonTweet.dataset.twitterTweet;
                    var link = "http://twitter.com/home?status=" + encodeURIComponent(text);
                    var twitterWindow = utils_1.Utils.getNewWindow(link, 600, 600);
                });
            }
        }
        Twitter.prototype._verify = function (callback) {
            if (callback === void 0) { callback = null; }
            utils_1.Utils.showLoading();
            this._getVerify()
                .then(function (data) {
                alert_1.Alert.show(data.message, data.validated ? 'success' : 'danger');
                if (data.validated) {
                    callback != null ? callback() : null;
                }
                utils_1.Utils.hideLoading();
            })
                .catch(function (ex) {
                alert_1.Alert.show('Oops, something went wrong, please try again.', 'danger');
                utils_1.Utils.hideLoading();
            });
        };
        Twitter.prototype._getVerify = function () {
            return $.get('/bounties/twitter/verify').promise();
        };
        return Twitter;
    }());
    new Twitter();
});
