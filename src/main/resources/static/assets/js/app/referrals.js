define(["require", "exports", "jquery"], function (require, exports, $) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    var Referrals = /** @class */ (function () {
        function Referrals() {
            $.get("/referrals", function (data) {
                $("#rewards-list-content").html(data);
            });
        }
        return Referrals;
    }());
    new Referrals();
});
