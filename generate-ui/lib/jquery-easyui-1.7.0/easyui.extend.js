// 重写datagrid属性
$.extend($.fn.datagrid.defaults,{
    rownumbers: true,
    pagination: true,
    pageSize: 20,
    pageList: [20,40,60,80,100,120,140,160,180,200],
    onLoadSuccess: function(data){
        $(this).datagrid("clearSelections");
        $(this).datagrid("clearChecked");
    },
    onLoadError: function () {
        $.msg("加载数据失败！",false);
    }
});


