define(["require", "exports", "app/alert", "jquery"], function (require, exports, alert_1, $) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    var LinkedIn = /** @class */ (function () {
        function LinkedIn() {
            var _this = this;
            this._postId = null;
            var $button = $('[data-share-linked-in="button"]');
            var $modal = $('#modal-share-linked-in');
            $button.on('click', function (e) {
                $.post('/bounties/linkedin', { 'post-id': _this._postId }, function () {
                    $modal.modal('hide');
                    alert_1.Alert.show('Sharing is caring, thanks!');
                }).fail(function () {
                    alert_1.Alert.show('Oeps, something went wrong, please try again.', { type: 'danger' });
                });
            });
            $modal.on('show.bs.modal', function (e) {
                var message = $('[data-share-linked-in="text"]', e.target)[0];
                $.get('/bounties/linkedin/random-post', function (data) {
                    _this._postId = data.id;
                    message.innerHTML = data.description;
                });
            });
        }
        return LinkedIn;
    }());
    new LinkedIn();
});
