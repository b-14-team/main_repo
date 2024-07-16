# 🐺Work-Flow



## 🖥️ 프로젝트 소개
- 한 줄 정리 : 협업의 흐름을 한번에 파악 가능한 칸반 보드 서비스
- 내용 : 보드를 사용하여 작업의 흐름을 정리하고 협업을 좀 더 효율적으로 만들어주는 엄청난 서비스입니다.

## 🕰️ 개발 기간
* 24.07.10일 - 24.07.16일

### 🧑‍🤝‍🧑 맴버구성
- 팀장  : 류정근 - 카드CRUD , 프론트
- 팀원 : 석현호 - 보드CRUD , Notion,Readme 정리
- 팀원 : 임애림 - 컬럼CRUD , 발표자료(PPT)
- 팀원 : 서찬원 - 시큐리티 및 USER , 쿼리 최적화
- 팀원 : 양소영 - 댓글CRUD , 프론트 , 발표


### ⚙️ 기술 스택

| Type      | Tech                                                                                                                                                                                                                                                                                                                                                |
| ---------- |-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| IDE        | ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)                                                                                                                                                                                                                       |
| Framework        | ![Spring](https://img.shields.io/badge/SpringBoot-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)                                                                                                                                                                                                                                    |
| Langage      | ![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E) ![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white) ![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)   ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)    |
| Database   | ![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)                                                                                                                                                                                                                                              |
| Tools   | ![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)  ![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)                                                                                                                                   |

##  📩 Commit Rule

> ex)  
> [#1] ✨ feat : 초기 세팅

| 작업 타입       | 작업내용            |
|-------------|-----------------|
| ✨ feat      | 없던 파일을 생성, 초기세팅 |
| 🐛 Fix      | 버그 수정           |
| ♻️ Refactor | 코드 리팩토링         |
| 🍻 Test      | 테스트 코드를 작성      |


##  ❗ Code Convention
<details>
<summary>Code Convention</summary>
<p>

![코드 컨벤션1](https://github.com/user-attachments/assets/f372de89-d70e-4160-9ece-908398d685b3)|![코드 컨벤션 2](https://github.com/user-attachments/assets/ba0d4aa8-78b6-4486-a56e-8af4af2b2315)|
|:---:|:---:|
|![코드 컨벤션 3](https://github.com/user-attachments/assets/9cfdc8ff-195d-409b-833e-d2d7b16213ab)|![코드 컨벤션4](https://github.com/user-attachments/assets/449181b1-afd3-4b91-8cfe-50221e44216f)|

<p>
</details>




##  와이어프레임
![image](https://github.com/b-14-team/main_repo/assets/161789810/feb83e5e-1c67-4fc5-87c6-e63970e0a3b8)

## ERD
![화면 캡처 2024-07-15 233950](https://github.com/user-attachments/assets/1f70f7b8-d5ff-491c-a22d-70feafb4141f)

## 최적화
- 프로그램 성능을 높이기 위해 시도한 것

### 쿼리 최적화

<details>
<summary>예시 1) 컬럼 ID + 담당자 ID 해당하는 카드 조회</summary>

- 환경 : 400만 카드 데이터
- 찾은 데이터 수 4 row
- 조건 : 컬럼 ID + 담당자 ID
- 최적화 방법 : 인덱싱
![img_2.png](images/img_2.png)
- 비교 : 
- 최적화 전
![img_3.png](images/img_3.png)
- 작업 시간 **3s 492 ms**
- 최적화 후
![img_1.png](images/img_1.png)
- 작업 시간 **24ms**
- 
</details>

<details>

<summary>예시 2) 담당자 ID로 해당하는 카드 조회</summary>

- 환경 : 400만 카드 데이터
- 찾은 데이터 수 4 row
- 조건 : 담당자 ID
- 최적화 방법 : 인덱싱
![img_4.png](images/img_4.png)
- 비교 :
- 최적화 전
![img_5.png](images/img_5.png)
- 작업 시간 **281 ms**
- 최적화 후
![img_6.png](images/img_6.png)
- 작업 시간 **18ms**

</details>

<p>