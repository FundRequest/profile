import {Alert} from 'app/alert';
import * as $ from 'jquery';

class LinkedIn {
    private _postId = null;

    constructor() {
        let $button = $('[data-share-linked-in="button"]');
        let $modal = $('#modal-share-linked-in');

        $button.on('click', (e) => {
            $.post('/bounties/linkedin', {'post-id': this._postId}, () => {
                $modal.modal('hide');
                Alert.show('Sharing is caring, thanks!');
            }).fail(() => {
                Alert.show('Oeps, something went wrong, please try again.', { type: 'danger'});
            });
        });

        $modal.on('show.bs.modal', (e) => {
            let message: HTMLElement = $('[data-share-linked-in="text"]', e.target)[0];

            $.get('/bounties/linkedin/random-post', (data) => {
                this._postId = data.id;
                message.innerHTML = data.description;
            });
        });
    }
}

new LinkedIn();