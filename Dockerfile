# 1단계: 빌드 단계
FROM gradle:7.6.0-jdk11 AS builder
WORKDIR /home/gradle/project

# 소스 코드와 gradlew 파일, gradle 설정 파일들을 컨테이너로 복사합니다.
COPY --chown=gradle:gradle . .

# gradlew 파일에 실행 권한 부여
RUN chmod +x gradlew

# Gradle Wrapper를 사용하여 프로젝트를 빌드합니다.
RUN ./gradlew clean build --no-daemon

# 2단계: 실행 단계
FROM openjdk:11-jre-slim
WORKDIR /app

# 빌드 단계에서 생성된 jar 파일을 복사합니다.
COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar

# 컨테이너가 노출할 포트를 지정합니다.
EXPOSE 8080

# 컨테이너 시작 시 Spring Boot 애플리케이션을 실행합니다.
ENTRYPOINT ["java", "-jar", "app.jar"]
