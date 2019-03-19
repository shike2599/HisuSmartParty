$(function () {
    console.log(getCookie('s'));

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
    if (sessionStorage.getItem('index') == 1) {
        $('.mui-bar a:nth-child(1) .mui-icon ').find("img").attr('src','images/homePage/home-1.png')
        $('.mui-bar a:nth-child(2) .mui-icon ').find("img").attr('src','images/homePage/study.png')
        $('.mui-bar a:nth-child(3) .mui-icon ').find("img").attr('src','images/homePage/dang-1.png')
        $('.mui-bar a:nth-child(4) .mui-icon ').find("img").attr('src','images/homePage/doit-1.png')
        $('.mui-bar a:nth-child(5) .mui-icon ').find("img").attr('src','images/homePage/prosen-1.png')
    }

    //判断用户如果是党员，在线考试显示（此处判断可要可不要，用户登录app本身就是党员）
    // if(localStorage.getItem('isPartyMember')=='true' ){
    if(getCookie('isPartyMember')=='true' ){
        // setCookie('isPartyMember', true, setCookieDate(7));
        $('.branchLearning').css('display','block');
        //点击党员按钮内容切换
        $('.onlineExaminations').css('display','block');
        $(".onlineExaminations").on("click",function(){
            window.location.href='study/studyExamination.html'
        })
    }
    //判断用户是不是支部党员，如果是，点击党员支部按钮进行切换，前提先让支部按钮显示
    // if(localStorage.getItem('isPartyBranch') == 'true'){
    if(getCookie('isPartyBranch') == 'true'){
        $('.branchLearning').css('display','block');
        //setCookie('isPartyBranch', true, setCookieDate(7));
        //点击支部按钮内容切换
        $('.branchLearning .branchLearningInfo').click(function(){
            setCookie('s', 1, setCookieDate(7));
            console.log('aaaa');
            $('.onlineExaminations').css('display','none');
            $('.branchLearning').css('display','none');
            $('.partyMemberLearning').css('display','block')
        })
        //党员
        $('.partyMemberLearning .partyMemberLearningInfo').click(function(){
            setCookie('s', 2, setCookieDate(7));
            $('.onlineExaminations').css('display','block');
            $('.branchLearning').css('display','block');
            $('.partyMemberLearning').css('display','none')
        })
    }
    //点击学习心得，进入心得界面
    $('.lineFour .studySum').click(function(){
        window.location.href='study/learningExperience.html'
    })
    // 专题学习跳转
    $(".thematicLearning").on("click",function(){
        window.location.href='subjectStudy/normalStudy.html'
    })
    // 常规学习跳转
    $(".regularLearning").on("click",function(){
        window.location.href='subjectStudy/studyList.html'
    })
    // 学习计划
    $(".studyPlan").on("click",function(){
        window.location.href='study/studyPlay.html'
    })
    //学习排名
    $(".studyRanking").on("click",function(){
        window.location.href='study/studyRanking.html'
    })
    // 三会一课跳转
    $(".threeSession").on("click",function(){
        window.location.href = "study/threeSession.html"
    })

    if(getCookie('s') == 1){
        // console.log(0);
        $('.onlineExaminations').css('display','none');
        $('.branchLearning').css('display','none');
        $('.partyMemberLearning').css('display','block')
        // console.log(2)
    }
})



