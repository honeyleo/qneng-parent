#!/bin/sh

CUR=`pwd`
APP_DIR=/caragent/car-server
PID=`cat $APP_DIR/service.pid`

# step into the app dir.
cd $APP_DIR

case "$1" in
    start)
        case "$2" in
            --force)
                sh shutdown.sh
                sleep 1
                sh startup.sh
            ;;
            *)
                if [ "$PID" ]; then
                    echo "PID: $PID was found! Is the service still running? Startup  
abort now."
                    exit 1
                else
                    sh startup.sh
                fi
            ;;
        esac
    ;;  
    stop)
        if [ "$PID" ]; then
            sh shutdown.sh
        else
            echo "Couldn't find the running pid of the service. Shutdown abort now."
            exit 1
        fi  
    ;;  
    *)  
        echo "Usage: ccagent.sh (start|stop)"
    ;;  
esac
exit 0

