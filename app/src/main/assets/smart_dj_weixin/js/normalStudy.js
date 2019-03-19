$(function() {
    // 轮播图
    var idxSwiper = new Swiper('.swiperStudyImg', {
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
// 判断是党员还是支部的专题学习；
    if(localStorage.getItem('isPartyBranch')=='true' ){
        $.get(gloablePath+"/party-app-education-front/plan/readonly/listBranchTopicResPlan?requestUser=hotel&requestPassword=123456",{userId:localStorage.getItem("userId"),pageNo:1,pageSize:6},function(data){
            if(data.resultCode == 200){
                console.log(data);
                sessionStorage.setItem("currPage",data.currPage);
                sessionStorage.setItem("totalPage",data.totalPage);
                $(".subStudyList ul").html("");
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
                })
                // 点击跳转详情
                $(".studyItem").on("click",function(){
                    var subStudyDetailId = $(this).attr("id");
                    console.log(subStudyDetailId);
                    sessionStorage.setItem("subStudyDetailId",subStudyDetailId);
                    window.location.href="subStudyDetail.html";
                })
            }
        })
    }else{
        $.get(gloablePath+"/party-app-education-front/plan/readonly/listMemberTopicResPlan?requestUser=hotel&requestPassword=123456",{userId:localStorage.getItem("userId"),pageNo:1,pageSize:6},function(data){
            if(data.resultCode == 200){
                console.log(data);
                sessionStorage.setItem("currPage",data.currPage);
                sessionStorage.setItem("totalPage",data.totalPage);
                $(".subStudyList ul").html("");
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
                })
                // 点击跳转详情
                $(".studyItem").on("click",function(){
                    var subStudyDetailId = $(this).attr("id");
                    console.log(subStudyDetailId);
                    sessionStorage.setItem("subStudyDetailId",subStudyDetailId);
                    window.location.href="subStudyDetail.html";
                })
            }
        })
    }

    //上拉加载函数,ajax
    var num = 2;
    var page = 6; //每次加载6条
    function pullOnLoad() {
        if(localStorage.getItem('isPartyBranch')=='true' ){
            $.get(gloablePath+"/party-app-education-front/plan/readonly/listBranchTopicResPlan?requestUser=hotel&requestPassword=123456",{userId:localStorage.getItem("userId"),pageNo:num,pageSize:page},function(data){
                if(data.resultCode == 200){
                    console.log(data);
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
                        })
                        sessionStorage.setItem("currPage",data.currPage);
                        sessionStorage.setItem("totalPage",data.totalPage);
                        num+=1;
                    }else{
                        return false;
                    }

                    // 点击跳转详情
                    $(".studyItem").on("click",function(){
                        var subStudyDetailId = $(this).attr("id");
                        console.log(subStudyDetailId);
                        sessionStorage.setItem("subStudyDetailId",subStudyDetailId);
                        window.location.href="subStudyDetail.html";
                    })
                }
            })
        }else{
            $.get(gloablePath+"/party-app-education-front/plan/readonly/listMemberTopicResPlan?requestUser=hotel&requestPassword=123456",{userId:localStorage.getItem("userId"),pageNo:num,pageSize:page},function(data){
                if(data.resultCode == 200){
                    console.log(data);
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
                        })
                        sessionStorage.setItem("currPage",data.currPage);
                        sessionStorage.setItem("totalPage",data.totalPage);
                    }else{
                        return false;
                    }


                    // 点击跳转详情
                    $(".studyItem").on("click",function(){
                        var subStudyDetailId = $(this).attr("id");
                        console.log(subStudyDetailId);
                        sessionStorage.setItem("subStudyDetailId",subStudyDetailId);
                        window.location.href="subStudyDetail.html";
                    })
                }
            })
        }
    }


})