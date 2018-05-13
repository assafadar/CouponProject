$( document ).ready(function() {

    /*======banner_slider=======*/
    $('#banner_slider').owlCarousel({
        items:4,
        loop:true,
        margin:10,
        nav:false,
        autoplay:true,
        autoplayTimeout:5000,
        smartSpeed: 900, 
        autoplayHoverPause:true,
        responsive:{
        0:{
            items:1,
            dots: false
        },
        600:{
            items:3
        },
        1000:{
            items:4
        }
        }
    });
    /*==========logo_slider============*/
    $('#logo_slider').owlCarousel({
        items:4,
        loop:true,
        margin:0,
        nav:true,
        dots:false,
        autoplay:true,
        autoplayTimeout:5000,
        autoplayHoverPause:true,
        smartSpeed: 900,
        responsive:{
        0:{
            items:1
        },
        600:{
            items:3
        },
        1000:{
            items:4
        }
        },
        navText: ["<i class='fa fa-long-arrow-left'></i>","<i class='fa fa-long-arrow-right'></i>"]
       
    });
    /*=======bitcoinwallet-slider===========*/
    $('#bitcoinwallet-slider').owlCarousel({
        items:1,
        loop:true,
        margin:0,
        nav:false,
        dots:true,
        autoplay:true,
        autoplayTimeout:3000,
        autoplayHoverPause:true,
        smartSpeed: 900       
       
    });
    /*=======testimonials_slider===========*/
    $('#testimonials_slider').owlCarousel({
        items:1,
        loop:true,
        margin:0,
        nav:false,
        dots:true,
        autoplay:true,
        autoplayTimeout:5000,
        autoplayHoverPause:true,
        smartSpeed: 900       
       
    });
});
