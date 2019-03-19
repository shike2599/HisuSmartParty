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
    if (sessionStorage.getItem('index') == 2) {
        $('.mui-bar a:nth-child(1) .mui-icon ').find("img").attr('src','images/homePage/home-1.png')
        $('.mui-bar a:nth-child(2) .mui-icon ').find("img").attr('src','images/homePage/book-1.png')
        $('.mui-bar a:nth-child(3) .mui-icon ').find("img").attr('src','images/homePage/dang.png')
        $('.mui-bar a:nth-child(4) .mui-icon ').find("img").attr('src','images/homePage/doit-1.png')
        $('.mui-bar a:nth-child(5) .mui-icon ').find("img").attr('src','images/homePage/prosen-1.png')
    }
    // 党员缴费
    $(".regularLearning").on("click",function(){
        window.location.href = 'partyBuild/payCost.html'
    })
    //点击进入党组织关系
    $('.lineOne .thematicLearning').click(function(){
        console.log('ok')
        window.location.href = 'partyBuild/relation.html'
    })
    //点击进入党组织架构页面
    $('.lineThree .onlineExaminations').click(function(){
        window.location.href = 'partyBuild/partyStructure.html'
    })


    // 党务公开、支部活动
    $(".videoLectureHall,.studyRanking").on("click",function(){
        var moreId = $(this).attr("name");
        console.log(moreId);
        sessionStorage.setItem("moreId",moreId);
        window.location.href = "news/newsMore.html";
    });
    //支部简介
    $(".threeSession").on("click",function(){
        window.location.href = "partyBuild/partyBranChIntro.html";
    })
    //党委简介
    $(".studyPlan").on("click",function(){
        window.location.href = "partyBuild/partyCommitContro.html";
    })

})
//党委简介
$(".studySum").on("click",function(){
    window.location.href = "partyBuild/bigData.html";
})