(function(){
    var navigation = document.querySelector(".headroom");
    var headroom = new Headroom(navigation, {
        tolerance: 10,
        classes: {
            pinned: "slideDown",
            unpinned: "slideUp"
        }
    });
    headroom.init();
})();