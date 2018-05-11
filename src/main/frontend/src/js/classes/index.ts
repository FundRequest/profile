import * as $ from 'jquery';

import * as ClipboardJS from 'clipboard';
import {Alert} from './alert';
import {InstantEdit} from './instant-edit';
import {OpenLinkInPopup} from './open-link-in-popup';
import LinkedIn from "./linkedin";
import Twitter from "./twitter";
import Referrals from "./referrals";

class Index {
    constructor() {
        $(function () {
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
            new Twitter();
            new LinkedIn();
            new Referrals();

            $('.fnd-badge[data-toggle="tooltip"]').tooltip();

            setTimeout(function () {
                document.body.classList.remove('preload');
            }, 500);
        });
    }
}

new Index();