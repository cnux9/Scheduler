# 1. Scheduler 개요
일정 관리 앱의 백엔드 스프링 부트 프로젝트입니다.

</br>

# 2. 기능
- **유저 CRUD**
- **일정 CRUD**
- **댓글 CRUD**

</br>

# 3. 기간
24/12/16 - 24/12/19

</br>

# 4. 만든 사람
### [cnux9](https://github.com/cnux9)

</br>

# 5. API Documentation
## Users 관련 API

| HTTP 메서드 | 엔드포인트            | 설명                           | 상태코드     | 요청 내용                                   | 응답 내용                       |
|-------------|-----------------------|--------------------------------|--------------|----------------------------------------------|----------------------------------|
| POST        | `/users`             | 새로운 유저를 생성한다.        | 201 Created  | **Request Body**: user_name, email            | 생성된 유저의 ID, 정보, 생성일시 |
| GET         | `/users/{userId}`    | 특정 유저 정보를 조회한다.     | 200 OK       | 없음                                         | 해당 유저의 상세 정보            |
| GET         | `/users`             | 여러 유저를 조회한다.          | 200 OK       | 없음                                         | 유저 목록                        |
| PUT         | `/users/{userId}`    | 특정 유저 정보를 수정한다.     | 200 OK       | **Request Body**: user_name, email            | 수정된 유저의 상세 정보          |
| DELETE      | `/users/{userId}`    | 특정 유저를 삭제한다.          | 200 OK       | 없음                                         | 없음(상태코드로만 응답)          |


## Tasks 관련 API

아래 예시는 질문자가 원하는 문서 형식의 예시에 해당하는 Tasks 엔드포인트에 대한 문서입니다.

| HTTP 메서드 | 엔드포인트          | 설명                                     | 상태코드       | 요청 내용                                                                             | 응답 내용                                 |
|-------------|---------------------|------------------------------------------|----------------|----------------------------------------------------------------------------------------|--------------------------------------------|
| POST        | `/tasks`            | 새로운 할 일을 생성한다.                 | 201 Created    | **Request Body**: user_id, password, content                                            | 생성된 할 일의 ID, 유저 정보, 생성일시 등 |
| GET         | `/tasks/{taskId}`   | 특정 ID의 할 일을 조회한다.              | 200 OK         | 없음                                                                                   | 해당 할 일의 상세 정보                    |
| GET         | `/tasks`            | 여러 조건(이메일, 날짜)으로 할 일을 조회한다. | 200 OK         | **Query Params**: email, date (다중 가능)                                              | 조건에 맞는 할 일 목록                    |
| PUT         | `/tasks/{taskId}`   | 특정 ID의 할 일을 수정한다.              | 200 OK         | **Request Body**: password, 수정할 content                                             | 수정된 할 일의 상세 정보                  |
| DELETE      | `/tasks/{taskId}`   | 특정 ID의 할 일을 삭제한다.              | 200 OK         | **Query Params**: password                                                             | 없음 (상태코드로만 응답)                  |


## Comments 관련 API

| HTTP 메서드 | 엔드포인트              | 설명                     | 상태코드    | 요청 내용                      | 응답 내용                      |
|-------------|-------------------------|--------------------------|-------------|--------------------------------|---------------------------------|
| POST        | `/comments`            | 새로운 댓글을 생성한다.  | 201 Created | **Request Body**: content 등    | 생성된 댓글의 ID, 정보, 생성일시 |
| GET         | `/comments/{commentId}`| 특정 댓글을 조회한다.    | 200 OK      | 없음                           | 해당 댓글의 상세 정보            |
| GET         | `/comments`            | 여러 댓글을 조회한다.    | 200 OK      | **Query Params**: 필요시 추가  | 댓글 목록                        |
| PUT         | `/comments/{commentId}`| 특정 댓글을 수정한다.    | 200 OK      | **Request Body**: content 등    | 수정된 댓글의 상세 정보          |
| DELETE      | `/comments/{commentId}`| 특정 댓글을 삭제한다.    | 200 OK      | 없음                           | 없음(상태코드로만 응답)          |
</br>

# 6. 데이터베이스 ERD
```mermaid
erDiagram
    USERS {
        bigint user_id PK
        varchar email "unique"
        varchar password
        varchar user_name
        datetime created_date_time
    }

    TASKS {
        bigint task_id PK
        datetime created_date_time
        datetime updated_date_time
        bigint user_id FK
        varchar content
    }

    COMMENTS {
        bigint comment_id PK
        datetime created_date_time
        datetime updated_date_time
        bigint user_id FK
        bigint task_id FK
        varchar content
    }

    USERS ||--o{ TASKS : "has many"
    USERS ||--o{ COMMENTS : "has many"
    TASKS ||--o{ COMMENTS : "has many"
```

</br>

# 8. 트러블 슈팅
<!-- ### 문제
1. **3 Layer의 역할 분담**: 리포지터리에서의 null값 처리와 쿼리 파라미터에 따라 달라지는 로직을 어느 레이어에서 해결해야 할지 모호하다고 생각했습니다.
2. **자료구조 선택**: 여러 개의 속성, 그리고 복수의 값을 처리하기 위해서 적절한 자료구조를 사용하는 것이 까다로웠습니다.

### 해결 방법
1. 쿼리에 따라서 null값이 나오는 것은 리포지터리의 정상 작동이라고 생각했고, 서비스에서 관련된 로직을 처리할 수 있도록 했습니다. 쿼리 파라미터에 달라지는 값 또한 통신과 검증에 관련된 컨트롤러의 역할보다 서비스의 역할에 가깝다고 생각해서 리팩토링을 진행했습니다.
2. JSON 형태로 입력되는 HTTP 메시지 바디를 MultiValueMap에 매핑되도록 만들어서 값들을 전달했습니다. 후에 속성이 추가되더라도 리포지터리를 수정해서 비교적 쉽게 확장할 수 있습니다.
-->
</br>
