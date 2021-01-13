#!/bin/bash

es_url=http://10.60.132.122:9200
index=sportradar_v3_playerprofile_copy
cat "$es_url/$index/_search?scroll=1m&_source=false"
response=$(curl -s "$es_url/$index/_search?scroll=1m&_source=false" -d @query.json -H "Content-Type: application/json")

scroll_id=$(echo $response | jq -r ._scroll_id)
hits_count=$(echo $response | jq -r '.hits.hits | length')
hits_so_far=hits_count
echo Got initial response with $hits_count hits and scroll ID $scroll_id

# TODO process first page of results here

while [ "$hits_count" != "0" ]; do
  response=$(curl  -H "Content-Type: application/json" -s "$es_url/_search/scroll" -d "{ \"scroll\": \"1m\", \"scroll_id\": \"$scroll_id\"}")
  scroll_id=$(echo $response | jq -r ._scroll_id)
  hits_count=$(echo $response | jq -r '.hits.hits | length')
  hits_so_far=$((hits_so_far + hits_count))
  echo "Got response with $hits_count hits (hits so far: $hits_so_far), new scroll ID $scroll_id"
  for (( i = 0; i < $hits_count; i++ )); do
      echo $response | jq -r ".hits.hits[$i]._id">>logs.txt
  done


  # TODO process page of results
done

echo Done!