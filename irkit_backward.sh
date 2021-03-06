#!/bin/bash

#if [ $# -lt 1 ]
#then
#	echo "[USAGE] $0 [FILE]"
#	exit 1
#fi

IRKIT=192.168.0.19

BR="65535,0"
HEAD="{\"format\":\"raw\",\"freq\":38,\"data\":"
HEAD="${HEAD}[1073,5794,1073,2813,1073,873,1073,2813,1073,904,1073,904,1073,2813,1073,873,1073,873,1073,873,1073,873,1073,2813,1073,2813,1073,2813,1073"
HEAD="${HEAD},${BR},${BR},${BR},13693,1073,5794,1073,2813,1073,873,1073,2813,1073,873,1073,873,1073,2813,1073,873,1073,873,1073,873,1073,873,1073,2813,1073,2813,1073,2813,1073"

CD="1073,5794,1073,2813,1073,873,1073,2813,1073,2813,1073,2813,1073,2813,1073,2813,1073,2813,1073,2813,1073,2813,1073,2813,1073,904,1073,787,1073"

S0="${BR},${BR},${BR},48898,${CD}"
S1="${BR},${BR},${BR},4107,${CD}"
S2="${BR},${BR},${BR},4554,${CD}"

I1="${BR},${BR},${BR},3704,${CD}"

B0="${BR},${BR},${BR},9707,${CD}"
B1="${BR},${BR},${BR},3579,${CD}"
B2="${BR},${BR},${BR},4400,${CD}"
B3="${BR},${BR},${BR},4107,${CD}"

#DATA="'${HEAD},${S0},${S1},${S2},${S3},${S1},${S2},${S3},${S2},${S3},${S2}]}'"
DATA="'${HEAD},${B0},${B1},${B2},${I1},${B3},${B2},${I1},${B3},${B2},${I1}]}'"

echo "${DATA}"
curl -i "http://${IRKIT}/messages" -d ${DATA}
