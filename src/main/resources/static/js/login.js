// 로그인
function login() {

    console.log('Login function called'); // 확인 로그

    // jQuery AJAX : $.ajax() 메서드
    $.ajax({
        type: "POST",
        url: "/users/login",
        contentType: "application/json",
        data: JSON.stringify({
            "email": document.getElementById("email").value,
            "password": document.getElementById("password").value
        }),
        success: function (response, textStatus, jqXHR) {
            // AJAX 성공시
            // jqXHR : 헤더정보
            // response : 바디정보

            // 성공 알림
            alert('로그인 성공!');

            // 헤더
            const accessToken = jqXHR.getResponseHeader('Authorization');
            const refreshToken = jqXHR.getResponseHeader('Authorization-Refresh');

            // 바디
            const message = response.message;

            // 확인 로그
            // console.log("accessToken: ", accessToken);
            // console.log("refreshToken:" , refreshToken);
            // console.log("message:" , message);

            // 로그인 성공 시 보드 리스트 페이지로 리다이렉트
            //window.location.href = '/board-list';
        },
        error: function (xhr, status, error) {
            alert('로그인 실패: ' + xhr.responseText || xhr.statusText);
            console.log("login function error", error); // 확인 로그
        }
    });
}
