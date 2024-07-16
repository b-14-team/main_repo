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

            // 헤더
            const accessToken = jqXHR.getResponseHeader('Authorization');
            const refreshToken = jqXHR.getResponseHeader('Authorization-Refresh');

            // 토큰을 localStorage에 저장
            localStorage.setItem('accessToken', accessToken);
            localStorage.setItem('refreshToken', refreshToken);

            // 바디
            const message = response.message;

            // 성공 알림
            alert(message);

            // 확인 콘솔 로그
            console.log("accessToken: ", accessToken);
            console.log("refreshToken:" , refreshToken);
            console.log("message:" , message);

            // 로그인 성공 시 보드 리스트 페이지로
            // window.location.href = '/board-list';
            //window.location.href = '/board';

            // access 토큰을 /board 페이지로 전달
            loadBoardPage(accessToken);
        },
        error: function (xhr, status, error) {
            alert('로그인 실패: ' + xhr.responseText || xhr.statusText);
            console.log("login function error", error);
        }
    });
}

function loadBoardPage(accessToken) {
    $.ajax({
        type: "GET",
        url: "/board",
        headers: {
            "Authorization": accessToken
        },
        success: function(response) {
            // /board 페이지의 데이터 처리
            document.open();
            document.write(response);
            document.close();
        },
        error: function(xhr, status, error) {
            alert('보드 페이지 로드 실패: ' + (xhr.responseText || xhr.statusText));
            console.log("loadBoardPage function error", error); // 확인 로그
        }
    });
}