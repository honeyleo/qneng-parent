#!/bin/sh
echo 'Stoping the I/O Service...'
pid=`cat "testservice.pid"`
kill $pid
rm -rf testservice.pid
echo 'Stoped.'

