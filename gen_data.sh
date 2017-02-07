#!/usr/bin/env bash

curl -H "Content-Type: application/json" --request POST 'http://localhost:8080/events' -d '{"type": "CLICK_EVENT", "timestamp": 1232434, "indexId": "image1id154", "payload": {"lat":"14","long":"15", "some": "value", "other": "value"} }'
curl -H "Content-Type: application/json" --request POST 'http://localhost:8080/events' -d '{"type": "CLICK_EVENT", "timestamp": 1232434, "indexId": "image1id154", "payload": {"lat":"14","long":"15", "some": "value", "other": "value"} }'

curl -H "Content-Type: application/json" --request POST 'http://localhost:8080/events' -d '{"type": "CLICK_EVENT", "timestamp": 1232434, "indexId": "image1id154", "payload": {"lat":"11","long":"77", "some": "value", "other": "value"} }'
curl -H "Content-Type: application/json" --request POST 'http://localhost:8080/events' -d '{"type": "CLICK_EVENT", "timestamp": 1232434, "indexId": "image1id154", "payload": {"lat":"14","long":"77", "some": "value", "other": "value"} }'

curl -H "Content-Type: application/json" --request POST 'http://localhost:8080/events' -d '{"type": "CLICK_EVENT", "timestamp": 1232434, "indexId": "image1id12254", "payload": {"lat":"11","long":"77", "some": "value", "other": "value"} }'
curl -H "Content-Type: application/json" --request POST 'http://localhost:8080/events' -d '{"type": "CLICK_EVENT", "timestamp": 1232434, "indexId": "image1id12254", "payload": {"lat":"11","long":"77", "some": "value", "other": "value"} }'
curl -H "Content-Type: application/json" --request POST 'http://localhost:8080/events' -d '{"type": "CLICK_EVENT", "timestamp": 1232434, "indexId": "image1id12254", "payload": {"lat":"11","long":"77", "some": "value", "other": "value"} }'
curl -H "Content-Type: application/json" --request POST 'http://localhost:8080/events' -d '{"type": "CLICK_EVENT", "timestamp": 1232434, "indexId": "image1id12254", "payload": {"lat":"11","long":"77", "some": "value", "other": "value"} }'
curl -H "Content-Type: application/json" --request POST 'http://localhost:8080/events' -d '{"type": "CLICK_EVENT", "timestamp": 1232434, "indexId": "image1id12254", "payload": {"lat":"11","long":"77", "some": "value", "other": "value"} }'

#####################################################################

curl -H "Content-Type: application/json" --request POST 'http://localhost:8080/events' -d '{"type": "HEARTBEAT_EVENT", "timestamp": 1232434, "indexId": "uid1212", "payload": {"timestamp": "1"} }'
curl -H "Content-Type: application/json" --request POST 'http://localhost:8080/events' -d '{"type": "HEARTBEAT_EVENT", "timestamp": 1232434, "indexId": "uid1212", "payload": {"timestamp": "5"} }'
curl -H "Content-Type: application/json" --request POST 'http://localhost:8080/events' -d '{"type": "HEARTBEAT_EVENT", "timestamp": 1232434, "indexId": "uid1212", "payload": {"timestamp": "9"} }'
curl -H "Content-Type: application/json" --request POST 'http://localhost:8080/events' -d '{"type": "HEARTBEAT_EVENT", "timestamp": 1232434, "indexId": "uid1212", "payload": {"timestamp": "17"} }'
curl -H "Content-Type: application/json" --request POST 'http://localhost:8080/events' -d '{"type": "HEARTBEAT_EVENT", "timestamp": 1232434, "indexId": "uid1212", "payload": {"timestamp": "21"} }'

curl -H "Content-Type: application/json" --request POST 'http://localhost:8080/events' -d '{"type": "HEARTBEAT_EVENT", "timestamp": 1232434, "indexId": "uid12", "payload": {"timestamp": "1"} }'
curl -H "Content-Type: application/json" --request POST 'http://localhost:8080/events' -d '{"type": "HEARTBEAT_EVENT", "timestamp": 1232434, "indexId": "uid12", "payload": {"timestamp": "5"} }'
curl -H "Content-Type: application/json" --request POST 'http://localhost:8080/events' -d '{"type": "HEARTBEAT_EVENT", "timestamp": 1232434, "indexId": "uid12", "payload": {"timestamp": "9"} }'
curl -H "Content-Type: application/json" --request POST 'http://localhost:8080/events' -d '{"type": "HEARTBEAT_EVENT", "timestamp": 1232434, "indexId": "uid12", "payload": {"timestamp": "17"} }'
curl -H "Content-Type: application/json" --request POST 'http://localhost:8080/events' -d '{"type": "HEARTBEAT_EVENT", "timestamp": 1232434, "indexId": "uid12", "payload": {"timestamp": "21"} }'

curl -H "Content-Type: application/json" --request POST 'http://localhost:8080/events' -d '{"type": "HEARTBEAT_EVENT", "timestamp": 1232434, "indexId": "uid12", "payload": {"timestamp": "101"} }'
curl -H "Content-Type: application/json" --request POST 'http://localhost:8080/events' -d '{"type": "HEARTBEAT_EVENT", "timestamp": 1232434, "indexId": "uid12", "payload": {"timestamp": "105"} }'
curl -H "Content-Type: application/json" --request POST 'http://localhost:8080/events' -d '{"type": "HEARTBEAT_EVENT", "timestamp": 1232434, "indexId": "uid12", "payload": {"timestamp": "109"} }'
curl -H "Content-Type: application/json" --request POST 'http://localhost:8080/events' -d '{"type": "HEARTBEAT_EVENT", "timestamp": 1232434, "indexId": "uid12", "payload": {"timestamp": "117"} }'
curl -H "Content-Type: application/json" --request POST 'http://localhost:8080/events' -d '{"type": "HEARTBEAT_EVENT", "timestamp": 1232434, "indexId": "uid12", "payload": {"timestamp": "121"} }'
