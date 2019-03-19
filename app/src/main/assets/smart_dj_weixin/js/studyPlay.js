// JavaScript Document
function changeHours(obj,a){
	$(obj).addClass('study_active');
	$(obj).siblings('li').removeClass('study_active');
	var palytext = $(obj).html();
	
	$('.palyName').html(palytext);
	playData(a)
};
function playData(a){
	var timeType = a; 
	var url = gloablePath + '/party-app-education-front/plan/readonly/partyMemberPlan';
	/*var userId = storage.getItem('userId');*/
	var userId = System.getUserInfoInt('user_id');
	$.get(url, {userId : userId,timeType:timeType}, function(data) {
		if (data.resultCode && data.resultCode == 200) {
			var cont = data.data;
			//常规学习
			var ctotalHours = cont.commPlan.totalHours;
			var cplanTotalHours = cont.commPlan.planTotalHours;
			$('#hours_two').html(ctotalHours);
			$('#hours_twoAll').html(cplanTotalHours);
			var cyxbfb = ctotalHours/cplanTotalHours;
			cyxbfb=cyxbfb.toFixed(2);
			$('.commPlans').html(cyxbfb);
			$('#commPlanWid').width(cyxbfb+'%');
			
			//专题学习
			var tototalHours = cont.topicPlan.totalHours;
			var toplanTotalHours = cont.topicPlan.planTotalHours;
			$('#hours_one').html(tototalHours);
			$('#hours_oneAll').html(toplanTotalHours);
			var toyxbfb = tototalHours/toplanTotalHours;
			toyxbfb=toyxbfb.toFixed(2);
			$('.topicPlans').html(toyxbfb);
			$('#topicPlanWid').width(toyxbfb+'%');

		}
	})
}
//排行方法
var rankFalg = 0; //判断排行类型 0为支部排行，1为个人排行
function rankingMod(){
	if(rankFalg == 0){
		$('.leftNav').show();
	}
}
function rankList(a){
	if(rankFalg==0){//个人排行
		$('.leftNav').html('支部排行');
		rankFalg = 1;
		/*$('#rankList_1').html('(我的个人排名第<span>33</span>名)');
		$('#rankList_2').html('(我的个人排名第<span>33</span>名)');
		$('#rankList_3').html('(我的个人排名第<span>33</span>名)');*/
		$('.rankingName_1').html('党员名称');
		$('.tdConClear').html('');
		grPh();
		
	}else if(rankFalg==1){//支部排行
		$('.leftNav').html('个人排行');
		rankFalg = 0;
		/*$('#rankList_1').html('(我的支部排名第<span>33</span>名)');
		$('#rankList_2').html('(我的支部排名第<span>33</span>名)');
		$('#rankList_3').html('(我的支部排名第<span>33</span>名)');*/
		$('.rankingName_1').html('支部名称');
		zbPh();
	}
}
function grPh(){//个人排名
	var url = gloablePath + '/party-data-statistics-front/studyRank/readonly/listPartyMemberRank';
	/*var userId = storage.getItem('userId');
	var userId =  getCookie('userId');
	var partyMemberId = storage.getItem('partyMemberId');*/
	
	var userId = System.getUserInfoInt('user_id');
	var partyMemberId = System.getUserInfoInt('member_id');
	
	$.get(url, {userId : userId, partyMemberId : partyMemberId, limitNum:6, sortType:0}, function(data) {//总学时
		if (data.resultCode && data.resultCode == 200) {
			rankingDataList(1,data);
		}
	})
	$.get(url, {userId : userId,partyMemberId : partyMemberId,limitNum:6,sortType:1}, function(data) {//常规学习
		if (data.resultCode && data.resultCode == 200) {
			rankingDataList(3,data);
		}
	})
	$.get(url, {userId : userId,partyMemberId : partyMemberId,limitNum:6,sortType:2}, function(data) {//专题学习
		if (data.resultCode && data.resultCode == 200) {
			rankingDataList(2,data);
		}
	})
}
function zbPh(){//支部排名
	var url = gloablePath + '/party-data-statistics-front/studyRank/readonly/listPartyBranchRank';
	//var userId = storage.getItem('userId');
	//var partyBranchId = storage.getItem('partyBranchId');
	/*var userId =  getCookie('userId');
	var partyBranchId =  getCookie('partyBranchId');*/
	var userId = System.getUserInfoInt('user_id');
	var partyBranchId = System.getUserInfoInt('member_partyBranchId');
	
	console.log(userId);
	console.log(partyBranchId);
	$.get(url, {userId : userId,partyBranchId:partyBranchId,limitNum:6,sortType:0}, function(data) {//总学时
		if (data.resultCode && data.resultCode == 200) {
			rankingDataList(1,data);
		}
	})
	$.get(url, {userId : userId,partyBranchId:partyBranchId,limitNum:6,sortType:1}, function(data) {//常规学习
		if (data.resultCode && data.resultCode == 200) {
			rankingDataList(3,data);
		}
	})
	$.get(url, {userId : userId,partyBranchId:partyBranchId,limitNum:6,sortType:2}, function(data) {//专题学习
		if (data.resultCode && data.resultCode == 200) {
			rankingDataList(2,data);
		}
	})
}
function rankingDataList(a,data){
	var cont = data.dataList;
	var len = cont.length;
	for(var i=0;i<len;i++){
		var totalHours;
		if(a==1){
			totalHours = cont[i].totalHours;
		}else if(a==2){
			totalHours = cont[i].topicHours;
		}else if(a==3){
			totalHours = cont[i].commHours;
			console.log(totalHours);
		}
		var name = cont[i].name;
		$('#hoursName'+a+'_'+i).html(name);//名称
		$('#hoursStudy'+a+'_'+i).html(totalHours);//学时
	}
}


