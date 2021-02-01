#!/bin/bash
cd /var/lib/budgetserver
/usr/bin/java -jar /var/lib/budgetserver/budgetread.jar --budgetserverhost=$budgetserverhost --budgetserverport=$budgetserverport --inputdir=/var/lib/exchange