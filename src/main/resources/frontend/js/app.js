require.config({
    baseUrl: '/assets/js',
    paths: {
        'jquery': ['//ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min', 'libs/jquery.min'],
        'popper': ['https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.2/umd/popper', 'libs/popper.min'],
        'bootstrap': ['libs/bmd'],
        'clipboard': ['//cdnjs.cloudflare.com/ajax/libs/clipboard.js/2.0.0/clipboard.min', 'libs/clipboard.min'],
        'datatables': ['//cdn.datatables.net/1.10.16/js/jquery.dataTables.min', 'libs/jquery.dataTables.min'],
        'datatables.net': ['//cdn.datatables.net/1.10.16/js/jquery.dataTables.min', 'libs/jquery.dataTables.min'],
        'datatables.net-bs4': ['//cdn.datatables.net/1.10.16/js/dataTables.bootstrap4.min', 'libs/dataTables.bootstrap4.min'],
        'datatables.net-responsive': ['//cdn.datatables.net/responsive/2.2.1/js/dataTables.responsive.min'],
        'datatables.net-responsive-bs4': ['//cdn.datatables.net/responsive/2.2.1/js/responsive.bootstrap4.min'],
    },
    shim: {
        'popper': {
            'deps': ['jquery'],
            'exports': 'popper'
        },
        'bootstrap': {
            'deps': ['jquery', 'popper']
        }
    }
});

require(['jquery', 'popper'], function($, Popper) {
    window.Popper = Popper; // re-attach to global scope
    require(['app/main'], function() {
        require(["app/linkedin"]);
        require(["app/twitter"]);
        require(["app/referrals"]);
    });
});
