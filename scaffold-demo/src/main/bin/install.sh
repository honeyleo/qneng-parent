#!/bin/sh
echo " -- Install service..."
SERVICE_FILE=/etc/init.d/caragent
if [ -f "$SERVICE_FILE" ]; then
    echo "clean the service file ..."
    rm -rf $SERVICE_FILE
fi
cp ccagent.sh $SERVICE_FILE
chmod u+x $SERVICE_FILE
echo " -- Install service successfully..."
sed -i '/\/etc\/init.d\/caragent/d' /etc/rc.local
echo "/etc/init.d/caragent start --force" >> /etc/rc.local
/etc/init.d/caragent start --force
exit 0
