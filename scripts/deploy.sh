#!/bin/bash

REPOSITORY=/home/ubuntu/Hooligans_Server
cd $REPOSITORY

DATE=$(date)
APP_NAME=Hooligans_Server
PROJECT_ROOT="/home/ubuntu/Hooligans_Server"
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep 'SNAPSHOT.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

BUILD_LOG="$PROJECT_ROOT/application.log"

CURRENT_PID=$(pgrep -f $APP_NAME)
echo "> start $DATE \n" >> $BUILD_LOG

if [ -z $CURRENT_PID ]
then
  echo "> 종료할 애플리케이션이 없습니다. \n" >> $BUILD_LOG
else
  echo "> kill -9 $CURRENT_PID \n" >> $BUILD_LOG
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> Deploy - $JAR_PATH \n" >> $BUILD_LOG
nohup java -jar $JAR_PATH > $REPOSITORY/nohup.out 2>&1 &
