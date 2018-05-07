var Referrals = (function () {
    function Referrals() {
        var rewardsContainer = document.querySelector('#rewards-list-content');
        if (rewardsContainer != null) {
            this._getReferrals()
                .then(function (html) {
                rewardsContainer.innerHTML = html;
            })
                .catch(function (ex) {
                console.log('Something when wrong during getting referrals', ex);
            });
        }
    }
    Referrals.prototype._getReferrals = function () {
        return $.get('/referrals').promise();
    };
    return Referrals;
}());
new Referrals();
