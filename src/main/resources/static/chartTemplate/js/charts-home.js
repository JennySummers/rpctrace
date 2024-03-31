/*global $, document, Chart, LINECHART, data, options, window*/
/**
 * 图一横纵坐标
 * @type {Array}
 */
var xArray1 = [];
var yArray1 = [];

/**
 * 图二横纵坐标
 * @type {Array}
 */
var xArray2 = [];
var yArray2 = [];

/**
 * 图三横纵坐标
 * @type {Array}
 */
var xArray3 = [];
var yArray3 = [];

var barChartHome = null; //柱状图
var myLineChart = null; //折线图

var name = "";

$(document).ready(function () {

    'use strict';

    // ------------------------------------------------------- //
    // Line Chart
    // ------------------------------------------------------ //
    var legendState = true;
    if ($(window).outerWidth() < 576) {
        legendState = false;
    }
    function convertDateFromString(dateString) {
        if (dateString) {
            var arr1 = dateString.split(" ");
            var sdate = arr1[0].split('-');
            var date = new Date(sdate[0], sdate[1]-1, sdate[2]);
            return date;
        }
    }

function myLineChart1() {
    var LINECHART = $('#lineChart');
    myLineChart = new Chart(LINECHART, {
        type: 'line',
        options: {
            scales: {
                xAxes: [{
                    display: true,
                    gridLines: {
                        display: false
                    }
                }],
                yAxes: [{
                    display: true,
                    ticks:{
                        beginAtZero:true
                    },
                    gridLines: {
                        display: false
                    }
                }]
            },
            legend: {
                display: legendState
            }
        },
        data: {
            labels: xArray1,
            // ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17"],
            datasets: [
                {
                    label: name,
                    fill: true,
                    lineTension: 0,
                    backgroundColor: "transparent",
                    borderColor: '#f15765',
                    pointBorderColor: '#da4c59',
                    pointHoverBackgroundColor: '#da4c59',
                    borderCapStyle: 'butt',
                    borderDash: [],
                    borderDashOffset: 0.0,
                    borderJoinStyle: 'miter',
                    borderWidth: 1,
                    pointBackgroundColor: "#fff",
                    pointBorderWidth: 1,
                    pointHoverRadius: 5,
                    pointHoverBorderColor: "#fff",
                    pointHoverBorderWidth: 2,
                    pointRadius: 1,
                    pointHitRadius: 0,
                    data: yArray1,
                    // [50, 20, 60, 31, 52, 22, 40, 25, 30, 68, 56, 40, 60, 43, 55, 39, 47],
                    spanGaps: false
                }
                // {
                //     label: "Page Views",
                //     fill: true,
                //     lineTension: 0,
                //     backgroundColor: "transparent",
                //     borderColor: "#54e69d",
                //     pointHoverBackgroundColor: "#44c384",
                //     borderCapStyle: 'butt',
                //     borderDash: [],
                //     borderDashOffset: 0.0,
                //     borderJoinStyle: 'miter',
                //     borderWidth: 1,
                //     pointBorderColor: "#44c384",
                //     pointBackgroundColor: "#fff",
                //     pointBorderWidth: 1,
                //     pointHoverRadius: 5,
                //     pointHoverBorderColor: "#fff",
                //     pointHoverBorderWidth: 2,
                //     pointRadius: 1,
                //     pointHitRadius: 10,
                //     data: [20, 7, 35, 17, 26, 8, 18, 10, 14, 46, 30, 30, 14, 28, 17, 25, 17, 40],
                //     spanGaps: false
                // }
            ]
        }
    });
}


$(function(){
        $('#query').click(function(){
            if($("#loginFlag").text()=="Login"){
                alert("请先登录");
                return;
            }
            var requestUrl = $("#eventName").val();
            var attrName = $("#attribute").val();
            var dateType = $("#dateType").val();
            //针对属性分析的请求路径
            var attrUrl = $("#attribute").attr("name");
            var date1 = $("#date1").val();
            var date2 = $("#date2").val();
            if(date1=="" || date2==""){alert("请选择时间范围");return;}
            var data11 = {dateType:dateType,pageTitle:attrName,date1:convertDateFromString(date1),date2:convertDateFromString(date2)};
            /**
             * 动态标签
             */
            if($("title").attr("id")=='user'){
                switch (requestUrl) {
                    case "/newUser":name='新增用户';break;
                    case "/activeUser":name='活跃用户';break;
                    case "/silenceUser":name='沉默用户';break;
                    case "/loginTime":name='登录时间分析';break;
                    default:break;
                }
            }else if($("title").attr("id")=='page') {
                switch (requestUrl) {
                    case "/pageViewCount":
                        name = '页面点击量';
                        switch (dateType) {
                            case "0":$(".byDay").text("按日");break;
                            case "1":$(".byWeek").text("按周");break;
                            case "2":$(".byMonth").text("按月");break;
                            case "3":$(".byYear").text("按年");break;
                            default:break;
                        }
                        break;
                    case "/pageVisitorCount":
                        name = '页面访问人数';
                        switch (dateType) {
                            case "0":$(".byDay").text("按日");break;
                            case "1":$(".byWeek").text("按周");break;
                            case "2":$(".byMonth").text("按月");break;
                            case "3":$(".byYear").text("按年");break;
                            default:break;
                        }
                        break;
                    case "/aveViewTime":
                        name = '页面平均访问时间';
                        switch (dateType) {
                            case "0":$(".byDay").text("按日（/秒）");break;
                            case "1":$(".byWeek").text("按周（/分）");break;
                            case "2":$(".byMonth").text("按月（/小时）");break;
                            case "3":$(".byYear").text("按年（/小时）");break;
                            default:break;
                        }
                        break;
                    case "/pageRedirect":
                        name = '页面跳转分析';
                        switch (dateType) {
                            case "0":$(".byDay").text("按日");break;
                            case "1":$(".byWeek").text("按周");break;
                            case "2":$(".byMonth").text("按月");break;
                            case "3":$(".byYear").text("按年");break;
                            default:break;
                        }
                        break;
                    default:
                        break;
                }
            }

            request(requestUrl,data11,success1);
            if(attrName!=null && attrName!=""){
                //查询的用户集合的标志
                var userType = 0;
                var data22;
                if($("title").attr("id")=="user"){
                    switch (requestUrl) {
                        case "/newUser":userType=1;break;
                        case "/activeUser":userType=2;break;
                        case "/silenceUser":userType=3;break;
                        case "/loginTime":userType=2;break;
                        default:break;
                    }
                    data22 = {attributeName:attrName,date1:convertDateFromString(date1),date2:convertDateFromString(date2),type:userType};
                    request(attrUrl,data22,success2);
                }else if($("title").attr("id")=="page"){
                    switch (requestUrl) {
                        case "/pageViewCount":userType=1;break;
                        case "/pageVisitorCount":userType=2;break;
                        case "/aveViewTime":userType=3;break;
                        case "/pageRedirect":userType=2;break;
                        default:break;
                    }
                    data22 = {dateType:dateType,page:attrName,date1:convertDateFromString(date1),date2:convertDateFromString(date2),type:userType};
                    request(attrUrl,data22,success2);
                }

            }
        });
    });

function request(url, data,successFun) {
    $.ajax({
        method: "post",
        url: url,
        data: data,
        contentType:'application/x-www-form-urlencoded',
        dataType:"json",
        async:false,
        success: function (data) {
            successFun(data);
        },
        error:function () {
            alert("发生错误");
        }
    });

}

function myChart2() {
    console.log("柱状图")
    var BARCHARTHOME = $('#barChartHome');
    barChartHome = new Chart(BARCHARTHOME, {
        type: 'bar',
        options: {
            scales: {
                xAxes: [{
                    display: true,
                    gridLines: {
                        display: false
                    }
                }],
                yAxes: [{
                    display: true,
                    ticks:{
                        beginAtZero:true
                    },
                    gridLines: {
                        display: false
                    }
                }]
            },
            legend: {
                display: false
            }
        },
        data: {
            labels:xArray2,
            datasets : [
                {
                    fillColor : "#f15765",
                    strokeColor : "#f15765",
                    data : yArray2
                }
            ]
        }
    });
    console.log("柱状图")
}

    /**
     * 折线图数据获取成功事件，绘制折线图
     * @param data
     */
    function success1(data) {
        xArray1=data.xArray?data.xArray:[];
        yArray1=data.yArray?data.yArray:[];
        if(myLineChart) myLineChart.destroy();
        myLineChart1();
        $("#table1").html("");
        buildT1();
    }

    /**
     * 柱状图数据获取成功事件，绘制柱状图
     * @param data
     */
    function success2(data) {
        xArray2=data.xArray?data.xArray:[];
        yArray2=data.yArray?data.yArray:[];
        if(barChartHome) barChartHome.destroy();
        myChart2();
        buildT2();
    }
});



