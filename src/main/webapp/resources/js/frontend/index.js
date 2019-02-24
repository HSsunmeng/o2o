$(function () {
    var url='/o2o/frontend/listmainpageinfo';
    $.getJSON(url,function (data) {
        if (data.success){
            var headLineList=data.headLineList;
            var swiperHtml="";
            headLineList.map(function (item,index) {
                swiperHtml +=''+'<div class="swiper-slide img-wrap">'
                +'<a href="'+item.lineLink
                +'"external><img class="banner-img" src="'+item.lineImg
                    +'"alt="'+item.lineName+'"></a>'+'</div>';
            });
            $('.swiper-wrapper').html(swiperHtml);
            $('.swiper-container').swiper({
                //设定轮播图转换时间为3秒
                autoplay:3000,
                //用户对轮播图进行操作时，是否停止轮播autoplay
                autoplayDisableOnInteraction:true
            });
            //获取后台传过来的打大类列表
            var shopCategoryList=data.shopCategoryList;
            var categoryHtml='';
            shopCategoryList.map(function (item,index) {
                categoryHtml +=''
                +'<div class="col-50 shop-classify"data-category='
                +item.shopCategoryId+'>'+'<div class="word">'
                +'<p class="shop-title">'+item.shopCategoryName
                +'</p>'+'<p class="shop-desc">'
                +item.shopCategoryDesc +'</p>'+'</div>'
                +'<div class="shop-classify-img-warp">'
                +'<img class="shop-img" src="'+item.shopCategoryImg
                +'">'+'</div>'+'</div>';
            });
            $('.row').html(categoryHtml);
        }
    });
    $('#me').click(function () {
        $.openPanel('#panel-right-demo');
    });
    $('.row').on('click','.shop-classify',function (e) {
        var shopCategoryId=e.currentTarget.dataset.category;
        var newUrl='/o2o/frontend/shoplist?parentId='+shopCategoryId;
        window.location.href=newUrl;
    });
});