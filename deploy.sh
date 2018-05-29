#!/bin/bash

chmod  400 ./stage_icusin_ubuntu

ssh -T -i ./stage_icusin_ubuntu -o StrictHostKeyChecking=no root@stage.icusin.com << stage-icusin-remote

cd /home
if [ ! -d "tw-camp-peking" ];then
    git clone git@github.com:brainysoon/tw-camp-peking.git
fi

cd tw-camp-peking
git pull origin master

echo start building tw-camp-peking

./gradlew bootJar
stage-icusin-remote


ssh -T -i ./stage_icusin_ubuntu -o StrictHostKeyChecking=no root@stage.icusin.com << stage-icusin-remote

cd /home/tw-camp-peking
echo start deploy tw-camp-peking

cd docker
docker build -t peking .
docker rm -f peking-instance
docker run --name peking-instance -d -p 8000:8000 peking
stage-icusin-remote