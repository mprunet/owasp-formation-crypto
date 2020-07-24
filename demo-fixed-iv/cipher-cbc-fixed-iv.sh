#!/bin/bash
. ../demo-transaction/ip.sh
curl -X POST "http://$SERVER_IP:8080/api/encrypt/aes-cbc-fixed-iv" -H  "accept: */*" -H  "Content-Type: multipart/form-data" -F "file=@$1;type=application/binary"
