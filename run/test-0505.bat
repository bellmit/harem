cd /d %~dp0..
call mvn clean package -DskipTests

call autoconfig %~dp0..\target\palace-web.war -u %~dp0test-0505.properties
@pause