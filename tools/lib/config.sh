#!/bin/echo This is a library file and cannot be executed directly

CONFIG_SH=1

TMPDIR=$(dirname $0)/tmp

WGET="wget -N"
MAVEN=mvn
LOG="$(basename $0 .sh).log"
