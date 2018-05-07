define(["require", "exports", "./alert", "jquery"], function (require, exports, alert_1, $) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    var LinkedIn = (function () {
        function LinkedIn() {
            var _this = this;
            this._postId = null;
            var button = document.querySelector('[data-share-linked-in="button"]');
            var modal = document.querySelector('#modal-share-linked-in');
            if (button != null) {
                button.addEventListener('click', function (e) {
                    $.post('/bounties/linkedin', { 'post-id': _this._postId }).promise()
                        .then(function () {
                        $(modal).modal('hide');
                        alert_1.Alert.show('Sharing is caring, thanks!');
                    })
                        .catch(function () {
                        alert_1.Alert.show('Oops, something went wrong, please try again.', 'danger');
                    });
                });
                $(modal).on('show.bs.modal', function (e) {
                    var target = e.target;
                    var title = target.querySelector('[data-share-linked-in="title"]');
                    var message = target.querySelector('[data-share-linked-in="text"]');
                    var image = target.querySelector('[data-share-linked-in="image"]');
                    var url = target.querySelector('[data-share-linked-in="url"]');
                    _this._getLinkedInPost()
                        .then(function (data) {
                        _this._postId = data.id;
                        url ? url.href = data.submittedUrl : null;
                        image ? image.src = data.submittedImageUrl : null;
                        title ? title.innerHTML = data.title : null;
                        message ? message.innerHTML = data.description : null;
                    })
                        .catch(function (ex) {
                        console.log('Something when wrong during getting LinkedIn post', ex);
                    });
                });
            }
        }
        LinkedIn.prototype._getLinkedInPost = function () {
            return $.get('/bounties/linkedin/random-post').promise();
        };
        return LinkedIn;
    }());
    new LinkedIn();
});
