$(function () {
    var shopId=getQueryString("shopId");
    var isEdit=shopId?true:false;
    var initUrl='/o2o/shopadmin/getshopinitinfo';
    var registerShopUrl='/o2o/shopadmin/regitershop';
    var  shopInfoUrl='/o2o/shopadmin/getshopbyid?shopId='+shopId;
    var editShopUrl='/o2o/shop/modifyshop';
if (!isEdit){
    getShopInitInfo();
} else{
    getShopInfo(shopId);
}
    function getShopInfo(shopId) {
        $.getJSON(initUrl, function (data) {
            if (data.success) {
                var shop = data.shop;
                $('#shop-name').val(shop.shopName);
                $('#shop-addr').val(shop.shopAddr);
                $('#phone').val(shop.phone);
                $('#shop-desc').val(shop.shopDesc);

                var shopCategory = '<option data-id="' + shop.shopCategoryId + '"selected>' + shop.shopCategoryName + '</option>';
                var tempAreaHtml = '';
                data.areaList.map(function (item, index) {
                    tempAreaHtml = '<option data-id="' + itme.areaId + '">' + itme.areaName + '</option>';

                    $('#shop-category').html(tempHtml);
                    $('#area').html(tempAreaHtml);
                });
                $('#shop-category').html(shopCategory);
                $('#shop-category').attr('disabled', 'disabled');
                $('#area').html(tempAreaHtml);
                $('#area').attr('data-id', shop.areaId);
            }
        });
    }

    function getShopInitInfo() {
        $.getJSON(initUrl,function (data) {
            if (data.success){
                var tempHtml='';
                var tempAreaHtml='';
                data.shopCategoryServiceList.map(function (itme,index) {
                    tempHtml='<option data-id="'+itme.shopCategoryId+'">'+itme.shopCategoryName+'</option>';
                });
                data.areaServiceList.map(function (itme,index) {
                    tempAreaHtml='<option data-id="'+itme.areaId+'">'+itme.areaName+'</option>';
                });
                $('#shop-category').html(tempHtml);
                $('#area').html(tempAreaHtml);
            }
        });
        $('#submit').click(function () {
            var shop={};
            shop.shopName=$('#shop-name').val();
            shop.shopAddr=$('#shop-addr').val();
            shop.phone=$('#phone').val();
            shop.shopDesc=$('#shop-desc').val();
            shop.shopCategory={
                shopCategoryId:$('#shop-category').find('option').not(function () {
                    return !this.selected;
                }).data('id')
            };
            shop.area={
                areaId:$('#area').find('option').not(function () {
                    return !this.selected;
                }).data('id')
            };
            var shopImg=$('#shop-img')[0].files[0];
            var formData=new FormData();
            formData.append('shopImg',shopImg);
            formData.append('shopStr',JSON.stringify(shop));
            var verifyCodeActual=$('#j_captcha').val();
            if (!verifyCodeActual){
                $.toast("请输入验证码");
                return;
            }
            formData.append("verifyCodeActual", verifyCodeActual);
            $.ajax({
                url:(isEdit?editShopUrl:registerShopUrl),
                type:'POST',
                data:formData,
                contentType:false,
                processData:false,
                cache:false,

                success:function (data) {
                    if (data.success){
                        $.toast('提交成功！');
                    }else{
                        $.toast('提交失败！'+data.errMsg);
                    }
                    $('#captcha_img').click();
                }

            });
        });
    }
});