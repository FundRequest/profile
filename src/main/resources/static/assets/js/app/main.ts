import {InstantEdit} from 'instant-edit';
import * as Clipboard from 'clipboard';
import * as $ from 'jquery';
import 'bootstrap';

class Alert {
    private static _container: HTMLElement = document.getElementById('alert-container');
    private static _options: Object = {
        type: 'success',
        timeout: 3000
    };

    public static show(message: string, options: any = null): void {
        let container = this._container;
        let myOptions = options ? $.assign(this._options, options) : this._options;
        let newAlert = this._getAlertElement();
        newAlert.querySelector('.alert-content').innerHTML = message;
        newAlert.classList.add(`alert-${myOptions.type}`);

        while (this._container.children.length > 3) {
            this._container.removeChild(this._container.lastChild);
        }
        this._container.insertBefore(newAlert, this._container.firstChild);
        setTimeout(function () {
            newAlert.classList.add('show');
        }, 100);

        setTimeout(function () {
            $(newAlert).alert('close'); // need jquery because it used bs
        }, myOptions.timeout);
    }

    private static _getAlertElement(): HTMLElement {
        let element = document.createElement('div');
        element.classList.add('alert', 'alert-dismissible', 'fade');
        element.innerHTML = '<span class="alert-content"></span><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
        return element;
    }
}

class Main {
    constructor() {
        let _clipboard = new Clipboard('[data-clipboard-target]');
        _clipboard.on('success', (e) => {
            Alert.show('Copied to your clipboard! ');
            e.clearSelection();
        });
        _clipboard.on('error', () => {
            Alert.show('This browser doesn\'t allow copying to your clipboard, please do it manually');
        });

        new InstantEdit();
        $('.fnd-badge[data-toggle="tooltip"]').tooltip();
        $('body').bootstrapMaterialDesign();
    }
}

new Main();