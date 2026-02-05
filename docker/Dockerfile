FROM bellsoft/liberica-openjdk-alpine:17

ENV APP_HOME=/app
WORKDIR ${APP_HOME}

ENV TZ=Asia/Seoul
RUN apk add --no-cache tzdata \
    && echo "${TZ}" > /etc/timezone \
    && ln -sf /usr/share/zoneinfo/${TZ} /etc/localtime

RUN mkdir /uploads && chmod 777 /uploads

COPY build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=product", "-jar", "app.jar"]