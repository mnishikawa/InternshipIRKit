#!/bin/bash

#if [ $# -lt 1 ]
#then
#	echo "[USAGE] $0 [FILE]"
#	exit 1
#fi

IRKIT=192.168.0.19

BR="65535,0"
HEAD="{\"format\":\"raw\",\"freq\":38,\"data\":"
HEAD="${HEAD}[1073,5794,1073,2813,1073,873,1073,2813,1073,904,1073,904,1073,2813,1073,904,1073,904,1073,904,1073,2813,1073,873,1073,873,1073,787,1073"
CD="1073,5794,1073,2813,1073,873,1073,2813,1073,2813,1073,2813,1073,2813,1073,2813,1073,2813,1073,2813,1073,2813,1073,2813,1073,904,1073,787,1073"
S0="${BR},${BR},${BR},48898,${CD}"
S1="${BR},${BR},${BR},4107,${CD}"
S2="${BR},${BR},${BR},4554,${CD}"
S3="${BR},${BR},${BR},3704,${CD}"

DATA="'${HEAD},${S0},${S1},${S2},${S3},${S1},${S2},${S3},${S2},${S3},${S2}]}'"

echo "${DATA}"
curl -i "http://${IRKIT}/messages" -d ${DATA}
