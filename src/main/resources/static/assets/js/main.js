(function() {
    var fnd = {};
    // headroom
    fnd.headroom = new Headroom(document.querySelector(".headroom"), {
        tolerance: 10,
        classes: {
            pinned: "slideDown",
            unpinned: "slideUp"
        }
    });
    fnd.headroom.init();

    // clipboard
    fnd.clipboard = new Clipboard('[data-clipboard-target]');
    fnd.clipboard.on('success', function(e) {
        fnd.alert.show('Copied to your clipboard! ');
        e.clearSelection();
    });
    fnd.clipboard.on('error', function(e) {
        fnd.alert.show('This browser doesn\'t allow copying to your clipboard, please do it manually');
    });

    // alert
    fnd.alert = (function() {
        var _options = {
            type: 'success',
            timeout: 3000
        };
        var $container = $('#alert-container');
        var alert = '<div class="alert alert-dismissible fade"><span class="alert-content"></span><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button></div>';

        var show = function(html, options) {
            var myOptions = typeof options !== 'undefined' ? $.merge(_options, options) : _options;
            var $element = $(alert);
            $element.find('.alert-content').html(html);
            $element.addClass('alert-' + myOptions.type);

            while ($container.children().length > 3) {
                $container.children().last().remove();
            }

            $container.prepend($element);
            setTimeout(function() {
                $element.addClass('show');
            }, 100);

            setTimeout(function() {
                $element.alert('close');
            }, myOptions.timeout);
        };

        return {
            show: show
        }
    })();

    window.fnd = fnd;
})();

// init stuff
$(function() {
    $('.fnd-badge[data-toggle="tooltip"]').tooltip();
});