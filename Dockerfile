FROM jkremser/mini-jre:8.1

ENV JAVA_OPTS="-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap"

LABEL BASE_IMAGE="jkremser/mini-jre:8"

ADD build/libs/haskell-example-operator-*-all.jar /haskell-example-operator.jar

CMD ["/usr/bin/java", "-jar", "/haskell-example-operator.jar"]
