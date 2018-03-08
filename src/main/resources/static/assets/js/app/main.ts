import * as $ from 'jquery';
import 'bootstrap';
import * as ClipboardJS from 'clipboard';
import {Alert} from './alert';
import {InstantEdit} from './instant-edit';
import {OpenLinkInPopup} from "./open-link-in-popup";

class Main {
    constructor() {
        let _clipboard = new ClipboardJS('[data-clipboard-target]');
        _clipboard.on('success', (e) => {
            Alert.show('Copied to your clipboard! ');
            e.clearSelection();
        });
        _clipboard.on('error', () => {
            Alert.show('This browser doesn\'t allow copying to your clipboard, please do it manually');
        });

        new InstantEdit();
        new OpenLinkInPopup();
        $('.fnd-badge[data-toggle="tooltip"]').tooltip();
        $('body').bootstrapMaterialDesign();
    }
}

new Main();