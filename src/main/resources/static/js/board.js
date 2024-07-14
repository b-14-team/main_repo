$(document).ready(function () {
    const auth = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxNDljYzg1Yy1lOWJiLTQ2OTktYjg2Mi00NGJmZDY5YTcyNzgiLCJzdWIiOiJiMTR1c2VyQGdtYWlsLmNvbSIsImF1dGgiOiJVU0VSIiwiaWF0IjoxNzIwOTY2NzU5LCJleHAiOjE3MjA5NzM5NTksInRva2VuVHlwZSI6ImFjY2VzcyJ9.gJDPwmxRiK0kdiV0H_RXd9RddgZnDLV-us49H_j3QUE";

    var columnMap = {};


    function addColumnToBoard(columnData) {
        var board = document.getElementById('board');
        var newColumn = document.createElement('div');
        newColumn.className = 'column';
        newColumn.id = `column${columnData.id}`;
        newColumn.innerHTML = `<div class="column-header">${columnData.columnsStatus}</div>
        <button class="add-button" onclick="showModal(${columnData.id})">할 일 추가하기</button>`;
        board.appendChild(newColumn);

        // 카드 목록을 올바르게 처리
        if (Array.isArray(columnData.cardList)) {
            columnData.cardList.forEach(card => {
                var newCard = document.createElement('div');
                newCard.className = 'card';
                newCard.textContent = card.title; // card.title이 정확한지 확인
                newColumn.appendChild(newCard);
            });
        } else {
            console.error("Expected an array of cards but got:", columnData.cardList);
        }
    }

    function loadCards() {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/cards',
            headers: {
                'Authorization': 'Bearer ' + auth
            },
            success: function (response) {
                if (Array.isArray(response)) {
                    response.forEach(card => {
                        addCardToColumn(card);
                    });
                } else {
                    console.error("Expected an array but got:", response);
                }
            },
            error: function (error) {
                console.error("Error loading cards:", error);
                alert("카드 데이터를 불러오는 중 오류가 발생했습니다.");
            },
        });
    }

    function loadColumns() {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/columns',
            headers: {
                'Authorization': 'Bearer ' + auth
            },
            success: function (response) {
                console.log("Columns response:", response); // 응답 확인
                if (Array.isArray(response)) {
                    response.forEach(column => {
                        addColumnToBoard(column);
                        columnMap[column.columnsStatus] = column.id;
                    });
                } else {
                    console.error("Expected an array but got:", response);
                }
            },
            error: function (error) {
                console.error("Error loading columns:", error);
                alert("칼럼 데이터를 불러오는 중 오류가 발생했습니다.");
            },
        });
    }



    loadColumns();
});



// 카드 추가 기능 (백엔드와 통신)
function addCard(cardDto, columnId) {
    console.log("Sending cardDto:", cardDto);

    $.ajax({
        type: 'POST',
        url: `http://localhost:8080/cards/${columnId}`,
        crossOrigin: true,
        contentType: 'application/json',
        data: JSON.stringify(cardDto),
        success: function (response) {
            alert("생성 완료");
            addCardToColumn(response); // 응답 받은 카드로 칼럼에 추가
        },
        error: function (error) {
            console.error("Error:", error);
            alert("다시 입력해 주세요");
        },
    });
}

function addCardToColumn(card) {
    if (!card || !card.status) {
        console.error("Invalid card data:", card);
        return;
    }

    var columnId = columnMap[card.status];
    if (columnId) {
        var column = document.getElementById(`column${columnId}`);
        if (column) {
            var newCard = document.createElement('div');
            newCard.className = 'card';
            newCard.textContent = card.title;
            column.appendChild(newCard);
        } else {
            console.error("Column with ID " + columnId + " not found.");
        }
    } else {
        console.error("No column found for status: " + card.status);
    }
}

