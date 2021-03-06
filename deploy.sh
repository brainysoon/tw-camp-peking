#!/bin/bash

chmod  400 ./stage_icusin_ubuntu

ssh -T -i ./stage_icusin_ubuntu -o StrictHostKeyChecking=no root@stage.icusin.com << stage-icusin-remote
echo start building tw-camp-peking
cd /home
if [ ! -d "tw-camp-peking" ];then
    git clone git@github.com:brainysoon/tw-camp-peking.git
fi

cd tw-camp-peking
git pull origin master

./gradlew bootJar
stage-icusin-remote


ssh -T -i ./stage_icusin_ubuntu -o StrictHostKeyChecking=no root@stage.icusin.com << stage-icusin-remote

cd /home/tw-camp-peking
cp build/libs/peking-0.0.1-SNAPSHOT.jar docker/peking.jar
echo start deploy tw-camp-peking

cd docker
docker build -t peking .
docker rm -f peking-instance
docker run --name peking-instance --link peking-mysql:mysql -d -p 8000:8000 peking
stage-icusin-remote