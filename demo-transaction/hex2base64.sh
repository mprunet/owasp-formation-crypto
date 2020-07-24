#!/usr/bin/python
import base64
import sys
#print base64.urlsafe_b64decode(sys.argv[1]).encode('hex')
print base64.urlsafe_b64encode(sys.argv[1].decode('hex'))

