<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="keywords" content="jquery,ui,easy,easyui,web">
	<meta name="description" content="easyui help you build your web page easily!">
	<title>TestProject</title>
	<link rel="stylesheet" type="text/css" href="../jquery-easyui-1.5.3/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../jquery-easyui-1.5.3/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../jquery-easyui-1.5.3/demo/demo.css">
	<style type="text/css">
		#fm{
			margin:0;
			padding:10px 30px;
		}
		.ftitle{
			font-size:14px;
			font-weight:bold;
			color:#666;
			padding:5px 0;
			margin-bottom:10px;
			border-bottom:1px solid #ccc;
		}
		.fitem{
			margin-bottom:5px;
		}
		.fitem label{
			display:inline-block;
			width:125px;
		}
	</style>
	
</head>
<body>
	<h2>新家伙：SpringBoot + Spring Data JPA + Git + Idea（后续切换IDE）</h2>
	<div class="demo-info" style="margin-bottom:10px">
		<div class="demo-tip icon-tip">&nbsp;</div>
		<div>
			1: JDK8 
			2：Spring Boot 
			3：Spring Data JPA 
			4：Git 
		</div>
	</div>
	<table>
		<tr>
			<td>customer:</td>
			<td><input id="customer" name="customer"/></td>
			<td>demandType:</td>
			<td><input id="demandType" name="demandType"/></td>
			<td>model:</td>
			<td><input id="model" name="model"/></td>
			<td>
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="doSearch()">查询</a>
			</td>
	</table>
	<table id="dg" title="ExcleData" class="easyui-datagrid" >
	</table>
	<div id="toolbar">
		<a href="#" style="float:left" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">新增</a>
		<a href="#" style="float:left" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">修改</a>
		<a href="#" style="float:left" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeUser()">删除</a>
		
		<div id="#DivUp" style="float:left;margin-left:10px;">
                <div style="float:left">
                   <!--  
                   <label for="file">
                        <h5> 文件上传:</h5>
                    </label> 
                    -->
                    <input type="file" name="fileToUpload" id="fileToUpload" multiple="multiple" onchange="fileSelected();" />
                    <a id="uploadFile" style=" display:none" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="uploadFile()">上传并导入&nbsp;&nbsp;</a>
                </div>
        
                <div id="fileName" style="display:none;padding: 10px"></div>
                <div id="fileSize" style="display:none;padding: 10px"></div>
                <div id="fileType" style="display:none;padding: 10px"></div> 
                <div id="progressNumber" class="easyui-progressbar" style="width: 300px;"></div>
        </div>
	</div>
	
	<div id="dlg" class="easyui-dialog" style="width:500px;height:600px;padding:10px 20px" closed="true" buttons="#dlg-buttons">
		<div class="ftitle">User Information</div>
		<form id="fm" method="post" novalidate>
			<input name="id" type="hidden" />
			<div class="fitem">
				<label>需求类型:</label>
				<input name="demandType" class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>产品名称:</label>
				<input name="externalRackName" class="easyui-validatebox" required="true" data-options="formatter:myformatter,parser:myparser">
			</div>
			<div class="fitem">
				<label>机场编码:</label>
				<input name="cluster" class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>编号:</label>
				<input name="ipn" class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>needByDate:</label>
				<input name="needByDate" class="easyui-datebox" required="true" data-options="formatter:myformatter,parser:myparser">
			</div>
			<div class="fitem">
				<label>数量:</label>
				<input name="quantity" class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>vendor:</label>
				<input name="vendor" class="easyui-validatebox" >
			</div>
			<div class="fitem">
				<label>shippingRegion:</label>
				<input name="shippingRegion" class="easyui-validatebox" data-options="formatter:myformatter,parser:myparser">
			</div>
			<div class="fitem">
				<label>cluster1:</label>
				<input name="cluster1" class="easyui-validatebox" data-options="formatter:myformatter,parser:myparser">
			</div>
			<div class="fitem">
				<label>deliveryWindowStart:</label>
				<input name="deliveryWindowStart" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser">
			</div>
			<div class="fitem">
				<label>needByDateOld:</label>
				<input name="needByDateOld" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser">
			</div>
			<div class="fitem">
				<label>dateStamp:</label>
				<input name="dateStamp" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser">
			</div>
			<div class="fitem">
				<label>testRack:</label>
				<input name="testRack" class="easyui-validatebox" >
			</div>
			<div class="fitem">
				<label>region:</label>
				<input name="region" class="easyui-validatebox" >
			</div>
			<div class="fitem">
				<label>type:</label>
				<input name="type" class="easyui-validatebox" >
			</div>
			
		</form>
	</div>
	
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
</body>
<script type="text/javascript" src="../jquery-easyui-1.5.3/jquery.min.js"></script>
<script type="text/javascript" src="../jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../jquery-easyui-1.5.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
(function($){
    $.fn.serializeJson=function(){
        var serializeObj={};
        $(this.serializeArray()).each(function(){
            serializeObj[this.name]=this.value;
        });
        return serializeObj;
    };
})(jQuery);

