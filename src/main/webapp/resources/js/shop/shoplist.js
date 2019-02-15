$(function () {
    getlist();
    function getlist(e) {
        $.ajax({
            url:"/o2o/shopadmin/getshoplist",
            type:"GET",
            dataType:"json",
            success:function (data) {
                if (data.success){
                    handleList(data.shopList);
                    handleUser(data.user);
                }
            }

        });
    }
    function handleUser(data) {
       $("#user-name").text(data.name);
    }

        function handleList(data) {
            var html='';
            data.map(function (itme,index) {
                html +='<div class="row row-shop"><div class="col-40">'
                    +itme.shopName+'</div><div class="col-40">'
                    +shopStatus(itme.enableStatus)
                    +'</div><div class="col-20">'
                    +goShop(itme.enableStatus,itme.shopId)
                    +'</div></div>';
            });
            $('.shop-wrap').html(html);
        }
        function shopStatus(status) {
            if (status == 0){
                return "审核中";
            }else if (status == -1){
                return "非法店铺";
            }else if (status == 1) {
                return "审核通过";
            }
        }
        function goShop(status,id) {
            if (status == 1){
                return '<a href="/o2o/shopadmin/shopmanagement?shopId='+id+'">进入</a>';
            }else {
                return "";
            }
        }

});