import {Utils} from 'utils';
import * as $ from 'jquery';

export class InstantEdit {
    private _document: HTMLDocument = document;
    private _invalidFormClass: string = 'is-invalid';
    private _invalidMessageClass: string = 'invalid-feedback';

    constructor() {
        let list = this._document.querySelectorAll('[data-edit]');
        for (let i = 0; i < list.length; i++) {
            let li: HTMLInputElement = list.item(i) as HTMLInputElement;
            let name = li.dataset.edit;
            li.addEventListener('focusin', () => {
                li.classList.remove(this._invalidFormClass);
            });
            li.addEventListener('focusout', () => {
                li.setSelectionRange(0, 0);
                this._saveItem(li, name);
            });
        }
    }

    private _validateItem(field, name) {
        let valid = true;
        let message = '';
        let value = field.value;
        let validationValue = field.dataset.editValidation;
        let validations = validationValue.split(',');

        for (let i = 0; i < validations.length && valid; i++) {
            let validation = validations[i];
            switch (validation) {
                case 'required':
                    valid = value.length;
                    message = 'Field is required';
                    break;
                case 'ethereum':
                    valid = value.length > 0 ? value.match(/^0x[a-fA-F0-9]{40}$/) : true;
                    message = 'Not a valid ethereum address';
                    break;
                case 'telegram':
                    valid = value.length > 0;
                    message = 'Not a valid telegram name';
                    break;
            }
        }

        if (!valid) {
            this._showError(field, name, message);
        } else {
            this._hideError(field, name);
        }

        return valid;
    }

    private _loadItem(field, name) {
        field.value = localStorage.getItem(`fnd.values.${name}`);
    }

    private _saveItem(field, name) {
        let isValid = this._validateItem(field, name);
        let self = this;

        if (!isValid) {
            return;
        }

        Utils.showLoading();
        let error: boolean = false;

        $.post('/profile/etheraddress', {
            etheraddress: field.value,
            error: () => {
                self._showError(field, name, 'Something went wrong.');
            },
            success: () => {
                Utils.hideLoading();
                self._hideError(field, name);
            }
        });

    }

    private _hideError(field, name) {
        this._showHideError(field, name);
    }

    private _showError(field, name, message) {
        this._showHideError(field, name, true, message);
    }

    private _showHideError(field, name, show = false, errorMessage = '') {
        let messageField: HTMLElement = this._document.querySelector(`[data-edit-messages="${name}"]`) as HTMLElement;
        show ? messageField.classList.add(this._invalidMessageClass) : messageField.classList.remove(this._invalidMessageClass);
        show ? messageField.innerHTML = errorMessage : messageField.innerHTML = '';
        show ? field.classList.add(this._invalidFormClass) : field.classList.remove(this._invalidFormClass);
    }
}
