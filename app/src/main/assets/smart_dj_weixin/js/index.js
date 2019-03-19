$(function(){
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
    //消息通知
    $.ajax({
        type:"get",
        url:gloablePath+'/party-app-education-front/info/readonly/listNotice?partyBranchId=14&pageNo=1&pageSize=10&requestUser=hotel&requestPassword=123456',
        dataType:"json",
        success:function(data){
            //console.log(data);
            var data =data.dataList[0].content;
            console.log(data);
            $(".messageNotification>.messageNotification_content>.news").append('<marquee direction="left" behavior="scroll" scrollamount="4">'+data+'</marquee>')
        }
    });
    //党建要闻
    $.ajax({
        type: 'get',
        url: gloablePath + '/party-app-education-front/res/readonly/listInformation?&pageNo=1&pageSize=2&&requestUser=hotel&requestPassword=123456',
        dataType:"json",
        data: {cateCode: 1003},
        success: function(data){
           console.log(data);
            $.each(data.dataList,function (index,val) {
                if(data.dataList[index].icon != ''){
                    $(".partyNew>.partyNew_content").append('<div class="partyNew_content" name="'+val.id+'"><div class="partyNewLeft"><p >'+data.dataList[index].name+'</p><h3><i class="date">'+data.dataList[index].publishTime.split(' ')[0]+'</i><span class="browseTimes"><img src="images/homePage/view.png">2034次</span></h3></div><div class="partyNewRight"><img src="'+data.outServer+data.dataList[index].icon+'"></div></div>')
                }
                else if(data.dataList[index].icon == ''){
                    $(".partyNew").append('<div class="partyNew_content" name="'+val.id+'"><div class="partyNewLeft"><p>'+data.dataList[index].name+'</p><h3><i class="date">'+data.dataList[index].publishTime.split(' ')[0]+'</i><span class="browseTimes"><img src="images/homePage/view.png">2034次</span></h3></div><div class="partyNewRight"><img src="images/homePage/xidada.png"></div></div>')
                }
            })
            $(".partyNew_content").on("click",function(){
                var newsDetailId = $(this).attr("name");
                console.log(newsDetailId);
                sessionStorage.setItem("newsDetailId",newsDetailId);
               window.location.href = "news/newsDetail-1.html";
            })
        }
    })
    //时政要闻
    $.ajax({
        type: 'get',
        url: gloablePath + '/party-app-education-front/res/readonly/listInformation?&pageNo=1&pageSize=2&&requestUser=hotel&requestPassword=123456',
        dataType:"json",
        data: {cateCode: 1001},
        success: function(data){
            console.log(data);
            $.each(data.dataList,function (index,val) {
                if(data.dataList[index].mediaType == 0){
                    $(".currentNew").append('<div class="currentNew_contentVideo" name="'+val.id+'"><div class="currentNewLeft"><p>'+data.dataList[index].name+'</p><h3><i class="date">'+data.dataList[index].publishTime.split(' ')[0]+'</i><span class="browseTimes"><img src="images/homePage/view.png">2034次</span></h3></div><div class="currentNewRight"><img src="'+data.outServer+data.dataList[index].icon+'"><span><img src="images/homePage/player.png"></span></div></div>')
                }
                // else if(data.dataList[index].icon == ''){
                //     $(".partyNew>.partyNew_content").append('<div class="partyNewLeft"><p>'+data.dataList[index].name+'</p><h3><i class="date">'+data.dataList[index].publishTime.split(' ')[0]+'</i><span class="browseTimes"><img src="images/homePage/view.png">2034次</span></h3></div><div class="partyNewRight"><img src="images/homePage/xidada.png"></div>')
                // }

            })
            $(".currentNew_contentVideo").on("click",function(){
                var newsDetailId = $(this).attr("name");
                console.log(newsDetailId);
                sessionStorage.setItem("newsDetailId",newsDetailId);
                window.location.href = "news/newsDetail-1.html";
            })
        }
    })

    $(".partyBuilding").on("click",function(){
        window.location.href = "news/newsList.html";
    });
    $(".whole").on("click",function(){
        var moreId = $(this).attr("id");
        sessionStorage.setItem("moreId",moreId);
        window.location.href = "news/newsMore.html";

    });
    //点击进去党员圈
    $('.partyCircle').click(function(){
        window.location.href = "partyMembersCircle-1.html";
    });
    // 党费缴纳
    $(".partyPayment").on("click",function(){
        window.location.href = "partyBuild/payCost.html";
    });
    // 三会一课
    $(".threeSessions").on("click",function(){
        window.location.href = "study/threeSession.html";
    });
    // 组织关系
    $(".organizationalRelationship").on("click",function(){
        window.location.href = "partyBuild/relation.html";
    });
    $(".onlineExamination").on("click",function(){
        window.location.href = "study/studyExamination.html";
    })
    // 基层动态和时代先锋// 支部活动
    $(".grassrootsDynamics,.eraPioneer,.branchActivities").on("click",function(){
        var avtiveId = $(this).attr("ref");
        console.log(avtiveId);
        sessionStorage.setItem("moreId",avtiveId);
        window.location.href = "news/newsMore.html"
    })
})


