$(function(){
    var userName,yearTime;
    var payType = 0;
    // 党员信息查询
    $.get(gloablePath+"/party-app-building-front/member/readonly/getPartyMember?requestUser=hotel&requestPassword=123456",{userId:System.getUserInfoInt("user_id")},function(data){
        console.log(data);
        $(".perMess h4").html(data.data.name);
        userName = data.data.name;
        if(data.data.status == 0){
            $(".partyAge .status").html("在职党员");
        }else if(data.data.status == 1){
            $(".partyAge .status").html("离退休党员");
        }else if(data.data.status == 2){
            $(".partyAge .status").html("农民党员");
        }
    });
    // 缴费类型切换
    $(".cate span").on("click",function(){
        payType = $(this).index();
        console.log(payType);
        $(".cate span").removeClass("active");
        $(this).addClass("active");
        if(payType == 1){
            $(".chooseDate").css("display","none");
        }else{
            $(".chooseDate").css("display","block");
        }
    });

    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth()+1;
    $(".year .fullYear").html(year);
    console.log(month);
    // console.log(typeof (year))
    $(".left").on('click',function(){
        var currentYear =parseInt($(".year .fullYear").html());
        // console.log(currentYear);
        $(".year .fullYear").html(currentYear-1);
        yearTime =parseInt($(".fullYear").html());
        console.log(yearTime);
        $(".date ul li p").html("");
        PartyMemberFeeOrder(yearTime);
    });
    $(".right").on('click',function(){
        var currentYear =parseInt($(".year .fullYear").html());
        // console.log(currentYear);
        $(".year .fullYear").html(currentYear+1);
        yearTime =parseInt($(".fullYear").html());
        console.log(yearTime);
        $(".date ul li p").html("");
        PartyMemberFeeOrder(yearTime);
    });
    $(".date li").eq(month-1).addClass("active");
    $(".date li").on("click",function(){
        $(".date li").removeClass("active");
        $(this).addClass("active")
        // $(this).toggleClass("active");
    });
    yearTime =parseInt($(".fullYear").html());
    PartyMemberFeeOrder(yearTime);
    console.log(yearTime);
    function PartyMemberFeeOrder(yearTime){
        // 请求当前党员全年的缴费情况
        $.get(gloablePath+"/party-app-building-front/order/readonly/listPartyMemberFeeOrder?requestUser=hotel&requestPassword=123456",{partyMemberId:System.getUserInfoInt("member_id"),year:yearTime},function(data){
            console.log(data);
            if(data.resultCode == 200){
                $.each(data.dataList,function(index,el){
                    if(el.startMonth == el.endMonth){
                        if(el.payStatus == 1 || el.payStatus == 2){
                            $(".date ul li").eq(el.endMonth-1).find("p").html("已缴费");
                            $(".date ul li").eq(el.endMonth-1).find("p").css("color","#00c2a5")
                        }else{
                            $(".date ul li").eq(el.endMonth-1).find("p").html("未缴费");
                            $(".date ul li").eq(el.endMonth-1).find("p").css("color","#ffc000");
                        }
                    }
                })
            }
        })
    }
    $(".payPartyMoney button").on("click",function(){
        //console.log(typeof (System.getUserInfoInt("member_id")));
        //var partyMemberId = parseInt( System.getUserInfoInt("member_id") );
        var partyMemberName = $(".perMess h4").html();
        var needFee = $(".addMoney input").val();
        if(payType == 0){
            var startMonth = parseInt($(".date ul li.active").text());
            if(needFee == ""){
                // alert("缴纳党费不能为空")
                $(".payCostAlert").css("display","block");
                setTimeout(function delay(){
                    $(".payCostAlert").css("display","none");
                },1000);
            }else{
                console.log(typeof (partyMemberName),typeof (needFee),yearTime,typeof (startMonth));
                $.get(gloablePath+"/party-app-building-front/order/write/submitPartyFeeOrder?requestUser=hotel&requestPassword=123456",{partyMemberId:System.getUserInfoInt("member_id") ,partyMemberName:partyMemberName,needFee:needFee,payFee:needFee,year:yearTime,startMonth:startMonth,endMonth:startMonth},function(data){
                    console.log(data);
                    // alert("对接中，敬请期待")
                    $(".payCostCon").css("display","block");
                    setTimeout(function delay(){
                        $(".payCostCon").css("display","none");
                        // history.go(0);
                    },1000);
                })
            }
        }else{
            $(".payCostCon").css("display","block");
            setTimeout(function delay(){
                $(".payCostCon").css("display","none");
                // history.go(0);
            },1000);
        }


    })
})