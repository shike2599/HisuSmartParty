function thumInf_showHide(obj){
     $(obj).siblings('.starShow').toggle();
     $(obj).parents('li').siblings('li').find('.starShow').hide();
}
//thumTyp 点赞状态0 为已点赞 1为未点赞
function thumUpfun(obj){
    //alert('测试一下');
    var resId = $(obj).attr('data-bh');
    var thumType = $(obj).attr('data-thumType');
    if(thumType==0){//调用取消点赞接口
        var thumNocan =  $(obj).attr('data-cirIds');
        var url= gloablePath + '/party-app-practice-front/res/write/cancelTheThumbsUp?requestUser=hotel&requestPassword=123456';
        $.get(url,{id:thumNocan}, function(data) {
            if (data.resultCode && data.resultCode == 200) {
                $(obj).attr('data-thumType',1);
                $(obj).html('点赞'); 
                var a = $(obj).parents('div.circle_button').siblings('div.reply-like').find('a');
                var len = a.length;
                if(len==1){
                    $(obj).parents('div.circle_button').siblings('div.reply-like').find('a').remove();
                    $(obj).parents('div.circle_button').siblings('div.reply-like').hide();
                }else if(len>1){
                    for(var i=0;i<len;i++){
                        var thumNo_s =a.eq(i).attr('data-thumno');
                        if(thumNo_s == thumNocan ){
                            a.eq(i).remove();
                        }
                    }
                }   
            }
            $(obj).parent('div.starShow').hide();
        });
    }else if(thumType==1){//调用点赞接口
        var url= gloablePath + '/party-app-practice-front/res/write/giveTheThumbsUp?requestUser=hotel&requestPassword=123456';
        var userId = System.getUserInfoInt('user_id');
        var partyMemberId = System.getUserInfoInt('member_id');
        $.get(url,{resId:resId,userId:userId,partyMemberId:partyMemberId}, function(data) {
            if (data.resultCode && data.resultCode == 200) {
                var thumNo = data.data;
                var name = System.getUserInfoStr('nick_name');
                $(obj).attr('data-thumType',0);  
                $(obj).html('取消');
                var html='<a href="#" class="reply-who" data-thumNo='+thumNo+'>'+name+'</a>';
                $(obj).parents('div.circle_button').siblings('div.reply-like').show();
                $(obj).parents('div.circle_button').siblings('div.reply-like').append(html);
                $(obj).parent('div.starShow').hide();
                $(obj).attr('data-thumNos',thumNo);//$('#circleCon li').eq(i).find('.thumUp_1').attr('data-cirIds',cirId);
                $(obj).attr('data-cirIds',thumNo);
            }
        })
    }
}
//展示全文
function show(obj){
    if($(obj).html()=='全文'){
        $(obj).html('收起');
        $(obj).parents().prev('.hiddenAll').css('display','block');
    }else if($(obj).html()=='收起'){
        $(obj).html('全文');
        $(obj).parents().prev('.hiddenAll').css('display','none');
    }

}
var circleNoId;//点赞序号
var xuhList = [];
var partyBranchId = System.getUserInfoInt('member_partyBranchId')
/*信息列表*/
function circleList(){
	var url= gloablePath + '/party-app-practice-front/res/readonly/listMemberActionWithPulled?requestUser=hotel&requestPassword=123456';
    $.get(url,{cateCode: 5007}, function(data) {
    	var cont = data.dataList;
    	var len = cont.length;
    	var html ='';
        console.log(data);
        if(len==0){
    		$(".mui-table-view").append('<p style="text-align: center;margin-top: 100px">暂无数据......</p>');    
    	}else{
            for(var i=0;i<len;i++){
                var headPor=cont[i].partyMemberPhoto;
                var infNo_id = cont[i].id;//党员圈信息序号
                var name = cont[i].name;//标题
                var mediaType = cont[i].mediaType;//类型
                var url = cont[i].url;//视频地址
                var images = cont[i].images;//内容图片组
                var dat = name.substring(0,80);
                var p = name.substring(80,801);
                var publishTime = cont[i].publishTime//发布时间
                var partyMemberId = cont[i].partyMemberId;//发布党员id
                console.log(parseInt(partyMemberId));
                var partyMemberName = cont[i].partyMemberName;//发布党员名称
                xuhList[i] =  infNo_id;
                //lisThumbUp(infNo_id);//点赞记录查询 
                html +='<li class="mui-table-view-cell" name="'+infNo_id+'">';
                html +='<div class="content">';
                html +='<div class="circle_title">';
                if(headPor=='' || headPor==undefined){
                    html +='<img src="images/quer/under.png">';
                }else{
                    headPor = gloablePath+'/'+headPor;
                    html +='<img src="'+headPor+'">';
                }
                html +='<div class="info">';
                html +='<span class="circle_name">'+partyMemberName+'</span><span class="circle_data">2378次播放</span></div>';
                html +='<div class="circle_bottom">';
                html +='<span class="upDate">'+handlePublishTimeDesc(publishTime)+'</span></div></div>';
                html +='<div class="circle_content">';
                html +='<div class="circle_info">';
                html +='<p class="one">'+dat+'</p>';
                if(name.length>80){
                    html +='<p class="hiddenAll">'+p+'</p><p class="look"><span class="activedTheme lookAll" style="display: block" onclick="show(this)">全文</span><span class="activedTheme hideAll">收起</span></p>';
                }
                html += '<div class="picture">';
                if(images!=''){
                    $.each(images,function(i,val){
                        html += '<img src="'+gloablePath+'/'+images[i]+'">'
                    })
                }
                html +='</div>'
                html +='</div></div></div>';
                html +='<div class="circle_button">';
                html +='<img class="ellipsis" name="'+infNo_id+'" src="images/quer/quer-more.png" onclick="thumInf_showHide(this)">';
                html +='<div class="starShow"><span class="thumUp thumUp_1" data-bh='+infNo_id+' = onclick="thumUpfun(this)"></span>';
                html +='<span class="commentNes" onclick="commFun(this,'+infNo_id+')">评论</span></div>';
                partyBranch = System.getUserInfoInt('member_id')
                if(partyMemberId== partyBranch){
                    html +='<div class="del" onclick="delComment(this)" name="'+infNo_id+'">删除</div>';
                }
                html +='</div>';
                html +='<div class="reply-like reply-zone-1" data-circXh='+infNo_id+'><img src="images/quer/star.png">';
                html +='</div><div class="reply-comment reply-zone plIdCon" data-plXh='+infNo_id+'></div></li>';  
            }
            $('#circleCon').html(html);
            var lencir = xuhList.length;
            console.log(xuhList);
            for(var i=0;i<lencir;i++){
                var cirCs = xuhList[i];
                lisThumbUp(cirCs);
                cirCommentList(cirCs);
                cirStatus(cirCs);
            }
        }     
    })
}
function lisThumbUp(a){//加载点赞
    var url= gloablePath + '/party-app-practice-front/res/readonly/listTheThumbsUp?requestUser=hotel&requestPassword=123456';
    $.get(url,{resId: a,}, function(data) {
        var conts = data.dataList;
        var len = conts.length;
        var lenList = $('#circleCon li').length;
        var html='';
        for(var i=0;i<lenList;i++){
            var cirDi = $('#circleCon li').eq(i).find('.reply-like').attr('data-circXh');
            if(len>0 && a== cirDi){
                $('#circleCon li').eq(i).find('.reply-like').addClass('cirshow');
                for(var j=0;j<len;j++){
                    var cirId = conts[j].id;
                    var pName = conts[j].partyMemberName;
                    //pName += '，'
                    html+='<a href="javascript:;" class="reply-who" data-cirId ='+cirId+'>'+pName+'</a>';
                    $('#circleCon li').eq(i).find('.thumUp_1').attr('data-cirIds',cirId);
                }
                $('#circleCon li').eq(i).find('.reply-like').append(html);
                // var htmll =   $('.reply-like').eq(i).find('a:last-child').html()
                // html1 = htmll.substr(0,2);
                //
                // $('.reply-like').eq(i).find('a:last-child').html(html1);
            }
        }
    })
}
function cirCommentList(a){//加载评论
    var url= gloablePath + '/party-app-practice-front/res/readonly/listTheComment?requestUser=hotel&requestPassword=123456';
    $.get(url,{resId: a,}, function(data) {
        var contis = data.dataList;
        var lenis = contis.length;
        var lenList = $('#circleCon li').length;
        var html='';
        for(var i=0;i<lenList;i++){
           var plxh = $('#circleCon li').eq(i).find('.reply-zone').attr('data-plxh'); 
           if(lenis>0 && a==plxh){
                $('#circleCon li').eq(i).find('.reply-zone').addClass('cirshow');
                for(var j=0;j<lenis;j++){ 
                    var plxhId = contis[j].id;
                    var pname  = contis[j].partyMemberName;
                    var comment= contis[j].comment;
                    html +='<div class="comment-item" onclick="delcomLi(this)" name="'+plxhId+'">';
                    html +='<a class="reply-who" href="javascript:;">'+pname+'</a>：'+comment+'';
                    html +='</div>';
                }
                $('#circleCon li').eq(i).find('.reply-zone').append(html); 
           }
        }
    })
}
function cirStatus(a){//点赞状态
    var url= gloablePath + '/party-app-practice-front/res/readonly/getUserThumbsUp?requestUser=hotel&requestPassword=123456';
    var userId = System.getUserInfoInt('user_id');
    var partyMemberId = System.getUserInfoInt('member_id');

    $.get(url,{resId: a,userId:userId,partyMemberId:partyMemberId}, function(data) {
        var contSt = data.data;
        var lenList = $('#circleCon li').length;
        for(var i=0;i<lenList;i++){
            var dataXh = $('#circleCon li').eq(i).attr('name');
            if(a==dataXh&&contSt>0){
                $('#circleCon li').eq(i).find('.thumUp_1').html('取消');
                $('#circleCon li').eq(i).find('.thumUp_1').attr('data-thumtype',0);
            }else if(a==dataXh&&contSt==0){
                $('#circleCon li').eq(i).find('.thumUp_1').html('点赞');
                $('#circleCon li').eq(i).find('.thumUp_1').attr('data-thumtype',1);
            }
        }
    })
}
function commFun(obj,b){
    var height = $(window).height();
    $(obj).parent('.starShow').hide();
    $('.tbj').show();
    $('.tbj').height(height);
    $('#divTop').css('left',0);
    $('.commit-button').attr('data-liNo',b);
}
$('.tbj').on('tap', function () {
    $('.tbj').hide();
    $('#divTop').css('left','-600px');
})
$('.commit-input').on('keyup',function(e){
    // 激活评论按钮
    activeCommitBtn();
    var html = $('.commit-input').val();
    if(html==null||html==""){
        diabaledCommitBtn();
        $('.commit-input').val('');
    }
});
//实现发布了多久的时间描述
function getTs(time){
    var arr = time.split(/[- :]/),
        _date = new Date(arr[0], arr[1]-1, arr[2], arr[3], arr[4], arr[5]),
        timeStr = Date.parse(_date)
    return timeStr
}
function handlePublishTimeDesc(post_modified){//时间加载
    // 拿到当前时间戳和发布时的时间戳，然后得出时间戳差
    var curTime = new Date();
    var postTime = new Date(post_modified);                  //部分浏览器不兼容此转换建议所以对此进行补充（指定调用自己定义的函数进行生成发布时间的时间戳）
    //var timeDiff = curTime.getTime() - postTime.getTime();
    //上面一行代码可以换成以下（兼容性的解决）
    var timeDiff = curTime.getTime() - getTs(post_modified);
    // 单位换算
    var min = 60 * 1000;
    var hour = min * 60;
    var day = hour * 24;
    var week = day * 7;
    var month =  week*4;
    var year = month*12;
    // 计算发布时间距离当前时间的周、天、时、分
    var  exceedyear = Math.floor(timeDiff/year);
    var exceedmonth = Math.floor(timeDiff/month);
    var exceedWeek = Math.floor(timeDiff/week);
    var exceedDay = Math.floor(timeDiff/day);
    var exceedHour = Math.floor(timeDiff/hour);
    var exceedMin = Math.floor(timeDiff/min);
    // 最后判断时间差到底是属于哪个区间，然后return
    if(exceedyear<100&&exceedyear>0){
        return exceedyear + '年前';
    }else{
        if(exceedmonth<12&&exceedmonth>0){
            return exceedmonth + '月前';
        }else{
            if(exceedWeek<4&&exceedWeek>0){
                return exceedWeek + '星期前';
            }else{
                if(exceedDay < 7 && exceedDay > 0){
                    return exceedDay + '天前';
                }else {
                    if (exceedHour < 24 && exceedHour > 0) {
                        return exceedHour + '小时前';
                    } else {
                        return exceedMin + '分钟前';
                    }
                }
            }
        }
    }
}
function activeCommitBtn(){
    $('.commit-button').css({
        'background-color': '#59BB3B',
        'color': 'white'});
    $('.commit-button').attr('disabled', false);
}
/*禁用评论按钮（设置样式，同时设置disabed属性*/
function diabaledCommitBtn() {
    $('.commit-button').css({
        'background-color': '#ccc',
        'color': 'whitesmoke'
    });
    $('.commit-button').attr('disabled', true);
}
//发送评论
function comSend(obj){
    var resId = $(obj).attr('data-lino');
    var comment = $('.commit-input').val();
    var html='';
    var userId = System.getUserInfoInt('user_id');
    var partyMemberId = System.getUserInfoInt('member_id');
    var name = System.getUserInfoStr('nick_name');
    $.ajax({
        type: 'post',
        url:gloablePath+'/party-app-practice-front/res/write/giveTheComment?&&requestUser=hotel&requestPassword=123456',
        dataType: 'json',
        data: {resId: resId,userId: userId,partyMemberId: partyMemberId,comment:comment},
        success: function(data){
            html +='<div class="comment-item" onclick="delcomLi(this)" name="'+data.data+'">';
            html +='<a class="reply-who" href="javascript:;">'+name+'</a>：';
            html +=comment;
            html +='</div>';
            var lenList = $('#circleCon li').length;
            for(var i=0;i<lenList;i++){
                var dataXh = $('#circleCon li').eq(i).attr('name');
                if(resId==dataXh){
                    $('#circleCon li').eq(i).find('.plIdCon').append(html);
                    $('.commit-input').val('');
                    //activeCommitBtn();
                    diabaledCommitBtn();
                    $('.tbj').hide();
                    $('#divTop').css('left','-600px');
                }
            } 
        }
    })
}
// function delComment(obj){
//     $('.pop').show();
//     var height = $(window).height();
//     $('.tbjs').show();
//     $('.tbjs').height(height);
//     $('.yes').attr('data-delNo',$(obj).attr('name'));
// }

