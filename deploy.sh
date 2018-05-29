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

./gradlew bootRun
stage-icusin-remote