var weburl = 'http://171.8.79.251';//接口连接地址
var storage=window.localStorage;
var jpart;//0为ios，1为android
var gloablePath = 'http://171.8.79.251';
var path = "/dj2.0/smart_dj_weixin/smart_dj_weixin";
function platformJudgement(){//判断移动端平台
	if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {//iphone平台
	    //alert(navigator.userAgent);  
	    //window.location.href ="iPhone.html";
	    
	} else if (/(Android)/i.test(navigator.userAgent)) {//android平台
	    //alert(navigator.userAgent); 
	    //window.location.href ="Android.html";
	}
}

//底部高亮显示（待优化）
$("#footer>.mui-tab-item").click(function(){
    var index = $(".mui-bar a").index(this);
    sessionStorage.setItem('index',index)
    if(sessionStorage.getItem('index')==0){
        $('.mui-bar a:nth-child(1) .mui-icon ').find("img").attr('src','images/homePage/home.png')
        $('.mui-bar a:nth-child(2) .mui-icon ').find("img").attr('src','images/homePage/book-1.png')
        $('.mui-bar a:nth-child(3) .mui-icon ').find("img").attr('src','images/homePage/dang-1.png')
        $('.mui-bar a:nth-child(4) .mui-icon ').find("img").attr('src','images/homePage/doit-1.png')
        $('.mui-bar a:nth-child(5) .mui-icon ').find("img").attr('src','images/homePage/prosen-1.png')
        window.location.href='index.html'
    } else if(sessionStorage.getItem('index')==1){
        $('.mui-bar a:nth-child(1) .mui-icon ').find("img").attr('src','images/homePage/home-1.png')
        $('.mui-bar a:nth-child(2) .mui-icon ').find("img").attr('src','images/homePage/study.png')
        $('.mui-bar a:nth-child(3) .mui-icon ').find("img").attr('src','images/homePage/dang-1.png')
        $('.mui-bar a:nth-child(4) .mui-icon ').find("img").attr('src','images/homePage/doit-1.png')
        $('.mui-bar a:nth-child(5) .mui-icon ').find("img").attr('src','images/homePage/prosen-1.png')
        window.location.href='study.html'
    } else if(sessionStorage.getItem('index')==2){
        $('.mui-bar a:nth-child(1) .mui-icon ').find("img").attr('src','images/homePage/home-1.png')
        $('.mui-bar a:nth-child(2) .mui-icon ').find("img").attr('src','images/homePage/book-1.png')
        $('.mui-bar a:nth-child(3) .mui-icon ').find("img").attr('src','images/homePage/dang.png')
        $('.mui-bar a:nth-child(4) .mui-icon ').find("img").attr('src','images/homePage/doit-1.png')
        $('.mui-bar a:nth-child(5) .mui-icon ').find("img").attr('src','images/homePage/prosen-1.png')
        window.location.href='partyBuilding.html'
    }else if(sessionStorage.getItem('index')==3){
        $('.mui-bar a:nth-child(1) .mui-icon ').find("img").attr('src','images/homePage/home-1.png')
        $('.mui-bar a:nth-child(2) .mui-icon ').find("img").attr('src','images/homePage/book-1.png')
        $('.mui-bar a:nth-child(3) .mui-icon ').find("img").attr('src','images/homePage/dang-1.png')
        $('.mui-bar a:nth-child(4) .mui-icon ').find("img").attr('src','images/homePage/doit.png')
        $('.mui-bar a:nth-child(5) .mui-icon ').find("img").attr('src','images/homePage/prosen-1.png')
        window.location.href='practice.html'
    }else if(sessionStorage.getItem('index')==4){
        $('.mui-bar a:nth-child(1) .mui-icon ').find("img").attr('src','images/homePage/home-1.png')
        $('.mui-bar a:nth-child(2) .mui-icon ').find("img").attr('src','images/homePage/book-1.png')
        $('.mui-bar a:nth-child(3) .mui-icon ').find("img").attr('src','images/homePage/dang-1.png')
        $('.mui-bar a:nth-child(4) .mui-icon ').find("img").attr('src','images/homePage/doit-1.png')
        $('.mui-bar a:nth-child(5) .mui-icon ').find("img").attr('src','images/homePage/porsen.png')
        window.location.href='my.html'
    }
})

