$(function(){
	updownData();
})
var pageNos = 0;
var lenCollent;
function messageList(){//消息通知信息列表
	var userId = System.getUserInfoInt('user_id');
	var urls = gloablePath+'/party-app-education-front/res/readonly/listMyCollection?userId='+userId;
	var html='';
	$.get(urls,{pageNo:pageNos,pageSize:5},function(data) {
		if (data.resultCode && data.resultCode == 200) {
			var cont = data.dataList;
			lenCollent = cont.length;
			if(lenCollent>0){
				for(var i=0;i<lenCollent;i++){
					var name = cont[i].name;
					var createTime = cont[i].createTime.substring(0,10);
					var dataUrl = cont[i].url;
					var resId = cont[i].resId;//资讯活动序号
					var mediaType = cont[i].mediaType;
					var id = cont[i].id;//收藏id
					var resType = cont[i].resType;
					html +='<li class="mui-media licon" data-scId = "'+id+'" data-restype = "'+resType+'" data-resid = "'+resId+'" data-url="'+dataUrl+'">'
					html +='<h3 class="myColl_h3"><i class="date2">'+createTime+'</i></h3>'
					html +='<div class="window">'
					html +='<img class="right" src="../images/mycenter/r_1.png">'
					html +='<div class="mui-media-body">'                             
					//html +='<p class="mui-ellipsis-4"><span class="name" href="javascript:;">@</span>近日，各省市税务部门在开展个人</p>'
					html +='<p class="mui-ellipsis-4">'+name+'</p>'
					html +='</div></div></li>'
				}
				$("#conCollect").html();
				$("#conCollect").html(html);
			}
		}else{
			html='<li class="mui-media" style="height:60px;line-height:60px;text-align:center;">您还没有收藏，快点去收藏吧！</li>'
			$("#conCollect").html();
			$("#conCollect").html(html);
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

mui("#conCollect").on("tap","li",function(){
	var newsDetailId = $(this).data("resid");
	var resType = $(this).data("restype");
    sessionStorage.setItem("newsDetailId",newsDetailId);
    sessionStorage.setItem("resType",resType);
    window.location.href="mycollDetail-1.html";
});

