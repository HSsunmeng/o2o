$(function () {
   var productId=getQueryString('productId');
   var infoUrl='/o2o/shopadmin/getproductbyid?productId='+productId;
   var categoryUrl='/o2o/shopadmin/getproductcategorylist';
   var productPostUrl='/o2o/shopadmin/modifyproduct';
   var isEdit=false;
   if (productId){
       getInfo(productId);
       isEdit=true;
   }else {
       getCategory();
       productPostUrl='/o2o/shopadmin/addproduct';
   }
   function getInfo(id) {
       $.getJSON(infoUrl,function (data) {
           if (data.success){
               var product = data.product;
               $('#product-name').val(product.productName);
               $('#product-desc').val(product.productDesc);
               $('#priority').val(product.priority);
               $('#normal-price').val(product.normalPrice);
               $('#promotion-price').val(product.promationPrice);
               var optionHtml='';
               var optionArr=data.productCategoryList;
               var optionSelected = product.productCategory.productCategoryId;
               optionArr.map(function (item,index) {
                   var isSelect=optionSeletced===item.productCategoryId?'selected':'';
                   optionHtml+='<option data-value="'
                       +item.productCategoryId
                        +'"'
                   +isSelect
                   +'>'
                   +item.productCategoryName
                   +'</option>';
               });
               $('#category').html(optionHtml);
           }
       });
   }
   function getCategory() {
       $.getJSON(categoryUrl,function (data) {
           if (data.success){
               var productCategoryList=data.data;
               var optionHtml='';
               productCategoryList.map(function (item,index) {
                   optionHtml+='<option data-value="'
                   +item.productCategoryId
                   +'">'
                   +item.productCategoryName
                   +'</option>';


               });
               $('#category').html(optionHtml);
           }
       });
   }
   $('.detail-img-div').on('change','.detail-img:last-child',function () {
       if ($('.detail-img').length < 6){
           $('#detail-img').append('<input type="file" class="detail-img">');
       }
   });
   $('#submit').click(function () {
       var product={};
       product.productName=$('#product-name').val();
       product.productDesc=$('#product-desc').val();
       product.priority=$('#priority').val();
       product.normalPrice=$('#normal-price').val();
        product.promationPrice=$('#promotion-price').val();
        product.productCategory={
            productCategoryId:$('#category').find('option').not(function () {
                return !this.selected;
            }).data('value')
        }
        product.productId=productId;
        var  thumbnail=$('#small-img')[0].files[0];
        var formDate=new FormData();
        formDate.append("thumbnail",thumbnail);
        $('.detail-img').map(function (index,item) {
            if ($('.detail-img')[index].files.length >0){
                formDate.append('productImg'+index,$('.detail-img')[index].files[0]);
            }
        });
        formDate.append('productStr',JSON.stringify(product));
        var verifyCodeActual=$('#j_captcha').val();
        if (!verifyCodeActual){
            $.toast('请输入验证码');
            return;
        }
        formDate.append("verifyCodeActual",verifyCodeActual);
        $.ajax({
            url:productPostUrl,
            type:'POST',
            data:formDate,
            contentType:false,
            processData:false,
            cache:false,
            success:function (data) {
                if (data.success){
                    $.toast('提交成功');
                    $("#captcha_img").click();
                }else {
                    $.toast('提交失败');
                    $("#captcha_img").click();
                }
            }
        })
   });
});