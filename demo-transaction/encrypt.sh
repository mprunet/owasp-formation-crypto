#!/bin/bash
. ./ip.sh
if [ "z$1" == "z" ] ; then
	echo "Usage : $0 <filename>"
	exit 1
fi
if [ ! -f $1 ] ; then
	echo "Fichier $1 introuvable"
	exit 1
fi
curl -X POST "http://$SERVER_IP:8080/api/chiffrer-fichier" -H  "accept: text/plain" -H  "Content-Type: multipart/form-data" -F "file=@$1;type=text/plain"

