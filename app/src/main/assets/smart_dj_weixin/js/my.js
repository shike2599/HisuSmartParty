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
    if (sessionStorage.getItem('index') == 4) {
        $('.mui-bar a:nth-child(1) .mui-icon ').find("img").attr('src', 'images/homePage/home-1.png')
        $('.mui-bar a:nth-child(2) .mui-icon ').find("img").attr('src', 'images/homePage/book-1.png')
        $('.mui-bar a:nth-child(3) .mui-icon ').find("img").attr('src', 'images/homePage/dang-1.png')
        $('.mui-bar a:nth-child(4) .mui-icon ').find("img").attr('src', 'images/homePage/doit-1.png')
        $('.mui-bar a:nth-child(5) .mui-icon ').find("img").attr('src', 'images/homePage/porsen.png')

    }
})

//消息
$('.listCell_0').click(function(){
    window.location.href = 'mycenter/mymessage.html'
})
//积分
$('.listCell_1').click(function(){
    window.location.href = 'mycenter/myIntegral.html'
})
//审批
$('.listCell_2').click(function(){
    window.location.href = 'mycenter/myApproval.html'
})
//收藏
$('.listCell_3').click(function(){
    window.location.href = 'mycenter/myCollection.html'
})
//设置
$('.listCell_5').click(function(){
    window.location.href = 'mycenter/mysetting.html'
})