function myformatter(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	return (m<10?('0'+m):m)+'/'+(d<10?('0'+d):d) + '/' + y;
}
function myparser(str){
	console.log(str);
	if (!str) return new Date();
		var ss = str.split('/');
		var m = parseInt(ss[0],10);
		var d = parseInt(ss[1],10);
		var y = parseInt(ss[2],10);
	if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
		return new Date(y,m-1,d);
	}else{
		return new Date();
	}
}

	var baseUrl="http://localhost:8080/api";
	var url;
	
	$(function(){
		$("#progressNumber").hide();
		
		//列表初始化
		$("#dg").datagrid({
			url:baseUrl + "/product",
			method:'GET',
			toolbar : '#toolbar',
			idField:"id",
			loadMsg : '正在努力为您加载..',
			rownumbers : true,
			singleSelect : true,
			//selectOnCheck: true,
			//checkOnSelect: true,
			pagination : true,
			pageSize : 20,
			pageList : [ 10, 20, 30, 40, 50 ],
			columns:[[
						{field:'id',title:'主键',hidden:true },
                        {field:'customer',title:'customer',align:'center'},
                        {field:'uploadTime',title:'上传时间',align:'center'
                        	,formatter : function(value){
                        		if(value){
                        			 var date = new Date(value);
     	                            var y = date.getFullYear();
     	                            var m = date.getMonth() + 1;
     	                            var d = date.getDate();
     	                            return m + '/' + d + '/' + y;
                        		}
	                        }
                        },
                        {field:'demandType',title:'需求类型',align:'center'},
						{field:'externalRackName',title:'产品名称model',align:'center'},
						{field:'cluster',title:'机场编码',align:'center'},
						{field:'ipn',title:'ipn',align:'center'},
						{field:'needByDate',title:'客户要求到货日期',align:'center'
							,formatter : function(value){
								if(value){
	                       			 var date = new Date(value);
	    	                            var y = date.getFullYear();
	    	                            var m = date.getMonth() + 1;
	    	                            var d = date.getDate();
	    	                            return m + '/' + d + '/' + y;
	                       		}
	                        }	
						},
						{field:'sunday',title:'sunday',align:'center'
							,formatter : function(value){
								if(value){
	                       			 var date = new Date(value);
	    	                            var y = date.getFullYear();
	    	                            var m = date.getMonth() + 1;
	    	                            var d = date.getDate();
	    	                            return m + '/' + d + '/' + y;
	                       		}
	                        }	
						},
						{field:'quantity',title:'数量',align:'center'},
						{field:'location',title:'location',align:'center'
							,formatter : function(value){
								if(!value){
	                       			return Math.random().toString(36).substr(2);
	                       		}
	                        }		
						},
						{field:'vendor',title:'vendor',align:'center'},
						{field:'shippingRegion',title:'shippingRegion',align:'center'},
						{field:'cluster1',title:'cluster1',align:'center'},
						{field:'deliveryWindowStart',title:'deliveryWindowStart',align:'center'
							,formatter : function(value){
								if(value){
	                       			 var date = new Date(value);
	    	                            var y = date.getFullYear();
	    	                            var m = date.getMonth() + 1;
	    	                            var d = date.getDate();
	    	                            return m + '/' + d + '/' + y;
	                       		}
	                        }		
						},
						{field:'needByDateOld',title:'needByDateOld',align:'center'
							,formatter : function(value){
								if(value){
	                       			 var date = new Date(value);
	    	                            var y = date.getFullYear();
	    	                            var m = date.getMonth() + 1;
	    	                            var d = date.getDate();
	    	                            return m + '/' + d + '/' + y;
	                       		}
	                        }		
						},
						{field:'dateStamp',title:'dateStamp',align:'center'
							,formatter : function(value){
								if(value){
	                       			 var date = new Date(value);
	    	                            var y = date.getFullYear();
	    	                            var m = date.getMonth() + 1;
	    	                            var d = date.getDate();
	    	                            return m + '/' + d + '/' + y;
	                       		}
	                        }		
						},
						{field:'testRack',title:'testRack',align:'center'},
						{field:'region',title:'region',align:'center'},
						{field:'type',title:'type',align:'center'},
			]]
			
		});
	});
	
	function doSearch(){
		var customer=$("#customer").val();
		var demandType=$("#demandType").val();
		var model=$("#model").val();
		
		$('#dg').datagrid('load',{
			customer: customer,
			demandType: demandType,
			externalRackName: model
		});
		
		/* var data = {"customer": customer, "demandType":demandType, "externalRackName":model};
		
		$.get(baseUrl + "/product", data, function(res){
			var total = res.totalElements;
            var resdata = res.data.content;
			
            var datasource = { total: total, rows: resdata };
            datasource = JSON.parse(datasource);
            $("#dg").datagrid('loadData', datasource);//TODO:cq...
		}); */
		
	}
	
	function newUser(){
		$('#dlg').dialog('open').dialog('setTitle','新增');
		$('#fm').form('clear');
		//url = 'save_user.php';
	}
	
	function editUser(){
		var row = $('#dg').datagrid('getSelected');
		if (row){
			$('#dlg').dialog('open').dialog('setTitle','修改');
			$('#fm').form('load',row);//填充数据
		}
	}
	
	function saveUser(){
		var flag = $("#fm").form('validate');
		if(!flag){
			return;
		}
		var formData = $("#fm").serializeJson();
		var methodType = 'POST';
		if(formData.id){
			methodType = 'PUT';
			formData.id = Number(formData.id);
		}
		$.ajax({
			url: baseUrl + '/product',
			type: methodType,
			contentType:"application/json",
			datatype:"json",
			data: JSON.stringify(formData),
			success: function(result) {
				$('#dlg').dialog('close');		// close the dialog
				$('#dg').datagrid('reload');	// reload the user data
			},
			error: function(){
				$.messager.show({
					title: 'Error',
					msg: '网络问题，请稍后再试！'
				});
			}
		});
	}
	
	function removeUser(){
		var row = $('#dg').datagrid('getSelected');
		if (row){
			$.messager.confirm('Confirm','你确定要删除?',function(r){
				if (r){
					$.ajax({
						url: baseUrl + '/product/'+ row.id,
						type: 'DELETE',
						success: function(result) {
							$('#dg').datagrid('reload');	// reload the user data
						},
						error: function(){
							$.messager.show({	// show error message
								title: 'Error',
								msg: '网络问题，请稍后再试！'
							});
						}
					});
				}
			});
		}
	}
