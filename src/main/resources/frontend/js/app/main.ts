import 'mdb';
import * as $ from 'jquery';
import * as ClipboardJS from 'clipboard';
import {Alert} from './alert';
import {InstantEdit} from './instant-edit';
import {OpenLinkInPopup} from "./open-link-in-popup";

import {LinkedIn} from './linkedin';
import {Twitter} from './twitter';
import {Referrals} from './referrals';

class Main {
    constructor() {
        $(function() {
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

            new LinkedIn();
            new Twitter();
            new Referrals();
        });
    }
}

new Main();