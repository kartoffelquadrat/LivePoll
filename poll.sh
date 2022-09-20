#!/bin/bash

function startPoll
{
URL=$(curl -X POST http://127.0.0.1:8361/polls    -H "Content-Type: application/json" -d "{ \"topic\": \"$1\", \"options\": [ \"$2\", \"$3\", \"$4\"]}")
open http://127.0.0.1:8361/polls/$URL
}

java -jar ~/Code/LivePoll/target/LivePoll.jar &

read -p "Press enter to open next poll"
startPoll "Which interaction step is commonly not enumerated" "Base Step" "Ref. to lower level" "Context Info"

read -p "Press enter to open next poll"
startPoll "Which use case granularity can be described as: Sparkles joy" "Summary" "User Goal" "Subfunction"

read -p "Press enter to open next poll"
startPoll "A phone receptionist is best described as" "Primary Actor" "Secondary Actor" "Facilit. Actor"

read -p "Press enter to open next poll"
startPoll "I have already followed the IDE setup / best practice screencast" "Yes" "Only watched" "No" 

