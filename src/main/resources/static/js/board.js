
let auth = "";
let refreshToken = "";
let draggedCard;
let boardId = 1;
let columnMap = {}; // 전역변수로 칼럼 맵 정의
let updateColumnId;
let updateCardId;

$(document).ready(function () {
    auth = localStorage.getItem('accessToken');
    refreshToken = localStorage.getItem('refreshToken');
    console.log("엑세스 토큰: " + auth)
    console.log("리프레시 토큰: " + refreshToken)

    if (auth !== undefined && auth !== '') {
        $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
            jqXHR.setRequestHeader('Authorization', auth);
            jqXHR.setRequestHeader('Authorization-refresh',refreshToken);
        });
    } else {
        window.location.href = host + '/api/user/login';
        alert("로그인 후 이용해 주세요");
        return;
    }

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
                newCard.style.borderWidth = '4px'; // 테두리 두께 설정

                // 수정 버튼 생성
                var editButton = document.createElement('button');
                editButton.textContent = '수정';
                editButton.className = 'edit-button';
                editButton.onclick = function() {
                    // 수정 로직 추가
                    showUpdateModal(card);
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

function loadAssignees(selectElementId) {
    $.ajax({
        url: `http://localhost:8080/boards/${boardId}/assignees`, // 실제 API URL
        method: 'GET',
        success: function(response) {
            const assigneeSelect = $(`#${selectElementId}`); // 선택자 매개변수
            assigneeSelect.empty(); // 기존 옵션 제거

            // 기본 옵션 추가
            assigneeSelect.append('<option value="">작업자를 선택하세요</option>');

            // 작업자 목록 추가
            const assignees = response.data;
            assignees.forEach(assignee => {
                assigneeSelect.append(`<option value="${assignee.assigneeId}">${assignee.nickName}</option>`);
            });
        },
        error: function(xhr) {
            console.error('Error loading assignees:', xhr);
        }
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

//수정부분

// 수정 모달 열기
function showUpdateModal(card) {
    // 수정할때 기본값 넣어주기
    document.getElementById('updateModal').style.display = 'flex';
    document.querySelector('#updateTitle').value = card.title;
    document.querySelector('#updateContent').value = card.content;
    document.querySelector('#updateDueDate').value = card.deadDate;

    loadAssignees('updateAssignee'); // 수정 모달의 작업자 목록 로드
    updateCardId = card.id;

}

// 할 일 추가(카드 생성)
// 카드 수정 함수
function updateAddTask() {
    var title = document.getElementById('updateTitle').value;
    var status = columnIdForFind
    var content = document.getElementById('updateContent').value;
    var dueDate = document.getElementById('updateDueDate').value;
    var assignee = document.getElementById('updateAssignee').value;

    if (!title) {
        alert("제목은 필수 항목입니다.");
        return;
    }

    // task 객체 생성
    var updateTask = {
        title: title,
        content: content,
        deadDate: dueDate ? new Date(dueDate).toISOString() : null,
        assigneeId: assignee ? parseInt(assignee) : null,
        status: status
    };

    // cardDto 객체 생성
    var updateCardDto = {
        title: title,
        content: content,
        deadDate: dueDate ? new Date(dueDate).toISOString() : null,
        assigneeId: assignee ? parseInt(assignee) : null,
        status: status,
        tasks: [updateTask]
    };

    updateCard(updateCardId,updateCardDto);
    closeModal();
}

// 카드 업데이트 함수
function updateCard(cardId, cardDto) {
    $.ajax({
        type: 'PATCH',
        url: `http://localhost:8080/cards/${cardId}`,
        contentType: 'application/json',
        data: JSON.stringify(cardDto),
        success: function(response) {
            alert("카드가 수정되었습니다.");
            location.reload(); // 페이지 새로 고침
        },
        error: function(error) {
            console.error("수정 오류:", error);
            alert("카드 수정 중 오류가 발생했습니다.");
        }
    });
}

// 모달 닫기
function closeUpdateModal() {
    document.getElementById('updateModal').style.display = 'none';
}


//칼럼 생성부분
function openCreateColumnModal() {
    const modal = document.getElementById("createColumnModal");
    modal.style.display = "block"; // 모달 열기
}

function createColumn() {
    const columnStatus = document.getElementById('columnStatus').value;
    const columnColor = document.getElementById('columnColor').value;
    const maxCards = document.getElementById('maxCards').value;

    if (!columnStatus || !columnColor) {
        alert("칼럼 명과 색은 필수 항목입니다.");
        return;
    }

    if (maxCards < -1) {
        alert("최솟값은 -1 입니다. , -1은 최대카드를 제한하지 않는 것 입니다.")
    }

    // 칼럼 데이터 객체
    const columnData = {
        columnsStatus: columnStatus,
        color: columnColor,
        maxCards: parseInt(maxCards, 10) // 문자열을 정수로 변환
    };

    // AJAX 요청
    $.ajax({
        type: 'POST',
        url: `http://localhost:8080/columns/${boardId}`, // 적절한 URL로 변경
        contentType: 'application/json',
        data: JSON.stringify(columnData),
        success: function(response) {
            alert("칼럼이 성공적으로 생성되었습니다.");
            closeCreateColumnModal(); // 모달 닫기
            location.reload(); // 새로 고침하여 업데이트된 내용을 반영
        },
        error: function(error) {
            console.error("칼럼 생성 중 오류:", error);
            alert("칼럼 생성 중 오류가 발생했습니다.");
        }
    });
}

function closeCreateColumnModal() {
    const modal = document.getElementById("createColumnModal");
    modal.style.display = "none"; // 모달 닫기
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

