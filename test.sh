#!/bin/bash

./gradlew test

chmod  400 ./stage_icusin_ubuntu

ssh -T -i ./stage_icusin_ubuntu -o StrictHostKeyChecking=no root@stage.icusin.com << stage-icusin-remote
cd /home
if [ ! -d "tw-camp-peking" ];then
    git clone git@github.com:brainysoon/tw-camp-peking.git
fi

cd tw-camp-peking
echo running automation test...
newman run dmallQuiz.postman_collection.json
stage-icusin-remote
