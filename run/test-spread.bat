cd /d %~dp0..
call mvn clean package -U -DskipTests

call autoconfig %~dp0..\target\palace-web.war -u %~dp0test-spread.properties
@pause