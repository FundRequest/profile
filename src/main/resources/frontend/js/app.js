require.config({
    baseUrl: '/assets/js',
    paths: {
        'jquery': ['//ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min', 'libs/jquery.min'],
        'bootstrap.bundle': ['libs/bootstrap.bundle'],
        'waves': ['libs/waves.min'],
        'mdb': ['libs/mdb.min'],
        'clipboard': ['//cdnjs.cloudflare.com/ajax/libs/clipboard.js/2.0.0/clipboard.min', 'libs/clipboard.min'],
        'datatables': ['//cdn.datatables.net/1.10.16/js/jquery.dataTables.min', 'libs/jquery.dataTables.min'],
        'datatables.net': ['//cdn.datatables.net/1.10.16/js/jquery.dataTables.min', 'libs/jquery.dataTables.min'],
        'datatables.net-bs4': ['//cdn.datatables.net/1.10.16/js/dataTables.bootstrap4.min', 'libs/dataTables.bootstrap4.min'],
        'datatables.net-responsive': ['//cdn.datatables.net/responsive/2.2.1/js/dataTables.responsive.min'],
        'datatables.net-responsive-bs4': ['//cdn.datatables.net/responsive/2.2.1/js/responsive.bootstrap4.min'],
    }
});

require(['jquery', 'bootstrap.bundle', 'waves'], function() {
    //window.Popper = Popper; // re-attach to global scope
    require(['app/main'], function() {
    });
});
