#!/bin/bash

./gradlew test

chmod  400 ./pro_icusin_ubuntu

ssh -T -i ./pro_icusin_ubuntu -o StrictHostKeyChecking=no root@icusin.com << pro-icusin-remote
cd /home
if [ ! -d "tw-camp-peking" ];then
    git clone git@github.com:brainysoon/tw-camp-peking.git
fi

cd tw-camp-peking
git pull origin master
echo running automation test...
newman run dmallQuiz.postman_collection.json
pro-icusin-remote
