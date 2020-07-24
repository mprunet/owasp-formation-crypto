#!/bin/bash
. ./ip.sh
skip=1
if [ "z$1" == "z" ] ; then
	echo "Usage : $0 <filename>"
	exit 1
fi
if [ ! -f $1 ] ; then
	echo "Fichier $1 introuvable"
	exit 1
fi
input="$1"
while IFS= read var; do
	if [ $skip -eq 0 ]; then
		curl -X POST "http://$SERVER_IP:8080/api/record" -H  "accept: */*" -F "transaction=$var" 
	else
		skip=0
	fi
done < "$input"

