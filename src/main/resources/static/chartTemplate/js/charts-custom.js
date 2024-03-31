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
            var attrVal = $("#attributeVal").val();
            var dateType = $("#dateType").val();
            //针对属性分析的请求路径
            var attrUrl = $("#attribute").attr("name");
            var date1 = $("#date1").val();
            var date2 = $("#date2").val();
            if(date1=="" || date2   ==""){alert("请选择时间范围");return;}
            var data11 = null;

            if($("title").attr("id")=="component"){
                switch (requestUrl) {
                    case '/byComponentName':name="按元素名";break;
                    case '/byComponentType':name="按元素类型";break;
                    case '/byComponentId':name="按元素id";break;
                    default:break;
                }
                data11 = {pageTitle:attrName,date1:convertDateFromString(date1),date2:convertDateFromString(date2)};
                request(requestUrl,data11,success1);
            }else if($("title").attr("id")=="custom"){
                if(attrVal!=null && attrVal!=""){
                    data11 = {eventName:$("#eventName").val(),attrName:attrName,attrVal:attrVal,date1:convertDateFromString(date1),date2:convertDateFromString(date2),dateType:dateType};
                    request("/specificAttrVal",data11,success1);
                }else {
                    data11 = {eventName:$("#eventName").val(),attrName:attrName,date1:convertDateFromString(date1),date2:convertDateFromString(date2)}
                    request("/allAttrVal",data11,success1);
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
        buildT();
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
        buildT();
    }
});