function delComment(obj){
    //$('.pop').show();
    var height = $(window).height();
    $('.tbjs').show();
    var Id = $(obj).attr('name');
    //console.log(Id);
    $('.delPassage').css('display','block');
    var userId = System.getUserInfoInt('user_id');
    var partyMemberId = System.getUserInfoInt('member_id');
    $('.delPassage  .yes').click(function(){
        $.ajax({
            type: 'get',
            url: gloablePath+'/party-app-practice-front/res/write/deleteMyAction?&&requestUser=hotel&requestPassword=123456',
            dataType: 'json',
            data: {
                resId: Id,
                userId: userId ,
                partyMemberId: partyMemberId
            },
            success: function(data){
                console.log(data);
                if(data.resultCode==200){
                    var lenList = $('#circleCon li').length;
                    for(var i=0;i<lenList;i++){
                        $('')
                        var lisNo =  $('#circleCon li').eq(i).attr('name');
                        //console.log(lisNo);
                        if(lisNo==Id){
                            $('#circleCon li').eq(i).remove();
                            $('.delPassage').css('display','none')
                        }
                    }
                }
            }
        });
    })
    $('.delPassage .no').click(function(){
        $('.delPassage').css('display','none')
    })

    $('.tbjs').height(height);
    // $('.yes').attr('data-delNo',$(obj).attr('name'));
}
// function delBtn(obj,b){//0删除，1不删除
//     var Id = $(obj).attr('data-delno');
//     var userId = System.getUserInfoInt('user_id');
//     var partyMemberId = System.getUserInfoInt('member_id');
//     if(b==0){
//         $.ajax({
//             type: 'get',
//             url: gloablePath+'/party-app-practice-front/res/write/deleteMyAction?&&requestUser=hotel&requestPassword=123456',
//             dataType: 'json',
//             data: {
//                 resId: Id,
//                 userId: userId,
//                 partyMemberId: partyMemberId
//             },
//             success: function(data){
//                 if(data.resultCode==200){
//                    var lenList = $('#circleCon li').length;
//                    for(var i=0;i<lenList;i++){
//                         var lisNo =  $('#circleCon li').eq(i).attr('name');
//                         if(lisNo==Id){
//                             $('#circleCon li').eq(i).remove();
//                         }
//                    }
//                 }
//             }
//         });
//         $('.pop').hide();
//         $('.tbjs').hide();
//         $('.yes').attr('data-delNo','');
//     }else if(b==1){
//         $('.pop').hide();
//         $('.tbjs').hide();
//         $('.yes').attr('data-delNo','');
//     }
// }
//评论删除
function delcomLi(obj){
    var Id = $(obj).attr('name')
    $('.pop').css('display','block');
    $('.pop .sub .yes').on('tap',function(){
        $.ajax({
            type: 'get',
            url: gloablePath+'/party-app-practice-front/res/write/deleteTheComment?&&requestUser=hotel&requestPassword=123456',
            dataType: 'json',
            data: {id: Id},
            success: function(data){
                $(obj).remove();
                $('.pop').css('display','none');
            }
        })
    })
    $('.pop .no').on('tap',function(){
        $('.pop').css('display','none');
    })
}
//发布党员圈
$('.cameraP').click(function(){
    System.toUpLoadActivity(5007);
    //window.webkit.messageHandlers.toUpLoadActivity.postMessage(7);
})
//头像获取
var user_photo = System.getUserInfoStr('user_photo');
$('.namePicture').find('img:nth-child(1)').attr('src',user_photo);