//考试
function exaStart(a){
	if(a==0){
		$('.red_tip').hide();
		$('.red_tip_bj').hide();
		timer(intDiff);
		$('.noclickBj').hide();
	}else{
		$('.red_tip').hide();
		$('.red_tip_bj').hide();
		System.finshWebView();
		//location.href='../study.html';
	}
}
var intDiff = parseInt(3599);//倒计时总秒数量
function timer(intDiff){
	window.setInterval(function(){
		var day=0,
		hour=0,
		minute=0,
		second=0;//时间默认值    
		if(intDiff > 0){
		    day = Math.floor(intDiff / (60 * 60 * 24));
		    hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
		    minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
		    second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
		}else{//当时间耗尽，刷新页面
		    btnSubmt();
		}
		if (minute <= 9) minute = '0' + minute;
		if (second <= 9) second = '0' + second;
		$('.exa_time_timing').html('<font>'+minute+':'+second+'</font>');
		intDiff--;
	}, 1000);
}

var waits = 6;
function giveCode(){
	if (waits == 0) {
       //location.href="../study.html";
       System.finshWebView();
    } else {
        $('.res_timing').html(waits);
        waits--;
        setTimeout(function () {
            giveCode()
        }, 1000)
    }	
};
var anList;
var tmCon;
var allti=new Array();//题目集合
var htmlExa='';
var tArray = new Array(); //先声明一维 
var ansList = new Array();
function exaList(){//考试题目列表
	var url = gloablePath + '/party-app-education-front/paper/readonly/getTestPaper';
	//var userId = storage.getItem('userId');
	var userId = System.getUserInfoInt("user_id");
	$.get(url, {userId : userId}, function(data) {
		if (data.resultCode && data.resultCode == 200) {
			var cont = data.data;
			var anId = cont.id;
			var remark = cont.remark;
			var name = cont.name;
			anList = cont.questionTypeList;
			var len = anList.length;
			var tiLx;//题目类型
			var tiLxs;
			var tms;//题目数
			for(var i=0;i<len;i++){
				if(anList[i].name=='单选题'){
					tiLxs=0;
				}else{
					tiLxs=1;
				}
				tiLx=anList[i].name;
				tms = anList[i].remark;
				var lenQl = anList[i].questionList.length;
				for(var k=0;k<lenQl;k++){
					anList[i].questionList[k].names=tiLx;
					anList[i].questionList[k].type=tiLxs;
					anList[i].questionList[k].remark=tms;
					allti.push(anList[i].questionList[k]);
				}
			}
			console.log(allti);
			var answers = allti[0].answer;
			var anClass = allti[0].names;
			var anFra = allti[0].score;
			var tmLen = allti[0].remark;
			$('.anListClass').html(anClass);//题型分类
			$('.anListFra').html(anFra);//分数
			$('.anListNumber').html(tmLen);//题目数量
			$('#answerTitle').html(name);//考试题目
			$('#answerDes').html(remark);//考试说明
			$('#answerTitle').attr('data-anId',anId);//题目id
			$('.subjectCon').html(tmnane);//题目名称
			
			var lenEax = allti.length;
			for(var j=0;j<lenEax;j++){
				var tmnane = allti[j].name;
				var opi0 = 'A. '+allti[j].item1;
				var opi1 = 'B. '+allti[j].item2;
				var opi2 = 'C. '+allti[j].item3;
				var opi3 = 'D. '+allti[j].item4;
				var types = allti[j].type;
				var score = allti[j].score
				if(j==0){
					htmlExa +='<ul class="study_exa_ul study_exa_ul_'+j+'  study_exa_status" data-tmid='+allti[j].id+' data-answ='+allti[j].answer+' data-no='+j+' data-score='+score+' data-dasu=1>';
				}else{
					htmlExa +='<ul class="study_exa_ul study_exa_ul_'+j+'" data-tmid='+allti[j].id+' data-answ='+allti[j].answer+' data-no='+j+' data-score='+score+' data-dasu=1>';
				}
				htmlExa +='<li class="study_exa_title subjectCon">'+tmnane+'</li>';
				htmlExa +='<li class="study_exa_con option0" onclick="optionList(this,1)" data-dan="5">'+opi0+'</li>';
				htmlExa +='<li class="study_exa_con option1" onclick="optionList(this,2)" data-dan="5">'+opi1+'</li>';
				htmlExa +='<li class="study_exa_con option2" onclick="optionList(this,3)" data-dan="5">'+opi2+'</li>';
				htmlExa +='<li class="study_exa_con option3" onclick="optionList(this,4)" data-dan="5">'+opi3+'</li>';
				htmlExa +='</ul>';
				
			}
			$("#exa_list").html(htmlExa);
			for(var v=0;v<lenEax;v++){
				var types = allti[v].type;
				var $ullicon = $('.study_exa_ul_'+v);
				var dnan = allti[v].answer;
				if(types==0){//单选
					var csdn = dnan-1;
					$ullicon.find('.option'+csdn).attr('data-dan',1);
				}else if(types==1){//多选
					dnan=dnan.split(",");
					//dnan = Array(dnan);
					var dalen = dnan.length;
					$ullicon.attr('data-dasu',dalen)
					for(var b=0;b<dalen;b++){
						var csdn = dnan[b]-1;
						$ullicon.find('.option'+csdn).attr('data-dan',1);
					}
				}	
			}
			var jon;
			//选项数组
			for(var k=0;k<allti.length;k++){ //一维长度为i,i为变量，可以根据实际情况改变 
				tArray[k]=new Array(); //声明二维，每一个一维数组里面的一个元素都是一个数组； 
				for(var j=0;j<6;j++){
					tArray[k][j]="0"
				}
			}
		}
	});
}
var is=0;
function btnNextList(a){
	//$('.study_exa_con').removeClass('changeRed');
	var len = allti.length;
	if(a==0){//上一步
		is--;
		$('.study_exa_ul_'+is).show();
		$('.study_exa_ul_'+is).siblings().hide();
		if(is==0){
			$('#exa_btn_0').hide();
		}else if(0<is||is<len){
			$('#exa_btn_1').show();
		}
		$("#exa_btn_2").hide();
	}else if(a==1){//下一步
		is++;
		$("#exa_btn_0").show();
		$('.study_exa_ul_'+is).show();
		$('.study_exa_ul_'+is).siblings().hide();
		
		if(is==(len-1)){
			$('#exa_btn_1').hide();
			$("#exa_btn_2").show();
		}
	}
	var anClass = allti[is].names;
	var anFra = allti[is].score;
	var tmLen = allti[is].remark;
	$('.anListClass').html(anClass);//题型分类
	$('.anListFra').html(anFra);//分数
	$('.anListNumber').html(tmLen);//题目数量
}
var TotalScore = 0;//总分
var ansArr = new Array();
var ansAgg = '';
function optionList(obj,a){//选择题目
	var changeType = $(".anListClass").html();
	var fs = $('.anListFra').html();
	var dan = $('.study_exa_status').data('answ');
	var zaw = $(obj).parent('ul').data('no');
	var fscc = $(obj).parent('ul').data('score');
	var dasu = $(obj).parent('ul').data('dasu');
	tArray[zaw][4] = fscc;
	tArray[zaw][5] = dasu;
	if(changeType=='单选题'){
		$(obj).addClass('changeRed');
		$(obj).siblings().removeClass('changeRed');
		var dans = $(obj).data('dan');
		tArray[zaw][0] = dans;
	}else if(changeType=='多选题'){
		var gew = a-1;
		var dans = $(obj).data('dan');
		if($(obj).hasClass('changeRed')){
			$(obj).removeClass('changeRed');
			$(obj).attr('data-fcVa','');
			tArray[zaw][gew] = '0';
		}else{
			$(obj).addClass('changeRed');
			$(obj).attr('data-fcVa',a);
			tArray[zaw][gew] = dans;
		}
	}
};
function btnSubmt(){
	console.log(tArray);
	var len = tArray.length;
	console.log(len);
	var lastScore=0;
	var scoreFj;
	for(var i=0;i<len;i++){
		var jgl = tArray[i][5];
		var jsjg = parseInt(tArray[i][0])+parseInt(tArray[i][1])+parseInt(tArray[i][2])+parseInt(tArray[i][3]);
		if(jgl==jsjg){
			lastScore += parseInt(tArray[i][4]);
		}
	}
	location.href="exaResults.html?lastScore="+lastScore;
}
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

function resultJudgement(){//考试成绩提示语
	var exaScore = getUrlParam('lastScore') ;
	$('.exa_score').html(exaScore);
	if(exaScore>=0 && exaScore<60){
		$('.exa_res_g').removeClass('exa_res_p');
		$('.exa_res_g').addClass('exa_res_fr');
		$(".levTwo_text").text("好好学习，天天向上！");
	}else if(exaScore>=60 && exaScore<85){
		$('.exa_res_g').addClass('exa_res_p');
		$('.exa_res_g').removeClass('exa_res_fr');
		$(".levTwo_text").text("表现不错，再接再厉哈！");
	}else if(exaScore>=85 && exaScore<=100){
		$('.exa_res_g').addClass('exa_res_p');
		$('.exa_res_g').removeClass('exa_res_fr');
		$(".levTwo_text").text("俺的目标是满分奥！");
	}
}

