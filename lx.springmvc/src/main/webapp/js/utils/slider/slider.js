var SimpleSlider = function(id) {
	"use strict";
	
	console.log(window.location.href);
	
	this.id = id;
	this.container = $('#' + id);
	
	if(this.container == null) {
		alert("ca");
		return;
	}
	
	this.w = this.container.width();
	this.h = this.container.height();
	this.list = [];

	var tempListForTagA = $(this.container).children('a');
	
	if ( $(tempListForTagA).length != 0 ) {
		for (var i = 0 ; i < $(tempListForTagA).length; i++) {
			var ele = tempListForTagA[i];
			var imgList = $(ele).children('img');
			if ( $(imgList).size() == 0 ) {
				$(ele).remove();
			}
			for (var j = 1 ; j < $(imgList).length; j++) {
				$(imgList[j].remove());
			}
			$(ele).width(this.w);
			$(ele).height(this.h);
			$(imgList[0]).width(this.w);
			$(imgList[0]).height(this.h);
			this.list.push(tempListForTagA[i]);
			$(ele).addClass("simple-slider-slide");
		}
	}
	this.cur = this.list[0];
	this.next = this.list.length > 1 ? this.list[1] : this.cur;
	this.pre = this.list[this.list.length - 1];
	
	$(this.pre).addClass("simple-slider-pre-slide");
	$(this.cur).addClass("simple-slider-cur-slide");
	$(this.next).addClass("simple-slider-next-slide");
	
	this.locked = false;
	
	this.right = function() {
		if (this.locked) {
			return;
		}
		this.removeClass();
		this.locked = true;
		this.next = this.cur;
		this.cur = this.pre;
		var index = $(this.list).index(this.cur);
		var length = this.list.length;
		if ( index == 0 ) {
			this.pre = this.list[$(this.list).length - 1];
		} else {
			this.pre = this.list[index - 1];
		}
		this.addClass();
		$(this.next).addClass("simple-direction");
		setTimeout($.proxy(function(){$(this.next).removeClass("simple-direction");}, this), 3000);
		this.locked = false;
	};
	
	this.left = function() {
		if (this.locked) {
			return;
		}
		this.removeClass();
		this.locked = true;
		this.pre = this.cur;
		this.cur = this.next;
		var index = $(this.list).index(this.cur);
		var length = this.list.length;
		if ( index != (length - 1) ) {
			this.next = this.list[index + 1];
		} else {
			this.next = this.list[0];
		}
		this.addClass();
		$(this.pre).addClass("simple-direction");
		if(this.ieVersion == 7 || this.ieVersion == 8) {
			$(this.pre).css("right", 0);
			$(this.pre).animate({right: "100%"}, 3000);
			$(this.cur).css("right", "-" + this.w);
			$(this.cur).animate({right: 0}, 3000);
		} else {
			setTimeout($.proxy(function(){$(this.pre).removeClass("simple-direction");}, this), 3000);
		}
		this.locked = false;
	};
	
	this.removeClass = function() {
		$(this.pre).removeClass("simple-slider-pre-slide");
		$(this.cur).removeClass("simple-slider-cur-slide");
		$(this.next).removeClass("simple-slider-next-slide");
		$(this.cur).attr('aria-hidden', 'true');
	};
	
	this.addClass = function() {
		$(this.pre).addClass("simple-slider-pre-slide");
		$(this.cur).addClass("simple-slider-cur-slide");
		$(this.next).addClass("simple-slider-next-slide");
		$(this.cur).attr('aria-hidden', 'false');
	};

	this.ieVersion = (function() {
		 var myNav = navigator.userAgent.toLowerCase();
		 return (myNav.indexOf('msie') != -1) ? parseInt(myNav.split('msie')[1]) : 0;
	})();
	
	setInterval($.proxy(this.left, this), 5000);
};