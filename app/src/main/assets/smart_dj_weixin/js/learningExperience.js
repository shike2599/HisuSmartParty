$(function(){
    //点击添加图片
    $('.immediate span').click(function(){
        //console.log('aaaa');
        wx.chooseImage({
            count: 9, // 默认9
            sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
            sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
            success: function (res) {
                images.localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
                //wx.previewImage();
                var i = 0;
                var length = images.localIds.length;
                var upload = function() {
                    wx.uploadImage({
                        localId:images.localIds[i],
                        success: function(res) {
                            // alert(res.serverId+'----res.serverId');
                            images.serverId.push(res.serverId);
                            imagesArr.push(res.serverId);
                            sessionStorage.setItem('learningFileImg',imagesArr);
                            //alert(imagesArr+'----imagesArr');
                            //如果还有照片，继续上传
                            i++;
                            if (i < length) {
                                upload();
                            }
                        }
                    });
                };

                upload();
                //目前因缺少学习列表页

            }
        });
    });

})