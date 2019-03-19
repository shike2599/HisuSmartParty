$(function() {
    // 学习分类

    // 轮播图
    var idxSwiper = new Swiper('.swiperNormalStudyImg', {
        autoplay: 5000,//可选选项，自动滑动
        loop: true,
        autoHeight: true,
        prevButton: '.swiper-button-prev',
        nextButton: '.swiper-button-next',
        pagination: '.swiper-pagination',
        paginationClickable: true,		//点切换
        autoplayDisableOnInteraction: false  //操作后继续
    });
    sessionStorage.removeItem("currPage");
    sessionStorage.removeItem("totalPage");
    var myscroll = new iScroll("wrapper", {
        onScrollMove: function () { //拉动时
            //上拉加载
            if (this.y < this.maxScrollY) {
                $(".pull-loading").html("释放加载");
                $(".pull-loading").addClass("loading");
            } else {
                $(".pull-loading").html("上拉加载");
                $(".pull-loading").removeClass("loading");
            }
        },
        onScrollEnd: function () { //拉动结束时
            //上拉加载
            if ($(".pull-loading").hasClass('loading')) {
                $(".pull-loading").html("加载中...");
                if(sessionStorage.getItem("currPage") == sessionStorage.getItem("totalPage")){
                    console.log(sessionStorage.getItem("currPage"));
                    console.log(sessionStorage.getItem("totalPage"));
                    $(".pull-loading").html("加载完成");
                }else{
                    pullOnLoad();
                }

            }
        }
    });
    $.get(gloablePath + "/party-app-education-front/res/readonly/listCommonCate?&requestUser=hotel&requestPassword=123456", function (data) {
        console.log(data);
        $.each(data.dataList, function (index, el) {
            $(".studyTit .swiper-wrapper").append(`
                    <div class="swiper-slide ">
                      <a href="javaScript:;" name="${el.code}">${el.name}</a>
                    </div>
             `)
        });

        var newsSwiper = new Swiper('.studyTit', {
            slidesPerView: 4,
            paginationClickable: true,
            freeMode: true    //自动贴合
        });
        $(".studyTit .swiper-slide ").eq(0).find("a").addClass("active");
        if(sessionStorage.getItem("studyTitCateId")){
            var studyTitCateId = sessionStorage.getItem("studyTitCateId");
            getStudyCate(studyTitCateId);
            $(".studyTit .swiper-slide a").removeClass("active");
            $(".studyTit .swiper-slide ").eq(sessionStorage.getItem("clickStudytCateIndex")).find("a").addClass("active");
        }else{
            getStudyCate(3003);
            sessionStorage.setItem("studyTitCateId",3003);
        }
        $(".studyTit .swiper-slide a").on("click",function(){
            $(".pull-loading").html("");
            var clickStudytCateIndex = $(this).parents(".swiper-slide").index();
            sessionStorage.setItem("clickStudytCateIndex",clickStudytCateIndex);
            console.log(clickStudytCateIndex);
            $(".studyTit .swiper-slide a").removeClass("active");
            $(this).addClass("active");
            var studyTitCateId = $(this).attr("name");
            sessionStorage.setItem("studyTitCateId",studyTitCateId);
            console.log(studyTitCateId);
            getStudyCate(studyTitCateId)
        });
    })

    function getStudyCate(studyTitCateId){
        $.get(gloablePath+"/party-app-education-front/res/readonly/listCommonContent?requestUser=hotel&requestPassword=123456",{cateCode:studyTitCateId,pageNo:1,pageSize:5},function(data){
            if(data.resultCode == 200){
                console.log(data);
                sessionStorage.setItem("currPage",data.currPage);
                sessionStorage.setItem("totalPage",data.totalPage);
                $(".subStudyList ul").html("");
                if(data.dataList.length == 0){
                    $(".pull-loading").css("display","none");
                    $(".subStudyList ul").append(`
                        <li style="margin-top: 50px;text-align: center;width:100%">暂无数据......</li>
                    `)
                }
                $.each(data.dataList,function(index,el){
                    var newsTime = el.publishTime.split(' ')[0];
                    if(el.mediaType == 0){
                        $(".subStudyList ul").append(`
                                <li class="studyItem" id="${el.id}">
                                    <div class="studyImg">
                                        <img src="${data.outServer}${el.icon}" alt="">
                                        <span><img src="../images/homePage/player.png" alt=""></span>
                                    </div>
                                    <p class="studyName">${el.name}</p>
                                    <div class="detail">
                                        <p class="pubTime">${newsTime}</p>
                                        <p class="addCollect"><span><img src="../images/homePage/view.png" alt=""></span>&nbsp;2300次 </p>
                                    </div>
                                </li>
                           `)
                    }else if(el.mediaType == 1){
                        $(".subStudyList ul").append(`
                                <li class="studyItem" id="${el.id}">
                                    <div class="studyImg">
                                        <img src="${data.outServer}${el.icon}" alt="">
                                    </div>
                                    <p class="studyName">${el.name}</p>
                                    <div class="detail">
                                        <p class="pubTime">${newsTime}</p>
                                        <p class="addCollect"><span><img src="../images/homePage/view.png" alt=""></span>&nbsp;2300次 </p>
                                    </div>
                                </li>
                           `)
                    }
                });
                // 点击跳转详情
                $(".studyItem").on("click",function(){
                    var studyDetailId = $(this).attr("id");
                    console.log(studyDetailId);
                    sessionStorage.setItem("studyDetailId",studyDetailId);
                    window.location.href="studyDetail.html";
                })
            }
        })
    }

    //上拉加载函数,ajax
    var num = 2;
    var page = 5; //每次加载5条
    function pullOnLoad() {
        $.get(gloablePath+"/party-app-education-front/res/readonly/listCommonContent?requestUser=hotel&requestPassword=123456",{cateCode:sessionStorage.getItem("studyTitCateId"),pageNo:num,pageSize:page},function(data){
            if(data.resultCode == 200){
                console.log(data);
                console.log(data.totalPage);
                console.log(data.currPage);
                if( data.currPage < data.totalPage || data.currPage == data.totalPage ){
                    $.each(data.dataList,function(index,el){
                        var newsTime = el.publishTime.split(' ')[0];
                        if(el.mediaType == 0){
                            $(".subStudyList ul").append(`
                                <li class="studyItem" id="${el.id}">
                                    <div class="studyImg">
                                        <img src="${data.outServer}${el.icon}" alt="">
                                        <span><img src="../images/homePage/player.png" alt=""></span>
                                    </div>
                                    <p class="studyName">${el.name}</p>
                                    <div class="detail">
                                        <p class="pubTime">${newsTime}</p>
                                        <p class="addCollect"><span><img src="../images/homePage/view.png" alt=""></span>&nbsp;2300次 </p>
                                    </div>
                                </li>
                           `)
                        }else if(el.mediaType == 1){
                            $(".subStudyList ul").append(`
                                <li class="studyItem" id="${el.id}">
                                    <div class="studyImg">
                                        <img src="${data.outServer}${el.icon}" alt="">
                                    </div>
                                    <p class="studyName">${el.name}</p>
                                    <div class="detail">
                                        <p class="pubTime">${newsTime}</p>
                                        <p class="addCollect"><span><img src="../images/homePage/view.png" alt=""></span>&nbsp;2300次 </p>
                                    </div>
                                </li>
                           `)
                        }
                    });
                    sessionStorage.setItem("currPage",data.currPage);
                    sessionStorage.setItem("totalPage",data.totalPage);
                    num+=1;
                }else{
                    return false;
                }
                // 点击跳转详情
                $(".studyItem").on("click",function(){
                    var studyDetailId = $(this).attr("id");
                    console.log(studyDetailId);
                    sessionStorage.setItem("studyDetailId",studyDetailId);
                    window.location.href="studyDetail.html";
                })
            }
        })
    }

})