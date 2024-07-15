
let auth = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0ZTE0M2ExNy1jMjI2LTRkMTAtYmNmMi0xODczYmU4YTcxMGIiLCJzdWIiOiJiMTR1c2VyQGdtYWlsLmNvbSIsImF1dGgiOiJVU0VSIiwiaWF0IjoxNzIwOTk5NjU3LCJleHAiOjE3MjEwMDY4NTcsInRva2VuVHlwZSI6ImFjY2VzcyJ9.l6h_LKhMUvXxPpl0X4eD2aoIQpwHjTiq9AgcWUaYvPs";
let draggedCard;
let boardId = 1;
let columnMap = {}; // 전역변수로 칼럼 맵 정의
$(document).ready(function () {

    var columnMap = {};


    function addColumnToBoard(columnData) {
        var board = document.getElementById('board');
        var newColumn = document.createElement('div');
        newColumn.className = 'column';
        newColumn.id = `column${columnData.id}`;
        // newColumn.style.borderColor = columnData.color; // 칼럼 색상 설정

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

                // 카드 테두리 색상 설정
                newCard.style.borderColor = columnData.color || 'black'; // 컬럼 색상으로 테두리 색상 설정
                newCard.style.borderStyle = 'solid'; // 테두리 스타일 설정
                newCard.style.borderWidth = '2px'; // 테두리 두께 설정

                // 수정 버튼 생성
                var editButton = document.createElement('button');
                editButton.textContent = '수정';
                editButton.className = 'edit-button';
                editButton.onclick = function() {
                    // 수정 로직 추가
                    editCard(card.id);
                };

                // 삭제 버튼 생성
                var deleteButton = document.createElement('button');
                deleteButton.textContent = '삭제';
                deleteButton.className = 'delete-button';
                deleteButton.onclick = function() {
                    // 삭제 로직 추가
                    showDeleteModal(card.id);
                };

                // 버튼들을 카드에 추가
                newCard.appendChild(editButton);
                newCard.appendChild(deleteButton);

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
                        columnMap[column.id] = {
                            id: column.id,
                            color: column.color,
                            status: column.columnsStatus
                        }; // 컬럼 색상 및 상태 저장

                        console.log(`Loading column: ${column.columnsStatus} with color: ${column.color}`);
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

    //드레그 가능
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
    //드레그 가능
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

    function loadAssignees() {
        $.ajax({
            url: `http://localhost:8080/boards/${boardId}/assignees`, // 실제 API URL
            method: 'GET',
            success: function(response) {
                const assigneeSelect = $('#taskAssignee');
                assigneeSelect.empty(); // 기존 옵션 제거

                // 기본 옵션 추가
                assigneeSelect.append('<option value="">작업자를 선택하세요</option>');

                // 작업자 목록 추가
                const assignees = response.data; // response.data를 사용해야 함
                populateAssigneeSelect(assignees);
            },
            error: function(xhr) {
                console.error('Error loading assignees:', xhr);
            }
        });
    }

    function populateAssigneeSelect(assignees) {
        var assigneeSelect = document.getElementById('taskAssignee');
        // 기존 옵션 제거
        assigneeSelect.innerHTML = '<option value="">작업자를 선택하세요</option>';
        assignees.forEach(assignee => {
            var option = document.createElement('option');
            option.value = assignee.assigneeId; // assigneeId로 변경
            option.textContent = assignee.nickName; // nickName으로 변경
            assigneeSelect.appendChild(option);
        });
    }



    loadAssignees();
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
        url: `http://localhost:8080/cards/${cardId}/move/${targetColumnId}`, // 카드 ID에 대한 PATCH 요청
        headers: {
            'Authorization': 'Bearer ' + auth,
            'Content-Type': 'application/json'
        },
        data: JSON.stringify({ status: targetColumnId }), // 새 상태(칼럼 ID)를 JSON으로 전달
        success: function (response) {
            console.log(`Card ${cardId} moved to column ${targetColumnId}`);
            alert("카드 이동되었습니다.");
            location.reload();
        },
        // error: function (error) {
        //     console.error("Error moving card:", error);
        //     alert("카드를 이동하는 중 오류가 발생했습니다.");
        // }
    });
}


// 삭제 모달 표시 함수
function showDeleteModal(cardId) {
    const modal = document.getElementById("deleteModal");
    modal.style.display = "block";

    const confirmDelete = document.getElementById("confirmDelete");
    confirmDelete.onclick = function() {
        deleteCard(cardId);
        modal.style.display = "none";
    }
}

// 모달 닫기 함수
function closeDeleteModal() {
    const modal = document.getElementById("deleteModal");
    modal.style.display = "none";
}

function deleteCard(cardId) {
    $.ajax({
        type: 'DELETE',
        url: `http://localhost:8080/cards/${cardId}`,
        headers: {
            'Authorization': 'Bearer ' + auth
        },
        success: function (response) {
            alert("카드가 삭제되었습니다.");
            location.reload();
        },
        error: function (error) {
            console.error("Error deleting card:", error);
            alert("카드를 삭제하는 중 오류가 발생했습니다.");
        },
    });
}


// function updateCardUI(cardId, targetColumnId) {
//     // 카드가 이동된 칼럼에 UI에서 카드 요소를 업데이트하는 로직
//     const cardElement = document.querySelector(`.card[data-id='${cardId}']`);
//     const targetColumn = document.getElementById(`column${targetColumnId}`);
//
//     if (cardElement && targetColumn) {
//         targetColumn.appendChild(cardElement); // 새로운 칼럼에 카드 추가
//
//         // 새로운 칼럼 색상 가져오기
//         const newColumnColor = columnMap[targetColumnId].color; // 컬럼 색상 가져오기
//         cardElement.style.borderColor = newColumnColor || 'black'; // 테두리 색상 업데이트
//     } else {
//         console.error("Card or target column not found.");
//     }
// }

