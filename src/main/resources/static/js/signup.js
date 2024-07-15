$(document).ready(function() {
    function signup() {
        // 폼 데이터를 가져옴
        var name = $("#name").val();
        var email = $("#email").val();
        var password = $("#password").val();
        var confirmPassword = $("#confirm-password").val();
        var description = $("#bio").val();

        // 비밀번호 확인
        if (password !== confirmPassword) {
            alert("Passwords do not match.");
            return;
        }

        // AJAX 요청
        $.ajax({
            url: '/users/signup', // 서버의 회원가입 엔드포인트
            type: 'POST',
            contentType: 'application/json', // Content-Type을 JSON으로 설정
            data: JSON.stringify({
                nickName: name,
                email: email,
                password: password,
                description: description
            }),
            success: function(response) {
                // 회원가입 성공 시 처리
                alert("회원가입에 성공하였습니다.");
                window.location.href = '/login'; // 로그인 페이지로 리디렉션
            },
            error: function(xhr, status, error) {
                // 오류 발생 시 처리
                alert("회원가입 실패: " + xhr.responseText);
            }
        });
    }

    // 폼의 제출 이벤트를 가로채서 signup 함수를 호출
    $("form").on("submit", function(event) {
        event.preventDefault();
        signup();
    });
});
