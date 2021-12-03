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
            }else if (jqXHR.status == 405) {
                alert("세션이 종료 되었습니다. 로그인 페이지로 이동합니다.");
                location.href = "/login";
            }
        }
    });
})(jQuery);