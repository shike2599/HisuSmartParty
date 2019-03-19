$(function () {
    var idxSwiper = new Swiper('.idxSwiper', {
        autoplay: 5000,//可选选项，自动滑动
        loop    : true,
        autoHeight: true,
        prevButton:'.swiper-button-prev',
        nextButton:'.swiper-button-next',
        pagination : '.swiper-pagination',
        paginationClickable :true,		//点切换
        autoplayDisableOnInteraction : false,    //操作后继续
    });
    if (sessionStorage.getItem('index') == 3) {
        $('.mui-bar a:nth-child(1) .mui-icon ').find("img").attr('src','images/homePage/home-1.png')
        $('.mui-bar a:nth-child(2) .mui-icon ').find("img").attr('src','images/homePage/book-1.png')
        $('.mui-bar a:nth-child(3) .mui-icon ').find("img").attr('src','images/homePage/dang-1.png')
        $('.mui-bar a:nth-child(4) .mui-icon ').find("img").attr('src','images/homePage/doit.png')
        $('.mui-bar a:nth-child(5) .mui-icon ').find("img").attr('src','images/homePage/prosen-1.png')
    }
    $(".studyPlan").on("click",function(){
        var practiceId = $(this).attr("id");
        console.log(practiceId);
        sessionStorage.setItem("practiceId",practiceId);
        window.location.href = "practice/practiceList.html";
    })

})