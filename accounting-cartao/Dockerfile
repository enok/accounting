FROM openjdk:8

RUN mkdir /cartao
RUN mkdir /cartao/arquivos
RUN mkdir /cartao/logs

ADD target/accounting-cartao.jar /cartao/accounting-cartao.jar

EXPOSE 9090
ENTRYPOINT ["java", "-jar", "/cartao/accounting-cartao.jar"]