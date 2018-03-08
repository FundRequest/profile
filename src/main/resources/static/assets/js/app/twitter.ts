import {Alert} from './alert';
import {Utils} from "./utils";
import * as $ from 'jquery';

interface VerifyResponse {
    verified: boolean;
    message: string;
}

class Twitter {
    constructor() {
        let modal: HTMLElement = document.querySelector('#modal-twitter-verify');
        let button: HTMLElement = document.querySelector('[data-twitter-verify]');
        let buttonTweet: HTMLElement = document.querySelector('[data-twitter-tweet]');

        button.addEventListener('click', (e) => {
            this._verify(() => { $(modal).modal('hide'); })
        });

        buttonTweet.addEventListener('click', (e) => {
            let text: string = buttonTweet.dataset.twitterTweet;
            let link: string = `http://twitter.com/home?status=${encodeURIComponent(text)}`;

            let twitterWindow: Window = Utils.getNewWindow(link, 600, 600);

            /*
            let winTimer = window.setInterval(function () {
                if (twitterWindow.closed !== false) {
                    window.clearInterval(winTimer);
                    this._verify();
                }
            }, 200);*/
        });
    }

    private _verify(callback = null) {
        Utils.showLoading();
        $.get('/bounties/twitter/verify', (data: VerifyResponse) => {
            Alert.show(data.message, data.verified ? 'success' : 'danger');
            if (data.verified) {
                callback != null ? callback() : null;
            }
        }).fail(() => {
            Alert.show('Oeps, something went wrong, please try again.', 'danger');
        }).always(() => {
            Utils.hideLoading();
        });
    }
}

new Twitter();
