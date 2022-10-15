#!/bin/bash

RED='\033[0;31m'
GREEN='\033[0;32m'
RESET='\033[0m'
BOLD='\033[1m'

tests="./tests"

silent_diff() {
    cmp --silent "$1" "$2"
}

normal_diff() {
    colordiff -cB -w "$1" "$2"
}

DIFF="silent_diff"

while getopts ":dvch" OPTION; do
    case "$OPTION" in
        d)
            DIFF="normal_diff"
            ;;
        *)
            exit 1
            ;;
    esac
done

let total=0;
let correct=0;
export CLASSPATH=./prr-app/prr-app.jar:./prr-core/prr-core.jar:./po-uilib/po-uilib.jar:.
make -s tests;
for x in "$tests/"*.in; do
    test_name="$(basename -s .in "$x")"
    in_file="./tests/${test_name}.in"
    import_file="./tests/${test_name}.import"
    actual_output_file="./tests/${test_name}.outhyp"
    expected_output_file="./tests/${test_name}.out"
    diff_file="./tests/${test_name}.diff"

    if [ -e ${import_file} ]; then
        java -Dimport=${import_file} -Din=${in_file} -Dout=${actual_output_file} prr.app.App;
    else
        java -Din=${in_file} -Dout=${actual_output_file} prr.app.App;
    fi

    diff -cB -w ${expected_output_file} ${actual_output_file} > ${diff_file} ;
    "$DIFF" "$expected_output_file" "$actual_output_file"
    
    if [ -s ${diff_file} ]; then
        test=$test"${RED}FAIL: ${in_file}: See file ${diff_file}\n${RESET}"
    else
        test=$test"${GREEN}CORRECT: ${in_file}\n${RESET}"
        let correct++;
        rm -f ${diff_file} ${actual_output_file} ; 
    fi
    let total++;
done

rm -f *.dat

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
