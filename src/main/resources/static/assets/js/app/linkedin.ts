import * as $ from 'jquery';

class LinkedIn {
    private _postId = null;
    private _message = null;

    constructor() {
        let self = this;

        $('[data-share-linked-in="button"]').on('click', (e) => {
            $.post('/bounties/linkedin', { 'post-id': this._postId }, {
                error: () => {
                    console.log('error sharing');
                },
                success: () => {
                    console.log('shared');
                }
            });
        });

        $('#modal-share-linked-in').on('show.bs.modal', (e) => {
            let message: HTMLElement = $('[data-share-linked-in="text"]', e.target)[0];

            $.get('/bounties/linkedin/random-post', (data) => {
                self._postId = data.id;
                message.innerHTML = data.description;
            });
        });
    }
}

new LinkedIn();