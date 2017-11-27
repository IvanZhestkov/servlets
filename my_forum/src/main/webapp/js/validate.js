/** Create topic Form validation*/
$(document).ready(function () {
    jQuery.validator.addMethod("topic_naming", function(value, element) {
        return this.optional(element) || /^(\w*\d*)*?/.test(value);
    }, "Допустимы только буквы и цифры");

    jQuery.validator.addMethod("usernaming", function(value, element) {
        return this.optional(element) || /^\d*\w+\d*?/.test(value);
    }, "Допустимы только буквы и цифры");

    jQuery.validator.addMethod("usernaming_real", function(value, element) {
        return this.optional(element) || /^\w*?/.test(value);
    }, "Допустимы только буквы");
    $('#create_topic__submit').on('click',function (e) {
        $('.create-topic').validate({
            errorClass: "validation_error",
            rules: {
                editor_text: {
                    required: function(){
                        CKEDITOR.instances.editor_text.updateElement();
                    },
                    minlength:10
                },
                topic_name: {
                    required: true,
                    topic_naming: {}
                }
            },
            messages: {
                topic_name: {
                    required: "<p class='error__message'>Введите хотя бы один символ</p>"
                },
                editor_text: {
                    required: "Заполни!"
                }
            }

        });
    })

    $(function() {
        var validator = $('.create-topic').submit(function() {
            // update underlying textarea before submit validation
            tinyMCE.triggerSave();
        }).validate({
            ignore: "",
            rules: {
                title: "required",
                content: "required"
            },
            errorPlacement: function(label, element) {
                // position error label after generated textarea
                if (element.is("textarea")) {
                    label.insertAfter(element.next());
                } else {
                    label.insertAfter(element)
                }
            }
        });
        validator.focusInvalid = function() {
            // put focus on tinymce on submit validation
            if (this.settings.focusInvalid) {
                try {
                    var toFocus = $(this.findLastActive() || this.errorList.length && this.errorList[0].element || []);
                    if (toFocus.is("textarea")) {
                        tinyMCE.get(toFocus.attr("id")).focus();
                    } else {
                        toFocus.filter(":visible").focus();
                    }
                } catch (e) {
                    // ignore IE throwing errors when focusing hidden elements
                }
            }
        }
    });

    $(function() {
        var validator = $('.form-message').submit(function() {
            // update underlying textarea before submit validation
            tinyMCE.triggerSave();
        }).validate({
            ignore: "",
            rules: {
                title: "required",
                content: "required"
            },
            errorPlacement: function(label, element) {
                // position error label after generated textarea
                if (element.is("textarea")) {
                    label.insertAfter(element.next());
                } else {
                    label.insertAfter(element)
                }
            }
        });
        validator.focusInvalid = function() {
            // put focus on tinymce on submit validation
            if (this.settings.focusInvalid) {
                try {
                    var toFocus = $(this.findLastActive() || this.errorList.length && this.errorList[0].element || []);
                    if (toFocus.is("textarea")) {
                        tinyMCE.get(toFocus.attr("id")).focus();
                    } else {
                        toFocus.filter(":visible").focus();
                    }
                } catch (e) {
                    // ignore IE throwing errors when focusing hidden elements
                }
            }
        }
    });

    $('.register_form').validate({
        errorClass: "validation_error",
        rules: {
            username: {
                required: true,
                minlength: 4,
                usernaming: true
            },
            fname: {
                required: false,
                usernaming_real: true
            },
            fname: {
                required: false,
                usernaming_real: true
            },
            email: {
                required: true,
                email: true
            },
            password: {
                required: true,
                minlength: 6
            }
        },
        messages: {
            username: {
                required: "Обязательное поле",
                minlength: "Минимальная длина 4"
            },
            email: {
                required: "Обязательное поле",
                email: "Введите правильный email адрес"
            },
            password: {
                required: "Обязательное поле",
                minlength: "Длина пароля не менее 5 символов"
            }
        }
    });

    $('.edit-user').validate({
        errorClass: "validation_error",
        rules: {
            fname: {
                required: false,
                usernaming_real: true
            },
            fname: {
                required: false,
                usernaming_real: true
            },
            email: {
                required: true,
                email: true
            },
            password: {
                required: true,
                minlength: 5
            }
        },
        messages: {
            email: {
                required: "Обязательное поле",
                email: "Введите правильный email адрес"
            },
            password: {
                required: "Обязательное поле",
                minlength: "Длина пароля не менее 6 символов"
            }
        }
    });

    checkUsername('#username-input');
    checkEmail('#email-input');
});

function checkUsername(input) {
    $(input).on('blur', function () {
        $.post('checkusername', {username : $(input).val()}, function(responseText) {
            $('.vmessage_username').html(responseText)
        })
    });
}

function checkEmail(input) {
    $(input).on('blur', function () {
        $.post('checkemail', {email : $(input).val()}, function(responseText) {
            $('.vmessage_email').html(responseText)
        })
    });
}

function feed(input) {
    $.post('feed', {topic_id: $(input).attr('data-topic')}, function(response) {
        if (response.getResponseHeader('REQUIRES_AUTH') === '1') {
            window.location.href = 'topic?id='+$(input).attr('data-topic');
        }
    });
}

function unfeed(input) {
    $.post('unfeed', {topic_id: $(input).attr('data-topic')},function(response) {
        if (response.getResponseHeader('REQUIRES_AUTH') === '1') {
            window.location.href = 'topic?id='+$(input).attr('data-topic');
        }
    });
}
