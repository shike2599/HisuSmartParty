var pageNos = 0;
var lenMessage;
var liLen = 5;
var countDataSum = 0;
var loadPar = 0;
var parm;
var lastId;
var lastTime;
var falg = false;
function messageList(){//消息通知信息列表
	var pbId =  System.getUserInfoInt('member_partyBranchId');
	var userId = System.getUserInfoInt('user_id');
	var urls = gloablePath+'/party-app-education-front/info/readonly/listNoticeByTime?partyBranchId='+pbId+'&userId='+userId;
	var html='';
	if(loadPar==0){
		parm = {
			pageSize:10
		}
	}else{
		parm = {
			pageSize:10,
			id:lastId,
			publishTime:lastTime
		}
	}
	$.get(urls,parm,function(data) {
		if (data.resultCode && data.resultCode == 200) {
			var cont = data.dataList;
			lenMessage = cont.length;
			if(lenMessage>0){
				for(var i=0;i<lenMessage;i++){
					var name = cont[i].name;
					var content = cont[i].content;
					var times = cont[i].publishTime;
					var dataId = cont[i].id;
					var hasRead = cont[i].hasRead;
					html +='<li class="mui-table-view-cell mui-media" data-id="'+dataId+'" data-time = "'+times+'">';
					if(hasRead){
						html +='<a href="javascript:;" class="messageIco-1 clearfix">  ';
					}else{
						html +='<a href="javascript:;" class="messageIco clearfix">  ';
					}
					html +='<div class="mui-media-body">';
					html +='<span class="mui-ellipsis-3 fontweigth messName">'+name+'</span>';
					html +='<p class="mui-ellipsis-3 messContent">'+content+'</p>';
					html +='<h3>';
					html +='<i class="date">'+times+'</i>';
					html +='</h3>';
					html +='</div>';
					html +='</a>';
					html +='</li>';
				}
				//$("#conMessage").html();
				$("#conMessage").append(html);
				liLen = $("#conMessage li").length;
				loadPar = 1;
				lastId = $("#conMessage li:last-child").data('id');
				lastTime = $("#conMessage li:last-child").data('time');
			}else{
				falg=true;
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
		//messageList();
		mui('#pullrefresh').pullRefresh().endPulldownToRefresh(); //refresh completed
	}, 1000);
}
var count = 0;
/**
 * 上拉加载具体业务实现
 */
function pullupRefresh() {
	setTimeout(function() {
		if(falg) {
		    mui('#pullrefresh').pullRefresh().endPullupToRefresh(true); //参数为true代表没有更多数据了。
		    return;
		} else {
		    mui('#pullrefresh').pullRefresh().endPullupToRefresh(false);
		}
		//mui('#pullrefresh').pullRefresh().endPullupToRefresh((++count > 2)); //参数为true代表没有更多数据了。
		var table = document.body.querySelector('.mui-table-view');
		var cells = document.body.querySelectorAll('.mui-table-view-cell');
		pageNos++;
		messageList();
	}, 1000);
}

mui('#conMessage').on("tap","li",function(){
	var obj = $(this);
	var noticeInfoId = obj.data('id');
	var userId = System.getUserInfoInt('user_id');
	var partyMemberId =  System.getUserInfoInt('member_id');
	var urls = gloablePath+'/party-app-education-front/info/write/readNoticeNum?partyMemberId='+partyMemberId+'&userId='+userId+'&noticeInfoId='+noticeInfoId;
	$.get(urls,function(data) {
		if (data.resultCode && data.resultCode == 200) {
			obj.find('a').addClass('messageIco-1');
			obj.find('a').removeClass('messageIco');
		}
	});
	var messContent = obj.find('.messContent').html();
	var messname = obj.find('.messName').html();
	var messtime = obj.find('.date').html();
	sessionStorage.setItem("messContent",messContent);
	sessionStorage.setItem("messname",messname);
	sessionStorage.setItem("messtime",messtime);
	window.location.href="messageDetail-1.html";
		
})
