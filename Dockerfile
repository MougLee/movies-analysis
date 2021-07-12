FROM openjdk:14-alpine
LABEL version="0.1"

ENV SCALA_VERSION=2.12.14 \
    SCALA_HOME=/usr/share/scala \
    PATH="/usr/local/sbt/bin:$PATH"

RUN apk update && apk add --no-cache bash vim wget openssh

RUN cd "/tmp" && \
    wget --no-verbose "https://downloads.typesafe.com/scala/${SCALA_VERSION}/scala-${SCALA_VERSION}.tgz" && \
    tar xzf "scala-${SCALA_VERSION}.tgz" && \
    mkdir "${SCALA_HOME}" && \
    rm "/tmp/scala-${SCALA_VERSION}/bin/"*.bat && \
    mv "/tmp/scala-${SCALA_VERSION}/bin" "/tmp/scala-${SCALA_VERSION}/lib" "${SCALA_HOME}" && \
    ln -s "${SCALA_HOME}/bin/"* "/usr/bin/" && \
    rm -rf "/tmp/"*

WORKDIR /app

RUN mkdir -p "/usr/local/sbt" && wget -qO - --no-check-certificate "https://github.com/sbt/sbt/releases/download/v1.5.4/sbt-1.5.4.tgz" | \
        tar xz -C /usr/local/sbt --strip-components=1 && sbt sbtVersion
