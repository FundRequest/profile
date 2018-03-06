define(["require", "exports", "jquery"], function (require, exports, $) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    var LinkedIn = /** @class */ (function () {
        function LinkedIn() {
            var _this = this;
            this._postId = null;
            this._message = null;
            var self = this;
            $('[data-share-linked-in="button"]').on('click', function (e) {
                $.post('/bounties/linkedin', { 'post-id': _this._postId }, {
                    error: function () {
                        console.log('error sharing');
                    },
                    success: function () {
                        console.log('shared');
                    }
                });
            });
            $('#modal-share-linked-in').on('show.bs.modal', function (e) {
                var message = $('[data-share-linked-in="text"]', e.target)[0];
                $.get('/bounties/linkedin/random-post', function (data) {
                    self._postId = data.id;
                    message.innerHTML = data.description;
                });
            });
        }
        return LinkedIn;
    }());
    new LinkedIn();
});
