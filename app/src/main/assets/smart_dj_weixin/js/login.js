// JavaScript Document
//mui初始化
mui.init();
//获取屏幕高度
function winHeight(obj){
	var winsHeight =  $(window).height();
	$(obj).height(winsHeight);
}
function loginTest(){
	var name = $('.login_name').val();
	var pasd = $('.login_pswd').val();
	if(name==''){
		tip('用户名不能为空');
		return;
	}else if(pasd==''){
		tip('密码不能为空');
		return;
	}
	var urls = gloablePath+'/party-user-front/login/readonly/login?requestUser=hotel&requestPassword=123456';
	console.log(name,pasd,urls);
	$.post(urls, {userName : '142702198904160342', password : '123456'}, function(data) {
		console.log(data);
		if (data.resultCode && data.resultCode == 200) {
			var userId = data.data.userId;
			var isPartyBranch = data.data.isPartyBranch;
			var isPartyMember=data.data.isPartyMember;
			var nickname = data.data.nickname;
			storage.setItem('userId',userId);
			storage.setItem('isPartyBranch',isPartyBranch);
            storage.setItem('isPartyMember',isPartyMember);
            storage.setItem('nickname',nickname);
            
            setCookie('userId', userId, setCookieDate(7));
            setCookie('isPartyBranch', isPartyBranch, setCookieDate(7));
            setCookie('isPartyMember', isPartyMember, setCookieDate(7));
            setCookie('nickname', nickname, setCookieDate(7));
            
			partyBran(userId);
			tip('登录成功');//后期需要删除
		}else{
			tip('账号或密码错误！');
		}
	});
};
function findbut(){
	var phone = $('.login_name').val();
	var code = $('.forg_pasd').val();
	if(phone==''){
		tip('手机号不能为空');
		return;
	}else if(code==''){
		tip('验证码不能为空');
		return;
	}
	var url = gloablePath + '/party-user-front/login/readonly/verifyPhoneCode';
	$.get(url, {phone : phone,code:code}, function(data) {
		if (data.resultCode && data.resultCode == 200) {
			location.href="mycenter/resetPassword.html";
		}else{
			//tip('111');
		}
	});
};
var wait = 10;
var timeYs;
function giveCode(time){
	timeYs = time;
	var phoneNo = $('.login_name').val();
	var reg = new RegExp(/^1[34578]\d{9}$/);
	if(phoneNo!==''){
		if(reg.test(phoneNo)){
			phoneCode(timeYs);
			ismg(phoneNo);
		}else{
			tip('手机号不对！');
		}
		return;
	}else{
		tip('手机号不能为空！');
	}
};
function phoneCode(time){
	if (wait == 0) {
        time.setAttribute("onclick", "javascript:;giveCode(this)");
        time.innerHTML = "发送验证码";
        wait = 10;
    } else {
        time.setAttribute("onclick", "");
        time.innerHTML = "&nbsp;重新发送(" + wait + ")";
        wait--;
        setTimeout(function () {
            phoneCode(timeYs);
        }, 1000)
    }	
};
function ismg(a){
	var url = gloablePath+ '/party-user-front/login/readonly/sendVerifyCode';
	$.get(url, {phone : a}, function(data) {
		if (data.resultCode && data.resultCode == 200) {
			
		}else{
			tip('账号或密码错误！');
		}
	});
};
function pasdSubmit(){
	var pasdOne = $('.pasdOne').val();
	var pasdTwo = $('.pasdTwo').val();
	if(pasdOne==''||pasdTwo==''){
		tip('密码不能为空！');
	}else{
		if(pasdOne==pasdTwo){
			
		}else{
			tip('密码不一致！')
		}
	}
};
function localData(){//判断是否登录过，如登录过则跳转至首页
	//var userId =  storage.getItem("userId");
	var userId =  getCookie('userId');
	if(userId!=null){
		//location.href="index.html";
	}
};
function partyBran(a){//党员信息查询
	var url = gloablePath + '/party-app-building-front/member/readonly/getPartyMember';
	$.get(url, {userId : a}, function(data) {
		if (data.resultCode && data.resultCode == 200) {
			var cont = data.data;
			var partyBranchId = cont.partyBranchId;
			var phone = cont.phone;
			var partyMemberId = cont.id;
			var code = cont.code;
			var integral = cont.integral; 
			storage.setItem('partyBranchId',partyBranchId);
			storage.setItem('phone',phone);
			storage.setItem('partyMemberId',partyMemberId);
			storage.setItem('code',code);
			storage.setItem('integral',integral);
			
			setCookie('partyBranchId', partyBranchId, setCookieDate(7));
			setCookie('phone', phone, setCookieDate(7));
			setCookie('partyMemberId', partyMemberId, setCookieDate(7));
			setCookie('code', code, setCookieDate(7));
			setCookie('integral', integral, setCookieDate(7));
			
			//location.href="index.html";
		}
	});
}


