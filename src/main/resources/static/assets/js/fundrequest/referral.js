define(["jquery"], function($) {
    $(function() {
        $.get("/referrals", function(data) {
            $("#rewards-list-content").html(data);
        });
    });
});