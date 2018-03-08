import {Alert} from 'app/alert';
import * as $ from 'jquery';

interface LinkedInData {
    id: string;
    title: string;
    description: string;
    submittedUrl: string;
    submittedImageUrl: string;
}

class LinkedIn {
    private _postId = null;

    constructor() {
        let button = document.querySelector('[data-share-linked-in="button"]');
        let modal = document.querySelector('#modal-share-linked-in');

        button.addEventListener('click', (e) => {
            $.post('/bounties/linkedin', {'post-id': this._postId}, () => {
                $(modal).modal('hide');
                Alert.show('Sharing is caring, thanks!');
            }).fail(() => {
                Alert.show('Oeps, something went wrong, please try again.', { type: 'danger'});
            });
        });

        $(modal).on('show.bs.modal', (e) => {
            let target: HTMLElement= (e.target as HTMLElement);
            let title: HTMLElement = target.querySelector('[data-share-linked-in="title"]');
            let message: HTMLElement = target.querySelector('[data-share-linked-in="text"]');
            let image: HTMLImageElement = target.querySelector('[data-share-linked-in="image"]');
            let url: HTMLAnchorElement = target.querySelector('[data-share-linked-in="url"]');

            $.get('/bounties/linkedin/random-post', (data: LinkedInData) => {
                this._postId = data.id;
                url ? url.href = data.submittedUrl : null;
                image ? image.src = data.submittedImageUrl  : null;
                title ? title.innerHTML = data.title  : null;
                message ? message.innerHTML = data.description  : null;
            });
        });
    }
}

new LinkedIn();