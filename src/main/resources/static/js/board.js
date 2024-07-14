
let auth = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIwZDY4ZWFlMC0xNmM1LTQzYmMtODRmNy0wMTNkOWNiMzM2ODkiLCJzdWIiOiJiMTR1c2VyQGdtYWlsLmNvbSIsImF1dGgiOiJVU0VSIiwiaWF0IjoxNzIwOTc0OTcyLCJleHAiOjE3MjA5ODIxNzIsInRva2VuVHlwZSI6ImFjY2VzcyJ9.Vja5BdFB5TDnJIQXI1aVmP1lPFlV_0i8bMFObR2Qcpc";
let draggedCard;
$(document).ready(function () {

    var columnMap = {};


    function addColumnToBoard(columnData) {
        var board = document.getElementById('board');
        var newColumn = document.createElement('div');
        newColumn.className = 'column';
        newColumn.id = `column${columnData.id}`;

        // 칼럼 드래그 가능하게 만들기
        makeColumnDraggable(newColumn);

        newColumn.innerHTML = `<div class="column-header">${columnData.columnsStatus}</div>
        <button class="add-button" onclick="showModal(${columnData.id})">할 일 추가하기</button>`;

        board.appendChild(newColumn);

        // 카드 목록을 올바르게 처리
        if (Array.isArray(columnData.cardList)) {
            columnData.cardList.forEach(card => {
                var newCard = document.createElement('div');
                newCard.className = 'card';
                newCard.textContent = card.title;
                newCard.dataset.cardId = card.id; // 카드 ID 저장
                // 드래그 가능한 카드 만들기
                makeDraggable(newCard);
                newColumn.appendChild(newCard);
            });
        }

        // 칼럼 드롭 이벤트 추가
        newColumn.addEventListener('dragover', (e) => {
            e.preventDefault();
            draggedCard = e.dataTransfer.
            e.dataTransfer.dropEffect = 'move';
        });

        newColumn.addEventListener('drop', (e) => {
            e.preventDefault();
            const draggedColumnId = e.dataTransfer.getData('text/plain');
            const draggedColumn = document.getElementById(draggedColumnId);
            const targetColumnId = columnData.id; // 현재 칼럼의 ID

            moveCard(draggedCard,targetColumnId);//카드 이동함수 호출

            if (draggedColumn && draggedColumn !== newColumn) {
                // 칼럼 위치 변경
                board.insertBefore(draggedColumn, newColumn);
            }
        });
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

    function makeColumnDraggable(column) {
        column.setAttribute('draggable', true);

        column.addEventListener('dragstart', (e) => {
            e.dataTransfer.setData('text/plain', column.id);
            e.dataTransfer.effectAllowed = 'move';
        });

        column.addEventListener('dragend', () => {
            column.classList.remove('dragging');
        });
    }

    function makeDraggable(card) {
        card.setAttribute('draggable', true);

        card.addEventListener('dragstart', (e) => {
            const cardId = card.dataset.cardId; // 카드의 data-card-id 가져오기
            e.dataTransfer.setData('text/plain', cardId); // 카드 ID 저장
            draggedCard = cardId; // draggedCard에 카드 ID 저장
            e.dataTransfer.effectAllowed = 'move';
        });

        card.addEventListener('dragend', () => {
            card.classList.remove('dragging');
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
            location.reload();
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
            newCard.draggable = true; // 드래그 가능 설정
            newCard.setAttribute('data-id', card.id); // 카드 ID 저장
            // 드래그 시작 이벤트
            newCard.addEventListener('dragstart', function (e) {
                e.dataTransfer.setData('text/plain', card.id); // 카드 ID 저장
            });
            column.appendChild(newCard);
        } else {
            console.error("Column with ID " + columnId + " not found.");
        }
    } else {
        console.error("No column found for status: " + card.status);
    }
}

function moveCard(cardId, targetColumnId) {
    console.log("카드아이디:" + cardId)
    console.log("칼럼아이디:" + targetColumnId)

    // AJAX 요청으로 카드의 상태 업데이트
    $.ajax({
        type: 'PATCH',
        url: `http://localhost:8080/cards/${cardId}/move/${targetColumnId}`, // 카드 ID에 대한 PUT 요청
        headers: {
            'Authorization': 'Bearer ' + auth,
            'Content-Type': 'application/json'
        },
        data: JSON.stringify({ status: targetColumnId }), // 새 상태(칼럼 ID)를 JSON으로 전달
        success: function (response) {
            console.log(`Card ${cardId} moved to column ${targetColumnId}`);
            alert("카드 이동되었습니다.")
            location.reload();
            // 성공적으로 이동한 경우 UI를 갱신
            updateCardUI(cardId, targetColumnId);
        },
        // error: function (error) {
        //     console.error("Error moving card:", error);
        //     alert("카드를 이동하는 중 오류가 발생했습니다.");
        // }
    });
}

function updateCardUI(cardId, targetColumnId) {
    // 카드가 이동된 칼럼에 UI에서 카드 요소를 업데이트하는 로직
    const cardElement = document.querySelector(`.card[data-id='${cardId}']`);
    const targetColumn = document.getElementById(`column${targetColumnId}`);

    if (cardElement && targetColumn) {
        targetColumn.appendChild(cardElement); // 새로운 칼럼에 카드 추가
    } else {
        console.error("Card or target column not found.");
    }
}

