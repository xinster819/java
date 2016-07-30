/**
 * @author 郭豪
 * @description v2版编辑页
 * @date 2016/07/22
 */
(function () {
    // 首屏loading效果
    window.setTimeout(function () {
        $('.global_loading_w').transition({
            opacity: 0,
            duration: 500,
            easing: 'in-out',
            complete: function() {
                $('.global_loading_w').remove();
            }
        });
    }, 500);
    // 禁用触摸事件
    $(document).on('touchmove', function (e) {e.preventDefault(); return false; }, false)
    document.addEventListener('touchmove', function (e) {e.preventDefault(); return false; }, false);
}());

var util = {
    toast: function (text, type, num, cb) {
        var $toast = $('<div class="global-toast">'+text+'</div>');
        $toast.css('visibility', 'hidden');
        $('body').append($toast);
        $toast.css({
            'margin-left': -$toast.outerWidth() / 2,
        }).css(type, num).show().css('visibility', 'visible')
        .css('margin-' + type, -$toast.outerHeight() / 2);
        $toast.transition({
            scale: 1, opacity: 1,
            duration: 300,
            easing: 'in-out',
            complete: function() {
                window.setTimeout(function () {
                    $toast.transition({
                        scale: 0.5,opacity: 0,
                        duration: 300,
                        easing: 'in-out',
                        complete: function() {
                            $toast.remove();
                            if ($.isFunction(cb)) {
                                cb();
                            }
                        }
                    });
                }, 1000);
            }
        });
    },
    // 编辑缓存处理
    editCache: {
        getCache: function () {
            var cache = window.localStorage.getItem('sohu_mp_mobile_edit_cache');
            if (cache === null || cache ===  void 0) {
                return {
                    text: '',
                    media: []
                };
            } else {
                return JSON.parse(window.localStorage.getItem('sohu_mp_mobile_edit_cache')); 
            }
        },
        removeCacheItem: function (type, content) {
            var cache = this.getCache(), i, item;

            for (i = 0; i < cache.media.length; i ++) {
                item = cache.media[i];
                if (item.type === type) {
                    if (type === 'image' && item.url === content) {
                        cache.media.splice(i, 1);
                    }

                    if (type === 'video' && item.id === content) {
                        cache.media.splice(i, 1);
                    }
                }
            }
            window.localStorage.setItem('sohu_mp_mobile_edit_cache', JSON.stringify(cache));
        },
        setCache: function (type, content) {
            var cache = this.getCache(), item;
            if (type === 'text') {
                cache.text = content;
            }

            if (type === 'image') {
                item = {
                    type: 'image',
                    url: content
                };
                cache.media.push(item)
            }

            if (type === 'video') {
                item = {
                    type: 'video',
                    id: content
                };
                cache.media.push(item)
            }
            window.localStorage.setItem('sohu_mp_mobile_edit_cache', JSON.stringify(cache));
        },
        removeCache: function () {
            window.localStorage.removeItem('sohu_mp_mobile_draft');
            window.localStorage.removeItem('sohu_mp_mobile_edit_cache');
        },
        show: function (imageObj, videoPreView) {
            // 渲染缓存内容
            var $submit = $('div[node-type="box-submit"]'),
                $textarea = $('textarea[node-type="box-textarea"]');
                $picWrapper = $('.pics-w-ul');
            var cache = this.getCache();

            var draft = window.localStorage.getItem("sohu_mp_mobile_draft");
            draft = JSON.parse(draft);

            if (!cache) {
                return;
            }

            // 显示缓存的文本
            if (cache.text.length > 0) {
                $textarea.val(cache.text);
                $submit.removeClass('disable');
            }

            // 显示缓存的媒体
            cache.media.forEach(function (item) {
                if (item.type === 'image') {
                    imageObj.preView(imageObj.preloading(), item.url);
                    $picWrapper.show();
                }

                if (item.type === 'video') {
                    videoPreView(item.id);
                    $picWrapper.show();
                }

            });

        }
    }
};

