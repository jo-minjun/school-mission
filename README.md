# school-mission

**학생 성적 관리 시스템 구축 미션**

* 아래의 API 를 구현하는 미션
  * 학생 조회, 추가
  * 과목 조회, 추가
  * 특정 학생, 특정 과목에 점수 할당 및 수정, 삭제
  * 특정 학생의 평균 점수 조회
  * 특정 과목의 평균 점수 조회

___
<br>


## 목차

0. 사용 스택
1. 실행 방법
2. 코드 구조
3. ERD
4. 테스트 코드 커버리지

___
<br>


## 0. 사용 스택

* Spring Boot 2.7.0
* JDK 11
* Spring Data JPA
* H2 Database
* Lombok

<br>

## 1. 실행 방법

1. H2 데이터베이스 다운로드: http://www.h2database.com/
2. git clone
3. H2 데이터베이스 실행
   * bin -> **./h2.sh**
   * URL 호스트를 **localhost**로 변경
   * **cd ~** 에서 **touch school-mission** 명령어
   * 설정: **Generic H2 (Embedded)**
   * JDBC URL: **jdbc:h2:tcp://localhost/~/school-mission**
4. 프로젝트 실행 및 Postman 사용

<br>

## 2. 코드 구조

### 전체 패키지 구조

```none
├─java
│    └─jun
│         └─schoolmission
│              ├─common
│              │    ├─annotation
│              │    │    └─validator
│              │    └─exception
│              ├─controller
│              ├─domain
│              │    ├─dto
│              │    |    ├─score
│              │    |    ├─student
│              │    |    └─subject
│              │    ├─entity
│              │    └─repository
│              └─service
│                   └─impl
└─resources
```

<br>

### 패키지 설명

* **common**: 프로젝트에서 전역적으로 사용되는 패키지
  * **annotation**: 어노테이션을 관리하는 패키지
    * **validator**: 어노테이션의 constraint를 검증하는 패키지
  * **exception**: exception을 관리하는 패키지

___

* **controller**: 컨트롤러를 관리하는 패키지

___

* **domain**: 엔티티와 DTO 등을 관리하는 패키지

  * **dto**: DTO를 관리하는 패키지
    * **score**: Score 엔티티 관련 DTO 패키지
    * **student**: Student 엔티티 관련 DTO 패키지
    * **subject**: Subject 엔티티 관련 DTO 패키지

  * **entity**: JPA 엔티티를 관리하는 패키지

  * **repository**: entity의 JPA Repository를 관리하는 패키지

___

* **service**: service를 관리하는 패키지
  * **impl**: service 인터페이스에 대한 구현체를 모아놓은 패키지

<br>

## 3. ERD

![school-mission-erd](./img/school-mission-erd.png)

<br>

## 4. 테스트 코드 커버리지

* 총 **109 개**의 테스트

  ![테스트_코드_케이스](./img/테스트_코드_케이스.png)

* 클래스 커버리지: **100%**

* 메소드 커버리지: **93%**

* 라인 커버리지: **94%**

  ![테스트_코드_커버리지](./img/테스트_코드_커버리지.png)
