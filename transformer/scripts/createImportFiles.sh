#!/bin/bash

cd /home/richard/bin
myhome=/home/richard
javaexec="java  -classpath /home/richard/bin/transformer/target -jar /home/richard/bin/transformer/target/transformer-0.0.1-SNAPSHOT.jar"
for dat in `ls $myhome/netto/*.loadbalancer2  2>/dev/null`
do
  echo "Datei ist $dat"
  $javaexec $dat /tmp/netto.txt netto http://192.168.2.28:8090  >> $myhome/logs/log.txt 2>> $myhome/logs/err.txt
  mv $dat $myhome/netto/old
  sleep 10
done
for dat in `ls $myhome/edeka/*.loadbalancer2 2>/dev/null`
do
  echo "Datei ist $dat"
  $javaexec $dat /tmp/edeka.txt edeka http://192.168.2.28:8090  >> $myhome/logs/log.txt 2>> $myhome/logs/err.txt
  mv $dat $myhome/edeka/old
  sleep 10
done
for dat in `ls $myhome/mintos/*.loadbalancer2 2>/dev/null`
do
  echo "Datei ist $dat"
  $javaexec $dat /tmp/mintos.txt mintos http://192.168.2.28:8090  >> $myhome/logs/log.txt 2>> $myhome/logs/err.txt
  mv $dat $myhome/mintos/old
  sleep 10
done
for dat in `ls $myhome/viainvest/*.loadbalancer2 2>/dev/null`
do
  echo "Datei ist $dat"
  $javaexec $dat /tmp/viainvest.txt viainvest http://192.168.2.28:8090  >> $myhome/logs/log.txt 2>> $myhome/logs/err.txt
  mv $dat $myhome/viainvest/old
  sleep 10
done
