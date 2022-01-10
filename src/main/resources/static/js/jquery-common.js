(function($) {
    $.ajaxSetup({
        cache:false,
        error: function(xhr, status, err) {
            console.log('common='+xhr.status);
            if (xhr.status == 401) {
                alert("인증에 실패 했습니다. 로그인 페이지로 이동합니다.");
                location.href = "/logout";
            } else if (xhr.status == 403) {
                alert("세션이 만료가 되었습니다. 로그인 페이지로 이동합니다.");
                location.href = "/login";
            }else {
                location.href = "/error/error_500.html";
            }
        }
    });

    $.validator.addMethod("filesize", function (value, element, param) {
        return this.optional(element)||$(element).data('size') <= param;
    });

    $.validator.addMethod("extension2", function (value, element, param) {
        return this.optional(element)||param.indexOf($(element).data('ext')) > -1;
    });

    $.validator.addMethod('num',function(value, element){
        return this.optional(element) || value.match(/^[0-9|\s]*$/);
    });

    $.validator.addMethod("regex", function(value, element, regexpr) {
        return this.optional(element) || regexpr.test(value);
    }, "Please enter a valid pasword.");

    $.validator.addMethod("regex2", function(value, element, regexpr) {
        return this.optional(element) || value.match(regexpr);
    }, "Please enter a valid pasword.");

    $.validator.addMethod("sameidcheck", function(value, element) {
        return this.optional(element)||value == 'true';
    }, "Please enter a valid sameidcheck.");

    $.validator.addMethod("passChange", function(value, element) {
        return this.optional(element)||/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?^=-])[A-Za-z\d$@$!%*#?^=-]{8,25}$/.test(value);
    }, "Please enter a valid sameidcheck.");

    $.validator.addMethod('nowdatecheck',function(value, element){
        var nowdate = new Date();
        var inputdate = new Date(value);

        var nyear = nowdate.getFullYear();              //yyyy
        var nmonth = (1 + nowdate.getMonth());          //M
        nmonth = nmonth >= 10 ? nmonth : '0' + nmonth;  //month 두자리로 저장
        var nday = nowdate.getDate();                   //d
        nday = nday >= 10 ? nday : '0' + nday;          //day 두자리로 저장

        var iyear = inputdate.getFullYear();              //yyyy
        var imonth = (1 + inputdate.getMonth());          //M
        imonth = imonth >= 10 ? imonth : '0' + imonth;  //month 두자리로 저장
        var iday = inputdate.getDate();                   //d
        iday = iday >= 10 ? iday : '0' + iday;          //day 두자리로 저장


        var nowymd=nyear+""+nmonth+""+nday;
        var inputymd=iyear+""+imonth+""+iday;


        return !(Number(inputymd) < Number(nowymd));
    });

    $.validator.setDefaults({
        onkeyup: false,
        onclick: false,
        onfocusout: false,
        showErrors: function(errorMap,errorList){
            // console.log('errorMap',errorMap);
            // console.log('errorList',errorList);
            if(this.numberOfInvalids()){ // 에러가 있으면
                alert(errorList[0].message); // 경고창으로 띄움
            }
        }
    });

})(jQuery);
