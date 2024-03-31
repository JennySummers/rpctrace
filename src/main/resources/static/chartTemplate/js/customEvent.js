$(document).ready(function () {

//数组内容
var userAttr = [];

if($("#loginFlag")!=null && $("#loginFlag").text()=="Logout"){
    var titleId = $("title").attr("id");
    var eventName = null;
    var eventAttrName = null;
    var eventAttrVal = null;
    if(titleId=="custom"){
        eventName = "/getEventName";
        eventAttrName = "/getAttrName";
        eventAttrVal = "/getAttrVal";
    }
    customRequest(eventName,"{}","eventName");
    $("#eventName").change(function () {
        if($("#eventName").val()!=null && $("#eventName").val()!=""){
            customRequest(eventAttrName,{eventName:$("#eventName").val()},"attribute");
        }
    });
    $("#attribute").change(function () {
        if($("#eventName").val()!=null && $("#eventName").val()!="" && $("#attribute").val()!=null && $("#attribute").val()!=""){
            customRequest(eventAttrVal,{eventName:$("#eventName").val(),attrName:$("#attribute").val()},"attributeVal");
        }
    });
    function customRequest(url,data1,attribute){
        $.ajax({
            method: "post",
            url: url,
            contentType:'application/x-www-form-urlencoded',
            dataType:"json",
            data:data1,
            async:false,
            success: function(data){
                userAttr=data.attr?data.attr:[];
            },
            error:function () {
                alert("发生错误");
            }
        });
//获取select
        var attribute = document.getElementById(attribute);
//因为一会要用str来拼接，所以要先声明一下
        var str = '<option value="">空</option>';
// console.log(sel);看能否获取到select
//遍历循环，将每一项都拼接到str中
        for(var i=0;i<userAttr.length;i++){
//试着获取data里的每一个值看能否获取到
            console.log(userAttr[i]);
//拼接str，把data里面的所有值都放在option标签里
            str +='<option value='+userAttr[i]+' >'+userAttr[i]+'</option>';
        }
//把拼接好的str放到select标签里
        attribute.innerHTML = str;

    }

}

});
