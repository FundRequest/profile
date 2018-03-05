class InstantEdit {
    constructor() {
        this._document = document;
        this._invalidFormClass = 'is-invalid';
        this._validFormClass = 'is-valid';
        this._invalidMessageClass = 'invalid-feedback';
        this._isLoadingClass = 'is-loading';
        let list = this._document.querySelectorAll('[data-edit]');
        for (let i = 0; i < list.length; i++) {
            let li = list.item(i);
            let name = li.dataset.edit;
            li.addEventListener('focusin', () => {
                li.classList.remove(this._invalidFormClass);
            });
            li.addEventListener('focusout', () => {
                li.setSelectionRange(0, 0);
                this._saveItem(li, name);
            });
            // TODO: remove dummy data
            this._loadItem(li, name);
        }
    }
    _validateItem(field, name) {
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
            }
        }
        if (!valid) {
            this._showError(field, name, message);
        }
        else {
            this._hideError(field, name);
        }
        return valid;
    }
    _loadItem(field, name) {
        field.value = localStorage.getItem(`fnd.values.${name}`);
    }
    _saveItem(field, name) {
        let isValid = this._validateItem(field, name);
        if (!isValid) {
            return;
        }
        this._addLoading(field, name);
        let error = false;
        localStorage.setItem(`fnd.values.${name}`, field.value);
        /*
            // get field
            // get validation attributes and do validation
                // if not ok
                    // show message

                // if ok
                    // do ajax call to update item
                        // show loader
                            // remove loader
                            // see if succeeded
                        // see if error
                            // remove loader
                            // show error message
         */
        setTimeout(() => {
            this._removeLoading(field, name);
            if (error) {
                this._showError(field, name, 'Not yet implemented');
            }
            else {
                this._hideError(field, name);
                field.classList.add(this._validFormClass);
                setTimeout(() => {
                    field.classList.remove(this._validFormClass);
                }, 1000);
            }
        }, 3000);
    }
    _addLoading(field, name) {
        field.classList.add(this._isLoadingClass);
    }
    _removeLoading(field, name) {
        field.classList.remove(this._isLoadingClass);
    }
    _hideError(field, name) {
        this._showHideError(field, name);
    }
    _showError(field, name, message) {
        this._showHideError(field, name, true, message);
    }
    _showHideError(field, name, show = false, errorMessage = '') {
        let messageField = this._document.querySelector(`[data-edit-messages="${name}"]`);
        show ? messageField.classList.add(this._invalidMessageClass) : messageField.classList.remove(this._invalidMessageClass);
        show ? messageField.innerHTML = errorMessage : messageField.innerHTML = '';
        show ? field.classList.add(this._invalidFormClass) : field.classList.remove(this._invalidFormClass);
    }
}
