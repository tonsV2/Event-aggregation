#!/usr/bin/python

import requests
import subprocess
import sys
import time

output = subprocess.Popen(["munin-run", sys.argv[1]], stdout=subprocess.PIPE).communicate()[0]

# Transform munin-run output to key/value... dict
payload = {line[0]: line[1] for line in [line.split() for line in output.splitlines()]}

reqBody = {
    "type": "munin-run-%s" % sys.argv[1],
    "timestamp": time.time(),
    "objectId": "laptop-something-something",
    "payload": payload
}

# TODO: url as command line argument...
url = 'http://localhost:8080/events'
headers = {'content-type': 'application/json'}

response = requests.post(url, json=reqBody, headers=headers)

print response
