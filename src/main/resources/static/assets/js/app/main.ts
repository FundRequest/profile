import * as $ from 'jquery';
import 'bootstrap';
import {Alert} from 'alert';
import {InstantEdit} from 'instant-edit';
import * as Clipboard from 'clipboard';

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

        Alert.show('test', 'danger');
    }
}

new Main();