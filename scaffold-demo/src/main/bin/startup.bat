@echo off
chcp 65002
title "car-server"
echo 'Ready to running the I/O service...'
echo 'Startup the I/O Service.'
java -da -Xss256k -XX:MaxPermSize=64m -XX:+UseParallelGC -XX:ParallelGCThreads=4 -XX:+UseParallelOldGC -XX:+UseAdaptiveSizePolicy -Ddubbo.container=logback,spring -Ddubbo.application.logger=slf4j -cp ./config;./lib/* com.alibaba.dubbo.container.Main
pause