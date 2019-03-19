$(function(){
    // 党员信息查询
    //userId:localStorage.getItem("userId")
    $.get(gloablePath+"/party-app-building-front/member/readonly/getPartyMember?requestUser=hotel&requestPassword=123456",{userId: System.getUserInfoInt('user_id')},function(data){
        console.log(data);
        $(".perMess h4").html(data.data.name);
        $(".partyAdress span").html(data.data.nativePlace);
        if(data.data.status == 0){
            $(".perMess .status").html("在职党员");
        }else if(data.data.status == 1){
            $(".perMess .status").html("离退休党员");
        }else if(data.data.status == 2){
            $(".perMess .status").html("农民党员");
        }
    });

    $(".connect button").on("click",function(){
        //判断当前是否有正在进行的转接记录,暂无则跳转；
        $.get(gloablePath+"/party-app-building-front/member/readonly/hasNoCompleteApplay?requestUser=hotel&requestPassword=123456",{userId:System.getUserInfoInt("user_id"),partyMemberId:System.getUserInfoInt('member_id')},function(data){
            console.log(data);
            if(data.data == false){
                window.location.href = 'targetOrganazition.html'
            }else{
                $(".events").css("display","block");
                setTimeout(function delay(){
                    $(".events").css("display","none");
                    // history.go(0);
                },1000);
            }
        })
    });

    //转移记录查询
    $.ajax({
        type: 'get',
        url: gloablePath+'/party-app-building-front/member/readonly/listMemberOrgInAndOut?&requestUser=hotel&requestPassword=123456',
        dataType:"json",
        data: {userId: System.getUserInfoInt('user_id')},
        // data: {userId: localStorage.getItem('userId')},
        success: function(data){
            console.log(data);
            var html = template("list-template",data);
            $(".party>ul").html(html);
        }
    })
    if(navigator.userAgent.match( /(?:iPad|iPod|iPhone).*OS\s([\d_]+)/ )) {
        var timer = setInterval(function(){
            if(sessionStorage.getItem('isHistory') == 'true') {
                sessionStorage.setItem('isHistory', 'false');
                timer = null;
                location.reload();
            }
        },30);
    }
})