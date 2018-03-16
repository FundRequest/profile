import * as $ from 'jquery';

class Referrals {
    constructor() {
        $.get("/referrals", function(data) {
            $("#rewards-list-content").html(data);
        });
    }
}

new Referrals();
