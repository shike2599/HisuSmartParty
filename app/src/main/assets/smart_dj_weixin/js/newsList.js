$(function(){
    // 轮播图
    var idxSwiper = new Swiper('.swiperImg', {
        autoplay: 5000,//可选选项，自动滑动
        loop    : true,
        autoHeight: true,
        prevButton:'.swiper-button-prev',
        nextButton:'.swiper-button-next',
        pagination : '.swiper-pagination',
        paginationClickable :true,		//点切换
        autoplayDisableOnInteraction : false  //操作后继续
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

    $.get(gloablePath+"/party-app-education-front/res/readonly/listInfoCate?requestUser=hotel&requestPassword=123456",function(data){
        console.log(data);
        $.each(data.dataList,function(index,el){
            $(".newsTit .swiper-wrapper").append(`
                    <div class="swiper-slide ">
                      <a href="javaScript:;" id="${el.code}">${el.name}</a>
                    </div>
             `)
        });

        var newsSwiper = new Swiper('.newsTit', {
            slidesPerView: 4,
            paginationClickable: true,
            freeMode: true    //自动贴合
        });
        $(".newsTit .swiper-slide ").eq(0).find("a").addClass("active");
        if(sessionStorage.getItem("newsTitCateId")){
            var newsTitCateIds = sessionStorage.getItem("newsTitCateId");
            getNewsCate(newsTitCateIds);
            $(".newsTit .swiper-slide a").removeClass("active");
            $(".newsTit .swiper-slide ").eq(sessionStorage.getItem("clicknewsTitCateIndex")).find("a").addClass("active");
        }else{
            getNewsCate(1001);
            sessionStorage.setItem("newsTitCateId",1001);
        }
        $(".newsTit .swiper-slide a").on("click",function(){
            $(".pull-loading").html("");
            var clicknewsTitCateIndex = $(this).parents(".swiper-slide").index();
            sessionStorage.setItem("clicknewsTitCateIndex",clicknewsTitCateIndex);
            console.log(clicknewsTitCateIndex);
            $(".newsTit .swiper-slide a").removeClass("active");
            $(this).addClass("active");
            var newsTitCateId = $(this).attr("id");
            sessionStorage.setItem("newsTitCateId",newsTitCateId);
            console.log(newsTitCateId);
            getNewsCate(newsTitCateId)
        });
    });


    function getNewsCate(newsTitCateId){
        $.get(gloablePath+"/party-app-education-front/res/readonly/listInformation?requestUser=hotel&requestPassword=123456",{cateCode:newsTitCateId,pageNo:1,pageSize:5},function(data){
            if(data.resultCode == 200){
                console.log(data);
                sessionStorage.setItem("currPage",data.currPage);
                sessionStorage.setItem("totalPage",data.totalPage);
                $(".newsList").html("");
                if(data.dataList.length == 0){
                    $(".pull-loading").css("display","none");
                    $(".newsList").append(`
                            <p style="text-align: center;margin-top: 100px">暂无数据......</p>
                        `)
                }
                $.each(data.dataList,function(index,el){
                    var newsTime = el.publishTime.split(' ')[0];
                    if(el.mediaType == 0){
                        $(".newsList").append(`
                                <div class="partyNew_contentVideo" id="${el.id}">
                                    <div class="partyNewLeft">
                                        <p>${el.name}</p>
                                        <h3>
                                            <i class="date">${newsTime}</i>
                                            <span class="browseTimes">
                                            <img src="../images/homePage/view.png">
                                          2034次
                                      </span>
                                        </h3>
                                    </div>
                                    <div class="partyNewRight">
                                        <img src="${data.outServer}/${el.icon}">
                                        <span><img src="../images/homePage/player.png"></span>
                                    </div>
                                </div> 
                           `)
                    }else if(el.mediaType == 1){
                        $(".newsList").append(`
                                <div class="partyNew_contentVideo" id="${el.id}">
                                    <div class="partyNewLeft">
                                        <p>${el.name}</p>
                                        <h3>
                                            <i class="date">${newsTime}</i>
                                            <span class="browseTimes">
                                            <img src="../images/homePage/view.png">
                                          2034次
                                      </span>
                                        </h3>
                                    </div>
                                    <div class="partyNewRight">
                                        <img src="${data.outServer}/${el.icon}">
                                      
                                    </div>
                                </div> 
                           `)
                    }
                })
                // 点击跳转详情
                $(".newsList .partyNew_contentVideo").on("click",function(){
                    var newsDetailId = $(this).attr("id");
                    console.log(newsDetailId);
                    sessionStorage.setItem("newsDetailId",newsDetailId);
                    window.location.href="newsDetail.html";
                })
            }
        })
    }

    //上拉加载函数,ajax
    var num = 2;
    var page = 5; //每次加载5条
    function pullOnLoad() {
            $.get(gloablePath+"/party-app-education-front/res/readonly/listInformation?requestUser=hotel&requestPassword=123456",{cateCode:sessionStorage.getItem("newsTitCateId"),pageNo:num,pageSize:page},function(data){
                if(data.resultCode == 200){
                    console.log(data);
                    if( data.currPage < data.totalPage || data.currPage == data.totalPage ){
                        $.each(data.dataList,function(index,el){
                            var newsTime = el.publishTime.split(' ')[0];
                            if(el.mediaType == 0){
                                $(".newsList").append(`
                                <div class="partyNew_contentVideo" id="${el.id}">
                                    <div class="partyNewLeft">
                                        <p>${el.name}</p>
                                        <h3>
                                            <i class="date">${newsTime}</i>
                                            <span class="browseTimes">
                                            <img src="../images/homePage/view.png">
                                          2034次
                                      </span>
                                        </h3>
                                    </div>
                                    <div class="partyNewRight">
                                        <img src="${data.outServer}/${el.icon}">
                                        <span><img src="../images/homePage/player.png"></span>
                                    </div>
                                </div> 
                           `)
                            }else if(el.mediaType == 1){
                                $(".newsList").append(`
                                <div class="partyNew_contentVideo" id="${el.id}">
                                    <div class="partyNewLeft">
                                        <p>${el.name}</p>
                                        <h3>
                                            <i class="date">${newsTime}</i>
                                            <span class="browseTimes">
                                            <img src="../images/homePage/view.png">
                                          2034次
                                      </span>
                                        </h3>
                                    </div>
                                    <div class="partyNewRight">
                                        <img src="${data.outServer}/${el.icon}">
                                      
                                    </div>
                                </div> 
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
                    $(".newsList .partyNew_contentVideo").on("click",function(){
                        var newsDetailId = $(this).attr("id");
                        console.log(newsDetailId);
                        sessionStorage.setItem("newsDetailId",newsDetailId);
                        window.location.href="newsDetail.html";
                    })
                }
            })
        }

});