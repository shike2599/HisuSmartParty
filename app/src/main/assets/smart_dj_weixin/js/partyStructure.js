var parentId = -1;
var url = gloablePath+"/party-app-building-front/org/readonly/listCommitteeByParent?requestUser=hotel&requestPassword=123456";
var url1 = gloablePath+"/party-app-building-front/org/readonly/listBranchByCommittee?requestUser=hotel&requestPassword=123456";
var url2 = gloablePath+"/party-app-building-front/org/readonly/listMemberByBranch?requestUser=hotel&requestPassword=123456";

var html = "";
// 请求下级党委
function threeList(a){
    $.get(url,{parentId:a},function(data){
        var cont = data.dataList;
        var len = cont.length;
        var id;
        for(var i=0;i<len;i++){
            id= cont[i].id;
            var name = cont[i].name;
            var orgType = cont[i].orgType;
            html += '<ul>'
            html += '<li class="parent_li firstLi" data-id="'+id+'" data-orgType="'+orgType+'">'
            html += '<span class="parent" title="Collapse this branch">'
            html += '<i></i>'
            html += '<span class="title">'+name+'</span>'
            html += '</span>'
            html += '</li>'
            html += '</ul>'
        }
        $('#threeLists').html(html);
        threeAddList(id,orgType);
    })
}
function threeAddList(b,ot){
    if(ot==0){
        $.get(url,{parentId:b},function(data){
            var cont = data.dataList
            var len = cont.length;
            html="";
            for(var i=0;i<len;i++){
                var id= cont[i].id;
                var name = cont[i].name;
                var orgType = cont[i].orgType;
                html += '<ul class="childList">'
                html += '<li class="parent_li" data-id="'+id+'">'
                html += '<span class="child" title="Collapse this branch"  data-orgType="'+orgType+'" data-blocks="0" onclick="threeListOther('+id+',this,'+orgType+')">'
                html += '<i></i>'
                html += '<span class="title">'+name+'</span>'
                html += '</span>'
                html += '</li>'
                html += '</ul>'
            }
            listBranchCon(b);
            $('.firstLi').append(html);
        })
    }else if(ot==1){
        $.get(url2,{parentId:b},function(data){
            var cont = data.dataList
            var len = cont.length;
            html="";
            for(var i=0;i<len;i++){
                var id= cont[i].id;
                var name = cont[i].name;
                var orgType = cont[i].orgType;
                var phone = cont[i].phone;
                var sexs;
                if(cont[i].sex==1){
                    sexs='男';
                }else{
                    sexs='女';
                }
                html += '<ul class="grandList">'
                html += '<li>'
                html += '<span class="greatGrandChild" data-id="'+id+'" data-orgType="'+orgType+'" >'
                html += '<i class="name">'+name+'</i>'
                html += '<i class="sex">'+sexs+'</i>'
                html += '<i class="phoneNumber">'+phone+'</i>'
                html += '<i class="phoneIcon"><img src="../images/dang-xmind/dang-xmind-call.png"></i>'
                html += '</span>'
                html += '</li>'
                html += '</ul>'
            }
            $(obj).parent('li').append(html);
        })
    }
}
function threeListOther(c,obj,op){
    var blackParam =  $(obj).attr('data-blocks');
    if(blackParam==0){
        if(op==0){
            $.get(url,{parentId:c},function(data){
                var cont = data.dataList;
                var len = cont.length;
                html="";
                for(var i=0;i<len;i++){
                    var id= cont[i].id;
                    var name = cont[i].name;
                    var orgType = cont[i].orgType;
                    html += '<ul class="partyList">'
                    html += '<li class="parent_li" data-id="'+id+'">'
                    html += '<span class="child" title="Collapse this branch"  data-orgType="'+orgType+'" data-blocks="0" onclick="threeListOther('+id+',this,'+orgType+')">'
                    html += '<i></i>'
                    html += '<span class="title">'+name+'</span>'
                    html += '</span>'
                    html += '</li>'
                    html += '</ul>'
                }
                $.get(url1,{parentId:c},function(data){
                    var cont = data.dataList;
                    var len = cont.length;
                    for(var i=0;i<len;i++){
                        var id= cont[i].id;
                        var name = cont[i].name;
                        var orgType = cont[i].orgType;
                        html += '<ul class="childList">'
                        html += '<li class="parent_li" data-id="'+id+'">'
                        html += '<span class="child" title="Collapse this branch"  data-orgType="'+orgType+'" data-blocks="0" onclick="threeListOther('+id+',this,'+orgType+')">'
                        html += '<i></i>'
                        html += '<span class="title">'+name+'</span>'
                        html += '</span>'
                        html += '</li>'
                        html += '</ul>'
                    }
                    $(obj).parent('li').append(html);
                })
            })
        }else if(op==1){
            $.get(url2,{parentId:c},function(data){
                var cont = data.dataList;
                var len = cont.length;
                html="";
                for(var i=0;i<len;i++){
                    var id= cont[i].id;
                    var name = cont[i].name;
                    var orgType = cont[i].orgType;
                    var phone = cont[i].phone;
                    var sexs;
                    if(cont[i].sex==1){
                        sexs='男';
                    }else{
                        sexs='女';
                    }
                    html += '<ul class="grandList">'
                    html += '<li>'
                    html += '<span class="greatGrandChild" data-id="'+id+'" data-orgType="'+orgType+'" >'
                    html += '<i class="name">'+name+'</i>'
                    html += '<i class="sex">'+sexs+'</i>'
                    html += '<i class="phoneNumber">'+phone+'</i>'
                    html += '<i class="phoneIcon"><img src="../images/dang-xmind/dang-xmind-call.png"></i>'
                    html += '</span>'
                    html += '</li>'
                    html += '</ul>'
                }
                $(obj).parent('li').append(html);
            })
        }
        $(obj).attr('data-blocks',1);
    }else if(blackParam==1){
        $(obj).parent('li').find('ul').remove();
        $(obj).attr('data-blocks',0);
    }
}
function listBranchCon(b){
    $.get(url1,{parentId:b},function(data){
        var cont = data.dataList;
        var len = cont.length;
        html="";
        for(var i=0;i<len;i++){
            var id= cont[i].id;
            var name = cont[i].name;
            var orgType = cont[i].orgType;
            html += '<ul class="childList">'
            html += '<li class="parent_li" data-id="'+id+'">'
            html += '<span class="child" title="Collapse this branch"  data-orgType="'+orgType+'" data-blocks="0" onclick="threeListOther('+id+',this)">'
            html += '<i></i>'
            html += '<span class="title">'+name+'</span>'
            html += '</span>'
            html += '</li>'
            html += '</ul>'
        }
    })
}
$(function(){
    threeList(parentId);
});



