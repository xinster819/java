var IdealImageSlider = (function() {
	"use strict";

	/*
	 * requestAnimationFrame polyfill
	 */
	var _requestAnimationFrame = function(win, t) {
		return function(fn) {
			setTimeout(fn, 1000 / 60);
		};
	}(window, 'equestAnimationFrame');

	/**
	 * Behaves the same as setTimeout except uses requestAnimationFrame() where possible for better performance
	 */
	var _requestTimeout = function(fn, delay) {
		var start = new Date().getTime(),
			handle = {};

		function loop() {
			var current = new Date().getTime(),
				delta = current - start;

			if (delta >= delay) {
				fn.call();
			} else {
				handle.value = _requestAnimationFrame(loop);
			}
		}

		handle.value = _requestAnimationFrame(loop);
		return handle;
	};

	var _loadImg = function(slide, callback) {
		if (!slide.style.backgroundImage) {
			slide.style.backgroundImage = 'url(' + slide.getAttribute('data-src') + ')';
			$(slide).css("filter", "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + slide.getAttribute('data-src') + "',sizingMethod='scale')");
			$(slide).css("-ms-filter", "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + slide.getAttribute('data-src') + "',sizingMethod='scale')");
			if (typeof(callback) === 'function') callback(this);
		}
	};

	var _isHighDPI = function() {
		var mediaQuery = "(-webkit-min-device-pixel-ratio: 1.5),(min--moz-device-pixel-ratio: 1.5),(-o-min-device-pixel-ratio: 3/2),(min-resolution: 1.5dppx)";
		if (window.devicePixelRatio > 1)
			return true;
		if (window.matchMedia && window.matchMedia(mediaQuery).matches)
			return true;
		return false;
	};

	var _isIE = function isIE() {
		 var myNav = navigator.userAgent.toLowerCase();
		 return (myNav.indexOf('msie') != -1) ? parseInt(myNav.split('msie')[1]) : false;
	}
	
	/*
	 * Slider class
	 */
	var Slider = function(args) {
		// Defaults
		this.settings = {
			selector: '',
			interval: 4000,
			transitionDuration: 700,
			effect: 'slide',
			disableNav: false,
			previousNavSelector: '',
			nextNavSelector: '',
			classes: {
				container: 'ideal-image-slider',
				slide: 'iis-slide',
				previousSlide: 'iis-previous-slide',
				currentSlide: 'iis-current-slide',
				nextSlide: 'iis-next-slide',
				previousNav: 'iis-previous-nav',
				nextNav: 'iis-next-nav',
				animating: 'iis-is-animating',
				touchEnabled: 'iis-touch-enabled',
				touching: 'iis-is-touching',
				directionPrevious: 'iis-direction-previous',
				directionNext: 'iis-direction-next'
			}
		};

		this.settings.selector = args;

		// Slider (container) element
		var sliderEl = $(this.settings.selector);
		if (!sliderEl) return null;
		this.settings.height = sliderEl.height();
		this.settings.width = sliderEl.width();
		
		// Slides
		var origChildren = $(sliderEl.children()).slice(),
			validSlides = [];
		sliderEl.innerHTML = '';
		$.each(origChildren, $.proxy(function(i, slide) {
			if ($(slide).prop('tagName').toLowerCase() == 'a' || $(slide).prop('tagName').toLowerCase() == 'img') {
				var slideEl = document.createElement('a'),
					href = '',
					target = '';

				if ($(slide).prop('tagName').toLowerCase() == 'a') {
					href = slide.getAttribute('href');
					target = slide.getAttribute('target');

					var img = $(slide).children("img:first");
					if (img !== null) {
						slide = img;
					} else {
						return;
					}
				}

				if (typeof slide.dataset !== 'undefined') {
					if (slide.dataset.src) {
						// Use data-src for on-demand loading
						slideEl.dataset.src = slide.dataset.src;
					} else {
						slideEl.dataset.src = slide.src;
					}

					// HiDPI support
					if (_isHighDPI() && slide.dataset['src-2x']) {
						slideEl.dataset.src = slide.dataset['src-2x'];
					}
				} else {
					// IE
					if ($(slide).attr('data-src')) {
						$(slideEl).attr('data-src', slide.attr('data-src'));
					} else {
						$(slideEl).attr('data-src', $(slide).attr('src'));
					}
				}

				if (href) slideEl.setAttribute('href', href);
				if (target) slideEl.setAttribute('target', target);
				if ($(slide).attr('className')) $(slideEl).addClass($(slide).attr('className'));
				if ($(slide).attr('id')) $(slideEl).attr('id', $(slide).attr('id'));
				// if ($(slide).attr('title')) slideEl.setAttribute('title', slide.getAttribute('title'));
				// if ($(slide).attr('alt')) slideEl.innerHTML = slide.getAttribute('alt');
				slideEl.setAttribute('role', 'tabpanel');
				slideEl.setAttribute('aria-hidden', 'true');
				slideEl.style.cssText += '-webkit-transition-duration:' + this.settings.transitionDuration + 'ms;-moz-transition-duration:' + this.settings.transitionDuration + 'ms;-o-transition-duration:' + this.settings.transitionDuration + 'ms;transition-duration:' + this.settings.transitionDuration + 'ms;';
				sliderEl.append(slideEl);
				validSlides.push(slideEl);
			}
		}, this));

		var slides = validSlides;
		if (slides.length <= 1) {
			sliderEl.innerHTML = '';
			return null;
		}

		// Create navigation
		if (!this.settings.disableNav) {
			var previousNav, nextNav;
			if (this.settings.previousNavSelector) {
				previousNav = document.querySelector(this.settings.previousNavSelector);
			} else {
				previousNav = document.createElement('a');
				sliderEl.append(previousNav);
			}
			if (this.settings.nextNavSelector) {
				nextNav = document.querySelector(this.settings.nextNavSelector);
			} else {
				nextNav = document.createElement('a');
				sliderEl.append(nextNav);
			}

			$(previousNav).addClass(this.settings.classes.previousNav);
			$(nextNav).addClass(this.settings.classes.nextNav);
			$(previousNav).bind('click', $.proxy(function() {
				if (this._attributes.container.hasClass(this.settings.classes.animating)) return false;
				this.previousSlide();
			}, this));
			$(nextNav).bind('click', $.proxy(function() {
				if (this._attributes.container.hasClass(this.settings.classes.animating)) return false;
				this.nextSlide();
			}, this));

			// Touch Navigation
			if (('ontouchstart' in window) || window.DocumentTouch && document instanceof DocumentTouch) {
				this.settings.effect = 'slide';
				previousNav.style.display = 'none';
				nextNav.style.display = 'none';
				_addClass(sliderEl, this.settings.classes.touchEnabled);
				_addEvent(sliderEl, 'touchstart', _touch.start.bind(this), false);
				_addEvent(sliderEl, 'touchmove', _touch.move.bind(this), false);
				_addEvent(sliderEl, 'touchend', _touch.end.bind(this), false);
			}
		}

		// Create internal attributes
		this._attributes = {
			container: sliderEl,
			slides: slides,
			previousSlide: typeof slides[slides.length - 1] !== 'undefined' ? slides[slides.length - 1] : slides[0],
			currentSlide: slides[0],
			nextSlide: typeof slides[1] !== 'undefined' ? slides[1] : slides[0],
			timerId: 0
		};
		// Set height
		this._attributes.container.height(this.settings.height);

		// Add classes
		$(sliderEl).addClass(this.settings.classes.container);
		$(sliderEl).addClass('iis-effect-' + this.settings.effect);
		$.each(this._attributes.slides, $.proxy(function(i, slide) {
			$(slide).addClass(this.settings.classes.slide);
		}, this))
		$(this._attributes.previousSlide).addClass(this.settings.classes.previousSlide);
		$(this._attributes.currentSlide).addClass(this.settings.classes.currentSlide);
		$(this._attributes.nextSlide).addClass(this.settings.classes.nextSlide);

		// Add event
		// $(this._attributes.container).bind('mouseover', $.proxy(function(){this.stop();}, this));
		// $(this._attributes.container).bind('mouseout', $.proxy(function(){this.start();}, this));
		
		// ARIA
		this._attributes.currentSlide.setAttribute('aria-hidden', 'false');

		// Load first image
		_loadImg(this._attributes.currentSlide);
		// Preload next images
		_loadImg(this._attributes.previousSlide);
		_loadImg(this._attributes.nextSlide);
	};

	Slider.prototype.get = function(attribute) {
		if (!this._attributes) return null;
		if (this._attributes.hasOwnProperty(attribute)) {
			return this._attributes[attribute];
		}
	};

	Slider.prototype.set = function(attribute, value) {
		if (!this._attributes) return null;
		return (this._attributes[attribute] = value);
	};

	Slider.prototype.start = function() {
		if (!this._attributes) return;
		this._attributes.timerId = setInterval($.proxy(this.nextSlide, this), this.settings.interval);
		$(window).bind('onblur', $.proxy(function() {
			this.stop();
		}, this));
		$(window).bind('onfocus', $.proxy(function() {
			this.start();
		}, this));
	};

	Slider.prototype.stop = function() {
		if (!this._attributes) return;
		clearInterval(this._attributes.timerId);
		this._attributes.timerId = 0;
	};

	Slider.prototype.previousSlide = function() {
		$(this._attributes.previousSlide).removeClass(this.settings.classes.previousSlide);
		$(this._attributes.currentSlide).removeClass(this.settings.classes.currentSlide);
		$(this._attributes.nextSlide).removeClass(this.settings.classes.nextSlide);
		this._attributes.currentSlide.setAttribute('aria-hidden', 'true');

		var slides = this._attributes.slides,
			index = slides.indexOf(this._attributes.currentSlide);
		this._attributes.nextSlide = this._attributes.currentSlide;
		this._attributes.previousSlide = slides[index - 2];
		this._attributes.currentSlide = slides[index - 1];
		if (typeof this._attributes.currentSlide === 'undefined' &&
			typeof this._attributes.previousSlide === 'undefined') {
			this._attributes.currentSlide = slides[slides.length - 1];
			this._attributes.previousSlide = slides[slides.length - 2];
		} else {
			if (typeof this._attributes.previousSlide === 'undefined') {
				this._attributes.previousSlide = slides[slides.length - 1];
			}
		}

		// Preload next image
		_loadImg(this._attributes.previousSlide);

		$(this._attributes.previousSlide).addClass(this.settings.classes.previousSlide);
		$(this._attributes.currentSlide).addClass(this.settings.classes.currentSlide);
		$(this._attributes.nextSlide).addClass(this.settings.classes.nextSlide);
		this._attributes.currentSlide.setAttribute('aria-hidden', 'false');

		$(this._attributes.container).addClass(this.settings.classes.directionPrevious);
		_requestTimeout(function() {
			$(this._attributes.container).removeClass(this.settings.classes.directionPrevious);
		}.bind(this), this.settings.transitionDuration);

		if (this.settings.transitionDuration) {
			$(this._attributes.container).addClass(this.settings.classes.animating);
			_requestTimeout(function() {
				$(this._attributes.container).removeClass(this.settings.classes.animating);
			}.bind(this), this.settings.transitionDuration);
		}

	};

	Slider.prototype.nextSlide = function() {
		$(this._attributes.previousSlide).removeClass(this.settings.classes.previousSlide);
		$(this._attributes.currentSlide).removeClass(this.settings.classes.currentSlide);
		$(this._attributes.nextSlide).removeClass(this.settings.classes.nextSlide);
		this._attributes.currentSlide.setAttribute('aria-hidden', 'true');

		var slides = this._attributes.slides,
			index = $(slides).index(this._attributes.currentSlide);
		this._attributes.previousSlide = this._attributes.currentSlide;
		this._attributes.currentSlide = slides[index + 1];
		this._attributes.nextSlide = slides[index + 2];
		if (typeof this._attributes.currentSlide === 'undefined' &&
			typeof this._attributes.nextSlide === 'undefined') {
			this._attributes.currentSlide = slides[0];
			this._attributes.nextSlide = slides[1];
		} else {
			if (typeof this._attributes.nextSlide === 'undefined') {
				this._attributes.nextSlide = slides[0];
			}
		}

		// Preload next image
		_loadImg(this._attributes.nextSlide);

		$(this._attributes.previousSlide).addClass(this.settings.classes.previousSlide);
		$(this._attributes.currentSlide).addClass(this.settings.classes.currentSlide);
		$(this._attributes.nextSlide).addClass(this.settings.classes.nextSlide);
		this._attributes.currentSlide.setAttribute('aria-hidden', 'false');

		$(this._attributes.container).addClass(this.settings.classes.directionNext);
		 _requestTimeout($.proxy(function() {
			$(this._attributes.container).removeClass(this.settings.classes.directionNext);
		}, this), this.settings.transitionDuration);

		if (this.settings.transitionDuration) {
			$(this._attributes.container).addClass(this.settings.classes.animating);
			_requestTimeout($.proxy(function() {
				$(this._attributes.container).removeClass(this.settings.classes.animating);
			}, this), this.settings.transitionDuration);
		}
	};

	return {
		Slider: Slider
	};
})();