/*点击修改*/
function editBtn(obj,a){
	var textCon;
	if(a==0){
		textCon = $(obj).find('span').text();
		$('.nicknameInput').val(textCon);
		$(obj).find('span').hide();
		$('.nicknameInput').show();
		$('.nicknameInput').focus();
	}else if(a==1){
		textCon = $(obj).find('span').text();
		$('.modPhoneInput').val(textCon);
		$(obj).find('span').hide();
		$('.modPhoneInput').show();
		$('.modPhoneInput').focus();
	}
}
$(".nicknameInput").blur(function(){
	var inpuText = $(this).val();
	$(this).hide();
	$('.nicknameCon').html(inpuText);
	$('.nicknameCon').show();
});
$(".modPhoneInput").blur(function(){
	var inpuText = $(this).val();
	
	var phoneNo = $('.login_name').val();
	var reg = new RegExp(/^1[34578]\d{9}$/);
	if(inpuText!==''){
		if(reg.test(inpuText)){
			$(this).hide();
			$('.modPhoneCon').html(inpuText);
			$('.modPhoneCon').show();
		}else{
			tip('手机号不对！');
			$('.modPhoneInput').focus();
		}
		return;
	}else{
		tip('手机号不能为空！');
		$('.modPhoneInput').focus();
	}
});
//var nicknames = storage.getItem('nickname');
//var nicknames = getCookie('nickname');
var nicknames = System.getUserInfoStr('nick_name');
var modPhoneCons = System.getUserInfoStr('user_phone');

function personalInf(){
	var url = gloablePath + '/party-app-building-front/member/readonly/getPartyMember';
	//var userId = storage.getItem('userId');
	var userId = getCookie('userId');
	$('.nicknameCon').html(nicknames);
	$('.modPhoneCon').html(modPhoneCons);
	$.get(url, {userId : userId}, function(data) {//总学时
		if(data.resultCode && data.resultCode == 200) {
			console.log(data);
		}
	})
}
function outLogin(){
	mui.confirm('您确定需要退出？', '退出登录', function(e) {
        if (e.index == 1) {
	        var url = gloablePath + '/party-user-front/login/write/stbLoginOut';
			//var userId = storage.getItem('userId');
			var userId = getCookie('userId');
			$.get(url, {userId : userId}, function(data) {//总学时
				if(data.resultCode && data.resultCode == 200) {
					location.href="../login.html"
				}
			})  
        }
    })
}
function integral(){
	//var integral = storage.getItem('integral');
	//var integral = getCookie('integral');
	var integral =System.getUserInfoStr('member_integral');
	$('.integ_no').html(integral);
}

function navRoll(){
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
    var newsSwiper = new Swiper('.newsTit', {
        slidesPerView: 4,
        paginationClickable: true,
        freeMode: true    //自动贴合
    });
    $(".newsTit .swiper-slide ").eq(0).find("a").addClass("active");
}
function dataSelection(obj,a){
	$(".newsTit .swiper-slide a").removeClass("active");
	$(obj).addClass("active");
}
//我的审批
var contApprov;
function appChange(obj,a){
	$(obj).find('span').addClass('active');
	$(obj).siblings().find('span').removeClass('active');
	var lenNo = 0;
	var len = $('.approvCon li').length-1;
	var appNo;
	$('.approvCon li').hide();
	for(var i=0;i<len;i++){
		var status = $('.approvCon li').eq(i).data('myappstatus');
		if(a==0){
			$('.approvCon li').eq(i).show();
		}else if(a==1 && status==2){
			$('.approvCon li').eq(i).show();
		}else if(a==2 && status==0){
			$('.approvCon li').eq(i).show();
		}else if(a==3 && status==4){
			$('.approvCon li').eq(i).show();
		}
		appNo = $('.approvCon li').eq(i).css('display');
		if(appNo=='none'){
			lenNo++;
		}
	}
	if(len==lenNo){
		$('.noInf').show();
	}
}
function approvList(){
	var url = gloablePath + '/party-app-building-front/member/readonly/listMemberOrgInAndOut';
	/*var userId = storage.getItem('userId');
	var partyMemberId = storage.getItem('partyMemberId');
	var userId = getCookie('userId');
	var partyMemberId = getCookie('partyMemberId');*/
	var userId = System.getUserInfoInt('user_id');
	var partyMemberId = System.getUserInfoInt('member_id');
	$.get(url, {userId : userId, partyMemberId : partyMemberId, limitNum:6, sortType:0}, function(data) {//总学时
		if (data.resultCode && data.resultCode == 200) {
			contApprov = data.dataList;
			var len = contApprov.length;
			var html='';
			for(var i=0;i<len;i++){
				var updateDate = contApprov[i].updateDate;
				var status = contApprov[i].status;
				var outName = contApprov[i].outName;
				var inName = contApprov[i].inName;
				var approResult;
				if(status==0){//0：待转入审核，1：转入转出中，2：已完成，3：转出成功，4：转入不成功
					approResult='待转入审核';
				}else if(status==1){
					approResult='转入转出中';
				}else if(status==2){
					approResult='已完成';
				}else if(status==3){
					approResult='转出成功';
				}else if(status==4){
					approResult='转入不成功';
				}
				html +='<li class="approvCon_list" data-myappStatus='+status+'>';
				html +='<div class="approvCon_list_title clearfix">';
				html +='<p class="approvCon_list_titleCon">党组关系转移</p>';
				html +='<p class="approvCon_sign approvCon_sign_1"><span class="approv_status">'+approResult+'</span></p>';
				html +='</div>';
				html +='<div class="approvCon_divCon">';
				html +='<p class="approvCon_date">办理日期：<span>'+updateDate+'</span></p>';
				html +='<p class="approvCon_con">您申请的党组关系由<span>'+outName+'</span>转到<span>'+inName+'</span></p>';
				html +='</div>';
				html +='</li>';
			}
			$('.approvCon').prepend(html);
		}
	})
}





