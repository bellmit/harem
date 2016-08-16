cd /d %~dp0..
call mvn package -DskipTests

call autoconfig %~dp0..\target\palace-web.war -u %~dp0test.properties
@pause