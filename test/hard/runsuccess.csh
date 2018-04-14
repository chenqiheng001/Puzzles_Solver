#!/bin/csh
# usage: runsuccess initfile goalfile, where there is a solution
limit cputime 120
set testdir=~cs61b/labcode/proj3/testing/hard
/bin/rm -f /tmp/out$$
echo $1 " " $2
/usr/sww/opt/jdk-1.6.0_13/bin/java Solver $testdir/$1 $testdir/$2 > /tmp/out$$
if ($status != 0) echo "*** incorrect status"
/usr/sww/opt/jdk-1.6.0_13/bin/java Checker $testdir/$1 $testdir/$2 < /tmp/out$$
if ($status != 0) echo "*** incorrect solver output"
/bin/rm -f /tmp/out$$
echo " "
