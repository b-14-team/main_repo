
$(document).ready(function () {
        const auth=BearereyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIwMTA0MWIwMi05MmIxLTQxYmItOGZiZS04Nzc2ZDUwOWNlMTUiLCJzdWIiOiJiMTR1c2VyQGdtYWlsLmNvbSIsImF1dGgiOiJVU0VSIiwiaWF0IjoxNzIwNzg1MDE3LCJleHAiOjE3MjA3OTIyMTcsInRva2VuVHlwZSI6ImFjY2VzcyJ9.zGBXhPiDiMHJT7avor9n6tWLMbb2mSiq_m3IsnnT2aA;

        if (auth !== undefined && auth !== '') {
            $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
                jqXHR.setRequestHeader('Authorization', auth);
            });
            }

    // 서버에서 카드 데이터를 가져오기
    function loadCards() {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/cards',  // 카드 데이터를 가져오는 엔드포인트
            success: function (response) {
                response.forEach(card => {
                    addCardToColumn(card);
                });
            },
            error: function (error) {
                console.error("Error loading cards:", error);  // 오류 로그 추가
                alert("카드 데이터를 불러오는 중 오류가 발생했습니다.");
            },
        });
    }

    // 카드 추가 칼럼 선택 기능
    function addCardToColumn(card) {
        var column;
        if (card.status === "시작전") {
            column = document.getElementById('column1');
        } else if (card.status === "진행중") {
            column = document.getElementById('column2');
        } else if (card.status === "완료") {
            column = document.getElementById('column3');
        } else if (card.status === "긴급") {
            column = document.getElementById('column4');
        }
        if (column) {
            var newCard = document.createElement('div');
            newCard.className = 'card';
            newCard.textContent = card.title;
            column.insertBefore(newCard, column.lastElementChild);
        }
    }

// 페이지가 로드되면 카드 데이터를 불러옵니다.
    $(document).ready(function() {
        loadCards();
    });




});