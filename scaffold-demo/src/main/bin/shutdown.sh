#!/bin/sh
echo 'Stoping the I/O Service...'
pid=`cat "service.pid"`
kill $pid
rm -rf service.pid
echo 'Stoped.'

