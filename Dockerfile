FROM openjdk
COPY build/libs/ArticleManager-0.0.1-SNAPSHOT.jar /articles.jar
EXPOSE 1125
CMD ["java", "-jar", "/articles.jar"]