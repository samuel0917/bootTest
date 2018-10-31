java -jar BootTest-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=test 表示使用测试环境的配置

java -jar BootTest-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod 表示使用生产环境的配置

mvn clean install -DskipTests -Ptest  pom里面profiles分环境就加-p