</script>
<script language="javascript" type="text/javascript">
    function fileSelected() {
        var file = document.getElementById('fileToUpload').files[0];
        var fileName = file.name;
        var file_typename = fileName.substring(fileName.lastIndexOf('.'), fileName.length);
    
        if (file_typename == '.xls' || file_typename == '.xlsx') {//这里限定上传文件文件类型
            if (file) {
                $("#uploadFile").show();
                var fileSize = 0;
                if (file.size > 1024 * 1024){
                    fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
                }else{
                    fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';
                }
                document.getElementById('fileName').innerHTML = '文件名: ' + file.name;
                document.getElementById('fileSize').innerHTML = '大小: ' + fileSize;
                document.getElementById('fileType').innerHTML = '类型: ' + file.type;
                
                
            }
        }
        else {
            $("#uploadFile").hide();
            
            var msg = "<span style='color:Red'>错误提示:上传文件应该是.xls或者.xlsx后缀而不应该是" + file_typename + ",请重新选择文件</span>";
            document.getElementById('fileName').innerHTML = msg;
            //$("#fileName").show();
            $.messager.show({	
            	showType: null,
				style: {},
				title: 'Error',
				msg: msg
			});
            document.getElementById('fileSize').innerHTML ="";
            document.getElementById('fileType').innerHTML ="";
        }
    }

    function uploadFile() {
        var fd = new FormData();
        fd.append("fileToUpload", document.getElementById('fileToUpload').files[0]);
        var xhr = new XMLHttpRequest();
        xhr.upload.addEventListener("progress", uploadProgress, false);
        xhr.addEventListener("load", uploadComplete, false);
        xhr.addEventListener("error", uploadFailed, false);
        xhr.addEventListener("abort", uploadCanceled, false);
        xhr.open("POST", "/api/product/import");
        xhr.send(fd);
        
        $("#progressNumber").progressbar("setValue", 0);
        $("#progressNumber").show();
    }

    function uploadProgress(evt) {
        if (evt.lengthComputable) {
            //var percentComplete = Math.round(evt.loaded * 100 / evt.total);
            //$('#progressNumber').progressbar('setValue', percentComplete);
        	startBar();
        }
        else {
            document.getElementById('progressNumber').innerHTML = '无法计算';
        }
    }

    function uploadComplete(evt) {
        /* 服务器返回数据*/
        var message = evt.target.responseText;
        console.log("uploadComplete",message);
        message = JSON.parse(message);
        if(message.data!=null){
        	var error = "";
        	for(var i=0;i<message.data.length;i++){
        		error = error + "第"+message.data[i].lineNum+"行，"+message.data[i].lineErrorMsg + "<br/>";
        	}
        	$.messager.show({	// show error message
				title: '错误提示',
				width: 500,
				height: '100%',
				showType: null,
				style: {
					left:'',  
			        right:0,  
			        top:document.body.scrollTop+document.documentElement.scrollTop,  
			        bottom:''
				},
				timeout: 0,
				msg: error
			});
        }
        else{
        	$('#progressNumber').progressbar('setValue', 100);
        	alert("导入成功");
        	
        	doSearch();//刷新数据
        	$('#dg').datagrid('reload');	// reload the user data
        	$("#uploadFile").hide();
        	$("#progressNumber").fadeOut();
        	$("#fileToUpload").val('');
        }
    }

    function uploadFailed(evt) {
        alert("上传出错.");
        console.log("uploadFailed",evt);
    }

    function uploadCanceled(evt) {
        alert("上传已由用户或浏览器取消删除连接.");
        console.log("uploadCanceled",evt);
    }
</script>

<script>
    var t = 0, time_id = null;
    $("#progressNumber").progressbar({
        //width:300,
        //height:25,
        value:0,
        onChange:function(newValue, oldValue) { 
            if (newValue >= 90) {
                //如果进度到了100%，清除定时器，结束过程
                window.clearInterval(time_id);
                time_id = null;
            }
        }
    });

    function addProgressBar() {
        // 设置进度条显示的数值
        t = t + Math.floor(Math.random() * 10) + 5;
        $("#progressNumber").progressbar("setValue", t);
    }

    function startBar() {
        // 建立定时器，每隔1秒调用addProgressBar方法
        time_id = window.setInterval(addProgressBar, 1000);
    }
</script>
</html>