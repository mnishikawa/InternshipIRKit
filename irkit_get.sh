#!/bin/bash

if [ $# -lt 1 ]
then
	echo "[USAGE] $0 [FILE]"
	exit 1
fi

FILE=$1
IRKIT=192.168.0.19

echo "${DATA}"
curl -i "http://${IRKIT}/messages" > ${FILE}
cat ${FILE}