$(function () {
    var $submit = $('div[node-type="box-submit"]'),
        $textarea = $('textarea[node-type="box-textarea"]');
        $picWrapper = $('.pics-w-ul');


    // icon触摸高亮
    (function () {
        
        $('.pic-input').on('touchstart', function () {
            $('.pic').addClass('active');
        });

        $('.pic-input').on('touchend', function () {
            $('.pic').removeClass('active');
        });

        $('.video-input').on('touchstart', function () {
            $('.video').addClass('active');
        });

        $('.video-input').on('touchend', function () {
            $('.video').removeClass('active');
        });
        
        
    }());

    // 文本区域滚动逻辑
    // (function () {
    //     var eventData = {};
    //     $textarea.on('touchstart', function (e) {
    //         var event = e.originalEvent.changedTouches ? e.originalEvent.changedTouches[0] : e;
    //         eventData.startY = event.pageY;
    //     });

    //     $textarea.on('touchmove', function (e) {
    //         var event = e.originalEvent.changedTouches ? e.originalEvent.changedTouches[0] : e;

    //         var startY = eventData.startY, scorll = $textarea.scrollTop(), scrollHeight = $textarea[0].scrollHeight - $textarea.innerHeight(),
    //             diff = scorll + (startY - event.pageY);
    //         console.log(startY - event.pageY);
    //         $textarea.scrollTop(diff);
    //     });

    //     $textarea.on('touchend', function (e) {
    //         var event = e.originalEvent.changedTouches ? e.originalEvent.changedTouches[0] : e;
    //     });
    // }());

    // 文本区域滚动逻辑
    (function () {
        var eventData = {};
        $textarea.on('touchstart', function (e) {
            var event = e.originalEvent.changedTouches ? e.originalEvent.changedTouches[0] : e;
            eventData.startY = event.pageY;
        });

        $textarea.on('touchmove', function (e) {
            e.stopPropagation();
            var event = e.originalEvent.changedTouches ? e.originalEvent.changedTouches[0] : e;
            var scorll = $textarea.scrollTop(), scrollHeight = $textarea[0].scrollHeight - $textarea.innerHeight();
            var startY = eventData.startY;
            if (scorll <= 10 && (startY - event.pageY) <= 0) {
                e.preventDefault();
                return false;
            }

            if (scorll >= scrollHeight && (startY - event.pageY) >= 0) {
                e.preventDefault();
                return false; 
            }
        });

    }());

    // 图片loading效果
    var imageObj = {
        preloading: function () {
            var $itemTpl = $('<li class="pics-w-li">\
                <div class="load-container loading">\
                    <div class="loader active"></div>\
                </div>\
            </li>');
            $picWrapper.show();
            $picWrapper.append($itemTpl);
            return $itemTpl;
        },
        preView: function ($itemTpl, url) {
            var $img = $('<img class="pics-w-img" src="' + url + '" alt="">')
            $img.on('load', function () {
                $itemTpl.find('.load-container').remove();
                $itemTpl.append($img);
                $submit.removeClass('disable');
                $itemTpl.append('<div class="delete"></div>');
            });
        }
    };

    // 视频上传成功后
    var videoPreView = function (id) {
        var $itemTpl = $('<li data-id="' + id + '" class="pics-w-li videoWrapper">\
            <div class="pics-w-vidio"></div>\
        </li>');
        $picWrapper.append($itemTpl);
        var width = $itemTpl.width(),
            height = $itemTpl.height();
        var videoHTML = createSHPlayer({
            channelSrc: 11050001,
            bid: id,
            width: width,
            height: height,
            autoplay: false,
            enforceHTML5:  true,
            getHTML: true,
        });
        $itemTpl.find('.pics-w-vidio').append(videoHTML);
        $itemTpl.append('<div class="press"></div>');
        $itemTpl.append('<div class="delete"></div>');
    };

    util.editCache.show(imageObj, videoPreView);
    // 上传图片
    var uploadFile = function () {
        var postUrl = "1";
        var $itemLoader = imageObj.preloading();
        var options = {
            type: "POST",
            url: postUrl,
            success: function (data) {
                // $(".loading").hide();
                if (data == "") {
                    util.toast('图片上传出错啦,请重传~', 'top', '50%');
                    $itemLoader.remove();
                }
                // 过滤空格回车
                var str = data.replace(/(\s|\t|\n)*/ig, '');
                if (/^successed/i.test(str)) {
                    var arr = str.split(',');
                    if (arr.length > 1) {
                        $picWrapper.show();
                        imageObj.preView($itemLoader, arr[1]);
                        util.editCache.setCache('image', arr[1]);
                    }
                } else {
                    $itemLoader.remove();
                    if (/^failed/i.test(str)) {
                        var arr = str.split(',');
                        if (arr.length > 1) {
                            util.toast(decodeURIComponent(arr[1]), 'top', '50%');
                        } else {
                            util.toast('图片上传出错啦,请重传~', 'top', '50%');
                        }
                    } else {
                        util.toast('图片上传出错啦,请重传~', 'top', '50%');
                    }
                }
                
            },
            error: function (data, status, e) {
                util.toast('图片上传出错啦,请重传~', 'top', '50%');
                $itemLoader.remove();
            }
        };
        $("#cyImageForm").ajaxSubmit(options);
    };

    // 输入监控，如果输入为空并且没有图片和视频
    $textarea.on('input', function () {
        var $this = $(this), inputLen = $this.val().length;
        util.editCache.setCache('text', $this.val());
        if (inputLen === 0 && $('.pics-w-li').length === 0) {
            $submit.addClass('disable');
        } else {
            $submit.removeClass('disable');
        }
    });

    // 图片上传触发
    $('.pic-input-w').on('change', function (event) {
        uploadFile();
    });

    // 视频上传
    var conf = {
        btn : "#video",
        onprogress : function(progress, speed, uploaded, lasttime){
            console.log(progress)
        },
        onerror : function(ev){
            console.log(ev)
        },
        onsuccess: function(data){
            // 上传成功回调
            $picWrapper.show();
            videoPreView(data.id);
            util.editCache.setCache('video', data.id);
        }
    };

    new _html5Upload(conf).init();

    // 点击发布按钮
    $submit.on('tap', function () {
        var content = '', cache = util.editCache.getCache();

        // 拼接发布字符串
        if (cache.text.length > 0) {
            content += '<p>' + cache.text + '</p>';
        }

        cache.media.forEach(function (item) {
            if (item.type === 'image') {
                content += '<img src="' + item.url + '">';
            }

            if (item.type === 'video') {
                content += "<embed bid='"+item.id+"' id='player1469422924741' pluginspage='http://www.macromedia.com/go/getflashplayer' type='application/x-shockwave-flash' width='300' height='300' src='http://tv.sohu.com/upload/swf/20160721/Main.swf' quality='high' bgcolor='#000000' allowfullscreen='true' allowscriptaccess='always' wmode='transparent' flashvars='&amp;oad=&amp;ead=&amp;pad=&amp;fbarad=&amp;flogoad=&amp;flogodelay=&amp;tlogoad=&amp;tlogodelay=&amp;fbottomad=&amp;fbottomdelay=&amp;ftitlead=&amp;ftitledelay=&amp;domain=inner&amp;sogouBtn=0&amp;skin=0&amp;api_key=&amp;enforceMP4=&amp;xuid=&amp;recommend=&amp;showRecommend=&amp;playercover=&amp;id="+item.id+"&amp;autoplay=false&amp;pageurl=http://mp.sohu.com/main/video.html&amp;showCtrlBar=0&amp;jump=0&amp;sid=1607111402445770&amp;enforceHTML5=false&amp;uuid=dcd88c87-b164-c2ac-2143-206e73c65151&amp;topBarFull=1'/>";
            }
        });

        // 获取频道id等
        var draft = window.localStorage.getItem("sohu_mp_mobile_draft");
        draft = JSON.parse(draft);
        
        $("#title").val(draft.title);

        $("#channelId").val(draft.channelId);

        $("#categoryId").val(draft.subchannelId);

        $('#umeditor_textarea_content').val(content);

        // 发布之后清空缓存
        util.editCache.removeCache();

        $("#news-form").attr("action", "/m/main/news/publish.action");

        $("#news-form").submit();
    });

    // 点击删除按钮
    $picWrapper.delegate('.delete', 'tap', '.delete', function () {
        var $this = $(this) ,$li = $this.closest('.pics-w-li');
        $li.remove();
        if ($li.data('id')) {
            util.editCache.removeCacheItem('video', parseInt($li.data('id')));
        } else {
            util.editCache.removeCacheItem('image', $li.find('img').attr('src'));
        }
    });

});