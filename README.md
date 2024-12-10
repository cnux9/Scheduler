# 1. Scheduler개요
일정 관리 앱의 백엔드 스프링 부트 프로젝트입니다.

</br>

# 2. 기능
- **할일 작성**: 할일을 작성합니다.(사용자의 id값이 없어도 익명으로 생성가능)
- **할일 단건 조회**: 할일을 ID로 조회할 수 있습니다.
- **할일 다건 조회**: 복수의 속성과 값들을 이용해서 날짜와 이름 등의 조건에 맞는 할일을 조회할 수 있습니다.
- **할일 수정**: 작성자 이름과 할일 내용을 수정합니다.
- **할일 제거**: 할일을 제거합니다.
</br>

# 3. 기간
24/12/06 - 24/12/10

</br>

# 4. 만든 사람
### [cnux9](https://github.com/cnux9)

</br>

# 5. 실행 예시
## **할일 생성**
```
POST localhost:8080/tasks
{
    "user_id": 3,
    "password": "1234",
    "content": "계란, 우유, 밤티라미수 사오기"
}
```
```
201 Created
{
    "taskId": 26,
    "userId": 3,
    "userName": "김인선",
    "email": "sun@kbs.co.kr",
    "content": "계란, 우유, 밤티라미수 사오기",
    "createdDateTime": "2024-12-10T15:55:28",
    "updatedDateTime": "2024-12-10T15:55:28"
}
```
## **할일 단건 조회**
```
GET localhost:8080/tasks/26
```
```
200 OK
{
    "taskId": 23,
    "userId": 0,
    "userName": null,
    "email": null,
    "content": "변경이오",
    "createdDateTime": "2024-12-09T18:55:20",
    "updatedDateTime": "2024-12-09T19:04:58"
}
```
## **할일 다건 조회**
```
GET localhost:8080/tasks?email=sun@kbs.co.kr&date=2024-12-08&date=2024-12-10
```
```
200 OK
[
    {
        "taskId": 26,
        "userId": 3,
        "userName": "김인선",
        "email": "sun@kbs.co.kr",
        "content": "계란, 우유, 밤티라미수 사오기",
        "createdDateTime": "2024-12-10T15:55:28",
        "updatedDateTime": "2024-12-10T15:55:28"
    },
    {
        "taskId": 4,
        "userId": 3,
        "userName": "김인선",
        "email": "sun@kbs.co.kr",
        "content": "블리츠크랭크 놀리기",
        "createdDateTime": "2024-12-08T13:46:22",
        "updatedDateTime": "2024-12-08T13:46:22"
    }
]
```
## **할일 수정**
```
PUT localhost:8080/tasks/26
```
```
200 OK
{
    "taskId": 26,
    "userId": 3,
    "userName": "김인선",
    "email": "sun@kbs.co.kr",
    "content": "밤티라미수만 사오기",
    "createdDateTime": "2024-12-10T15:55:28",
    "updatedDateTime": "2024-12-10T16:02:39.6938752"
}
```
## **할일 제거**
```
DELETE localhost:8080/tasks/26?password=1234
```
```
200 OK
```
</br>

# 6. 트러블 슈팅

### 문제 - 3 Layer의 역할 분담
1. 리포지터리에서의 null값 처리와 쿼리 파라미터에 따라 달라지는 로직을 어느 레이어에서 해결해야 할지 모호하다고 생각했습니다.
2. 여러 개의 속성, 그리고 복수의 값을 처리하기 위해서 적절한 자료구조를 사용하는 것이 까다로웠습니다.

### 해결 방법
1. 쿼리에 따라서 null값이 나오는 것은 리포지터리의 정상 작동이라고 생각했고, 서비스에서 관련된 로직을 처리할 수 있도록 했습니다. 쿼리 파라미터에 달라지는 값 또한 통신과 검증에 관련된 역할을 하는 컨트롤러보다 서비스의 역할에 가깝다고 생각해서 리팩토링을 진행했습니다.
2. JSON 형태로 입력되는 HTTP 메시지 바디를 MultiValueMap에 매핑되도록 만들어서 값들을 전달했습니다. 후에 속성이 추가되더라도 리포지터리를 수정해서 확장할 수 있습니다.

</br>
