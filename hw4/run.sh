#!/usr/bin/env bash

MEMORY="-Xms80m -Xmx80m"

GC=-XX:+UseSerialGC
echo "Starting $GC"
java $MEMORY $GC -jar target/hw4-1.0-SNAPSHOT.jar > logs/SerialGC.log

GC=-XX:+UseParallelGC
echo "Starting $GC"
java $MEMORY $GC -jar target/hw4-1.0-SNAPSHOT.jar > logs/ParallelGC.log

GC=-XX:+UseConcMarkSweepGC
echo "Starting $GC"
java $MEMORY $GC -jar target/hw4-1.0-SNAPSHOT.jar > logs/ConcMarkSweepGC.log

GC=-XX:+UseG1GC
echo "Starting $GC"
java $MEMORY $GC -jar target/hw4-1.0-SNAPSHOT.jar > logs/G1GC.log