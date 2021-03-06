$(function(){
    // 轮播图
    var idxSwiper = new Swiper('.swiperImg', {
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

    $.get(gloablePath+"/party-app-practice-front/res/readonly/listActionContent?requestUser=hotel&requestPassword=123456",{cateCode:sessionStorage.getItem("practiceId"),pageNo:1,pageSize:5},function(data){
        console.log(data);
        if(data.resultCode == 200){
            $(".newsList").html("");
            sessionStorage.setItem("currPage",data.currPage);
            sessionStorage.setItem("totalPage",data.totalPage);
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
            });
            // 点击跳转详情
            $(".newsList .partyNew_contentVideo").on("click",function(){
                var praDetailId = $(this).attr("id");
                console.log(praDetailId);
                sessionStorage.setItem("praDetailId",praDetailId);
                window.location.href="practiceDetail.html";
            })
        }
    })

    //上拉加载函数,ajax
    var num = 2;
    var page = 5; //每次加载5条
    function pullOnLoad() {
        $.get(gloablePath+"/party-app-practice-front/res/readonly/listActionContent?requestUser=hotel&requestPassword=123456",{cateCode:sessionStorage.getItem("practiceId"),pageNo:num,pageSize:page},function(data){
            console.log(data);
            if(data.resultCode == 200){
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
                    });
                    sessionStorage.setItem("currPage",data.currPage);
                    sessionStorage.setItem("totalPage",data.totalPage);
                    num+=1;
                }else{
                    return false;
                }


                // 点击跳转详情
                $(".newsList .partyNew_contentVideo").on("click",function(){
                    var praDetailId = $(this).attr("id");
                    console.log(praDetailId);
                    sessionStorage.setItem("praDetailId",praDetailId);
                    window.location.href="practiceDetail.html";
                })
            }
        })
    }



    $(".public").on("click",function(){
        window.location.href = "../study/learningExperience.html";
    })
})