define(["require", "exports", "jquery", "datatables.net-responsive-bs4"], function (require, exports, $) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    var DataTable = (function () {
        function DataTable() {
            $('.dt-responsive').DataTable();
        }
        return DataTable;
    }());
    new DataTable();
});
