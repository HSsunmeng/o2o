$(function () {
    //获取此店铺下商品列表的URL
    var listUrl='/o2o/shopadmin/getproductlistbyshop?pageIndex=1&pageSize=100';
    //商品下架的url
    var statusUrl='/o2o/shopadmin/modifyproduct';
    /*
    * 获取此店铺下的商品列表
    * */
    getList();
    function getList() {
        $.getJSON(listUrl,function (data) {
           if (data.success){
               var productList=data.productList;
               var tempHtml='';
               //遍历每条商品信息，拼接成一行显示，
               productList.map(function (item,index) {
                   var textOp='下架';
                   var contraryStatus=0;
                   if (item.enableStatus==0){
                       textOp='上架';
                       contraryStatus=1;
                   }else{
                       contraryStatus=0;
                   }
                   tempHtml+=''+'<div class="row row-product">'
                   +'<div class="col-33">'
                   +item.productName
                   +'</div>'
                   +'<div class="col-20">'
                   +item.priority
                   +'</div>'
                   +'<div class="col-40">'
                   +'<a href="#" class="edit" data-id="'
                   +item.productId
                   +'"data-status="'
                   +item.enableStatus
                   +'">编辑</a>'
                   +'<a href="#" class="status" data-id="'
                   +item.productId
                   +'"data-status="'
                   +contraryStatus
                   +'">'
                   +textOp
                   +'</a>'
                   +'<a href="#" class="preview" data-id="'
                   +item.productId
                   +'"data-status="'
                   +item.enableStatus
                   +'">预览</a>'
                   +'</div>'
                   +'</div>'
               });
               $('.product-wrap').html(tempHtml);
           }
        });
    }
    $('.product-wrap').on('click','a',function (e) {
        var target=$(e.currentTarget);
        if (target.hasClass('edit')){
            window.location.href='/o2o/shopadmin/product?productId='+e.currentTarget.dataset.id;
        }else if (target.hasClass('status')) {
            changeItemStatus(e.currentTarget.dataset.id,e.currentTarget.dataset.status);
        }else if (target.hasClass('preview')){
            window.location.href='/o2o/shopadmin/?productId='+e.currentTarget.dataset.id;
        }
    });
    function changeItemStatus(id,enableStatus) {
        var product={};
        product.productId=id;
        product.enableStatus=enableStatus;
        $.confirm('确定马？',function () {
            $.ajax({
                url:statusUrl,
                type:'POST',
                data:{
                    productStr:JSON.stringify(product),
                    statusChange:true
                },
                dataType:'json',
                success:function (data) {
                    if (data.success){
                        $.toast('操作成功');
                        getList();
                    } else {
                        $.toast('操作失败');
                    }
                }

            });
        });
    }
});