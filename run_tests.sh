#!/bin/bash

RED='\033[0;31m'
GREEN='\033[0;32m'
RESET='\033[0m'
BOLD='\033[1m'

let total=0;
let correct=0;
export CLASSPATH=./prr-app/prr-app.jar:./prr-core/prr-core.jar:./po-uilib/po-uilib.jar:.
make -s tests;
for x in tests/*.in; do
    if [ -e ${x%.in}.import ]; then
        java -Dimport=${x%.in}.import -Din=$x -Dout=${x%.in}.outhyp prr.app.App;
    else
        java -Din=$x -Dout=${x%.in}.outhyp prr.app.App;
    fi

    colordiff -cB -w ${x%.in}.out ${x%.in}.outhyp > ${x%.in}.diff ;
    if [ -s ${x%.in}.diff ]; then
        test=$test"${RED}FAIL: $x: See file ${x%.in}.diff\n${RESET}"
    else
        test=$test"${GREEN}CORRECT: $x\n${RESET}"
        let correct++;
        rm -f ${x%.in}.diff ${x%.in}.outhyp ; 
    fi
    let total++;
done

rm -f saved*

let res=100*$correct/$total;
let failures=total-correct;
echo ""
printf "$test"
echo ""
echo "Total Tests =" $total 
echo -e "${GREEN}Passed Tests${RESET} =" $correct
echo -e "${RED}Failed Tests${RESET} =" $failures
echo -e "${BOLD}Result${RESET} =" $res"%"

make -s clean;

