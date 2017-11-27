/*Animations and customizations*/

$(document).ready(function() {
	$('.form-search__input_header').on('focus', function() {
		myAddClass('.form-search__icon_header', 'form-search__icon_header_blue')
	});

	$('.form-search__input_header').on('blur', function() {
		myRemoveClass('.form-search__icon_header', 'form-search__icon_header_blue')
	});
});

function myAddClass(elem, myclass) {
	$(elem).addClass(myclass);
}

function myRemoveClass(elem, myclass) {
	$(elem).removeClass(myclass);
}



/*Popup JS*/

/*popups*/
var notifications = {
		click : '.user-block__notification',
		popup : '#notifications'
	},
	add = {
		click : '.user-block__add',
		popup : '#add'
	},
	profile = {
		click : '.user-block__profile',
		popup : '#profile'
	};

var popupClicks = [notifications.click, add.click, profile.click];
var search_input = '.form-search__input';
$(document).ready(function() {
	clicked(notifications);
	clicked(add);
	clicked(profile);

	close('.popup__close');
	showSearchResults(search_input);
});

$(document).mouseup( function(event){
	if($(event.target).closest(".popup").length ) return;
	popupHide('.popup');
});

function popupShow(popupId) {
	$(popupId).removeClass('js-popup-hidden');
	$(popupId).addClass('js-popup');
}

function popupHide(popupId) {
	$(popupId).removeClass('js-popup');
	$(popupId).addClass('js-popup-hidden');
}

function close(closeElement) {
	$(closeElement).on('click', function() {
		popupHide('.popup');
	});
}

function clicked(element) {
	$(element.click).on('click', function() {
		if($(element.popup).hasClass('js-popup-hidden')) {
			popupHide('.popup');
			popupShow($(element.popup));
		} else popupHide($(element.popup));
	});
}

function showTopicShort(topicLink) {
	$(topicLink).on('mousemove', function () {
		$('.topic__short').fadeIn();
	});
	$(topicLink).on('mouseout', function () {
		$('.topic__short').fadeOut();
	});
}

function showSearchResults(search_input) {
	$(search_input).blur(function () {
		$('.search-results').addClass('disable');
	});

	$(search_input).focus(function () {
		$('.search-results').removeClass('disable');
	});
}

/*Ajax like*/
function likeMessage(message_id) {
	$.post('/topic', {like_message_id:message_id}, function () {

	});
}

/*quote*/
function quote(elem) {
	var messageId = $(elem).next('span').attr('class');
	var messageInfo = $('#'+messageId).find('.topic-info__publicatedate').text();
	var messageAuthor = $('#'+messageId).find('.topic-info__authorlink').text();
	var text = $('#'+messageId).find('.topic__text').text();
	var frame = $('#editor_text_ifr');

	tinymce.activeEditor.insertContent('<blockquote class="quote">' +
		'<p class="quote__info">'+
		messageAuthor+
		' '+
		messageInfo+
		'</p>'+
		'<br>'+
		'<p class="quote__text">'+
		text+
		'</p>'+
		'<br>'+
		'</blockquote>');
}

function like(message_id) {
	$.post('topic?like='+message_id);
}

function unlike(message_id) {
	$.post('topic?unlike='+message_id);
}