//提示框
function tip(a){
	mui.toast(a);
}
var pageNos = 0;
var gloablePath = 'http://171.8.79.251';
var lenMessage;
function messageList(){//消息通知信息列表
	var pbId = System.getUserInfoInt('member_partyBranchId');
	var urls = gloablePath+'/party-app-education-front/info/readonly/listNotice?partyBranchId='+pbId;
	var html='';
	$.get(urls,{pageNo:pageNos,pageSize:6},function(data) {
		if (data.resultCode && data.resultCode == 200) {
			var cont = data.dataList;
			lenMessage = cont.length;
			//var len = 0;
			if(lenMessage>0){
				for(var i=0;i<lenMessage;i++){
					var name = cont[i].name;
					var content = cont[i].content;
					var times = cont[i].publishTime;
					var dataId = cont[i].id;
					html +='<li class="mui-table-view-cell mui-media" data-id='+dataId+'>';
					html +='<a href="javascript:;" class="messageIco clearfix">  ';                     
					html +='<div class="mui-media-body">';
					html +='<span class="mui-ellipsis-3 fontweigth">'+name+'</span>';
					html +='<p class="mui-ellipsis-3">'+content+'</p>';
					html +='<h3>';
					html +='<i class="date">'+times+'</i>';
					html +='</h3>';
					html +='</div>';
					html +='</a>';
					html +='</li>';
				}
				$("#conMessage").html();
				$("#conMessage").html(html);
			}
		}
	});
}
function updownData(){ //上拉加载，下拉刷新
	mui.init({
		pullRefresh: {
			container: '#pullrefresh',
			down: {
				callback: pulldownRefresh
			},
			up: {
				contentrefresh: '正在加载...',
				callback: pullupRefresh
			}
		}
	});
	if (mui.os.plus) {
		mui.plusReady(function() {
			setTimeout(function() {
				mui('#pullrefresh').pullRefresh().pullupLoading();
			}, 1000);

		});
	} else {
		mui.ready(function() {
			mui('#pullrefresh').pullRefresh().pullupLoading();
		});
	}
}
/**
 * 下拉刷新具体业务实现
 */
function pulldownRefresh() {
	setTimeout(function() {
		var table = document.body.querySelector('.mui-table-view');
		var cells = document.body.querySelectorAll('.mui-table-view-cell');
		pageNos=1;
		messageList();
		mui('#pullrefresh').pullRefresh().endPulldownToRefresh(); //refresh completed
	}, 1000);
}
var count = 0;
/**
 * 上拉加载具体业务实现
 */
function pullupRefresh() {
	setTimeout(function() {
		mui('#pullrefresh').pullRefresh().endPullupToRefresh((++count > 2)); //参数为true代表没有更多数据了。
		var table = document.body.querySelector('.mui-table-view');
		var cells = document.body.querySelectorAll('.mui-table-view-cell');
		pageNos++;
		messageList();
	}, 1000);
}

/*cookie 存储*/
function setCookie(name, value, expires, path, domain, secure){//存cookie
    var cookieName = encodeURIComponent(name) + '=' + encodeURIComponent(value);
    if(expires instanceof Date){
        cookieName += '; expires=' + expires;
    }
    if(path){
        cookieName += '; path=' + path;
    }
    if(domain){
        cookieName += '; domain=' + domain;
    }
    if(secure){
        cookieName += '; secure';
    }
    document.cookie=cookieName;
}
function getCookie(name){//读cookie
    var cookieName = encodeURIComponent(name) + '=';
    var cookieStart = document.cookie.indexOf(cookieName);
    var cookieValue = null;
    if(cookieStart>-1){
        var cookieEnd = document.cookie.indexOf(';', cookieStart);
        if(cookieEnd == -1){
            cookieEnd = document.cookie.length;
        }
        cookieValue = document.cookie.substring(cookieStart + cookieName.length , cookieEnd);
    }
    return cookieValue;
}
function setCookieDate(day){//设置cookie的有效期
    var date = null;
    if(typeof day == 'number' && day>0){
        date = new Date();
        date.setDate(date.getDate()+day);
    }else{
        throw new Error('您传递的天数不合法！必须是数字且大于0');
    }
    return date.toGMTString();
}




