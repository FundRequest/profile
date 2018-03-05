(function () {
  $(document).ready(function () {
    $.get("/referrals", function (data) {
      $("#rewards-list-content").html(data);
    });

    $.get("/referrals/total", function (data) {
      $("#referral-total").html(data);
    });
  });
})();