import {Alert} from './alert';
import {Utils} from "./utils";
import * as $ from 'jquery';

interface VerifyResponse {
    verified: boolean;
    message: string;
}

export class Twitter {
    constructor() {
        let button = document.querySelector('[data-verify-twitter="button"]');
        let modal = document.querySelector('#modal-verify-twitter');

        button.addEventListener('click', (e) => {
            Utils.showLoading();
            $.get('/bounties/twitter/validated', (data: VerifyResponse) => {
                Alert.show(data.message, data.verified ? 'success' : 'danger');
                if(data.verified) {
                    $(modal).modal('hide');
                }
            }).fail(() => {
                Alert.show('Oeps, something went wrong, please try again.', { type: 'danger'});
            }).always(() => {
                Utils.hideLoading();
            });
        });
    }
}
