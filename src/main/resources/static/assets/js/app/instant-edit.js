define(["require", "exports", "./alert", "./utils"], function (require, exports, alert_1, utils_1) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    var InstantEdit = (function () {
        function InstantEdit() {
            var _this = this;
            this._document = document;
            this._invalidFormClass = 'is-invalid';
            this._invalidMessageClass = 'invalid-feedback';
            var list = this._document.querySelectorAll('[data-edit]');
            var _loop_1 = function (i) {
                var li = list.item(i);
                var name_1 = li.dataset.edit;
                li.addEventListener('focusin', function () {
                    li.classList.remove(_this._invalidFormClass);
                });
                li.addEventListener('focusout', function () {
                    _this._saveItem(li, name_1);
                });
            };
            for (var i = 0; i < list.length; i++) {
                _loop_1(i);
            }
        }
        InstantEdit.prototype._validateItem = function (field, name) {
            var valid = true;
            var message = '';
            var value = field.value;
            var validationValue = field.dataset.editValidation;
            var validations = validationValue.split(',');
            for (var i = 0; i < validations.length && valid; i++) {
                var validation = validations[i];
                switch (validation) {
                    case 'required':
                        valid = value.length;
                        message = 'Field is required';
                        break;
                    case 'ethereum':
                        valid = value.length > 0 ? /^0x[a-fA-F0-9]{40}$/.test(value) : true;
                        message = 'Not a valid ethereum address';
                        break;
                    case 'telegram-handle':
                        valid = value.length > 0 ? /^@?[a-zA-Z][a-zA-Z0-9_]*[a-zA-Z0-9]$/.test(value) : false;
                        message = 'Telegram handle is required, you can use a-z, 0-9 and underscores.';
                        break;
                }
            }
            if (!valid) {
                this._showError(field, name, message);
            }
            else {
                this._hideError(field, name);
            }
            return valid;
        };
        InstantEdit.prototype._saveItem = function (field, name) {
            var isValid = this._validateItem(field, name);
            var self = this;
            var title = null;
            var postAddress = null;
            var data = null;
            if (!isValid) {
                return;
            }
            utils_1.Utils.showLoading();
            switch (field.dataset.edit) {
                case 'eth-address':
                    title = 'ETH address';
                    postAddress = '/profile/etheraddress';
                    data = { etheraddress: field.value };
                    break;
                case 'telegram-handle':
                    title = 'Telegram handle';
                    postAddress = '/profile/telegramname';
                    data = { telegramname: field.value };
                    break;
                default:
                    utils_1.Utils.hideLoading();
                    return;
            }
            $.post(postAddress, data).promise()
                .then(function () {
                self._hideError(field, name);
                alert_1.Alert.show(title + " saved!");
            })
                .catch(function () {
                self._showError(field, name, 'Something went wrong.');
            })
                .always(function () {
                utils_1.Utils.hideLoading();
                self._showHideEmptyMessage(field, name);
            });
        };
        InstantEdit.prototype._hideError = function (field, name) {
            this._showHideError(field, name);
        };
        InstantEdit.prototype._showError = function (field, name, message) {
            this._showHideError(field, name, true, message);
        };
        InstantEdit.prototype._showHideError = function (field, name, show, errorMessage) {
            if (show === void 0) { show = false; }
            if (errorMessage === void 0) { errorMessage = ''; }
            var messageField = this._document.querySelector("[data-edit-messages=\"" + name + "\"]");
            show ? messageField.classList.add(this._invalidMessageClass) : messageField.classList.remove(this._invalidMessageClass);
            show ? messageField.innerHTML = errorMessage : messageField.innerHTML = '';
            show ? field.classList.add(this._invalidFormClass) : field.classList.remove(this._invalidFormClass);
        };
        InstantEdit.prototype._showHideEmptyMessage = function (field, name) {
            var messageField = this._document.querySelector("[data-edit-empty-message=\"" + name + "\"]");
            if (messageField != null) {
                field.value && field.value.trim().length > 0 ? messageField.classList.add('d-none') : messageField.classList.remove('d-none');
            }
        };
        return InstantEdit;
    }());
    exports.InstantEdit = InstantEdit;
});
