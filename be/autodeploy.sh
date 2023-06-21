#!/bin/bash

KIOSK_ID=$(jps | grep be | awk '{ print $1 }')

if [ -z $KIOSK_ID ]; then
  echo "동작중인 서버가 없습니다."
else
  echo "$KIOSK_ID 프로세스를 삭제합니다."
  kill -9 $KIOSK_ID
fi

echo "서버 시작"
nohup java -jar ~/app/build/libs/be-0.0.1-SNAPSHOT.jar >/home/ubuntu/app/log.txt 2>&1 &
echo "배포 완료!"
