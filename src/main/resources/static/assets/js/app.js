require.config({
    baseUrl: 'assets/js',
    paths: {

        'jquery': ['//ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min', 'libs/jquery.min'],
        'popper': 'https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.2/umd/popper',
        'bootstrap': 'libs/bootstrap-material-design',
        'clipboard': ['//cdnjs.cloudflare.com/ajax/libs/clipboard.js/2.0.0/clipboard.min', 'libs/clipboard.min']
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
    });
});