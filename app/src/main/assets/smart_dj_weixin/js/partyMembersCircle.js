$(function(){
    var newsSwiper = new Swiper('.circle', {
        slidesPerView: 4,
        paginationClickable: true,
        freeMode: true    //自动贴合
    });
    $(".circle .swiper-slide ").eq(0).find("a").addClass("active");
    $(".circle .swiper-slide a").click(function(){
        $('.circle .swiper-slide a').removeClass('active');
        $(this).addClass('active');
        var index = $(".circle .swiper-slide a").index(this);
        console.log(index);
        if(index==0){
            $('.listContentOne').css('display','block');
            $('.listContentTwo').css('display','none');
            $('.listContentThree').css('display','none');
            $('.listContentFour').css('display','none');
        }else if(index == 1){
            $('.listContentOne').css('display','none');
            $('.listContentTwo').css('display','block');
            $('.listContentThree').css('display','none');
            $('.listContentFour').css('display','none');
        }else if(index == 2){
            $('.listContentOne').css('display','none');
            $('.listContentTwo').css('display','none');
            $('.listContentThree').css('display','block');
            $('.listContentFour').css('display','none');
        }else if(index == 3){
            $('.listContentOne').css('display','none');
            $('.listContentTwo').css('display','none');
            $('.listContentThree').css('display','none');
            $('.listContentFour').css('display','block');
        }
    });
    //发布党员圈
    $('.cameraP').click(function(){
        System.toUpLoadActivity(7);
        //window.webkit.messageHandlers.toUpLoadActivity.postMessage(7);
    //
    })
   //头像获取
    var user_photo = System.getUserInfoStr('user_photo');
    $('.namePicture').find('img:nth-child(1)').attr('src',user_photo);



   //
    // $('.circle_container .button').click(function(){
    //     window.location.href = 'study/learningExperience.html'
    // })
    // //点击删除
    // $('.circle_bottom img').click(function (){
    //     console.log('bbbb')
    // })
    //评论点赞
    $('.ellipsis img').click(function(){
        console.log('aaa');
        if($(".starShow").css("display")=="none"){
             $(".starShow").css('display','block');
          }else{
             $(".starShow").css('display','none');
          }
    })
})