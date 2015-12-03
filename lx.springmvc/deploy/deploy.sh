#!/bin/sh
resin="/opt/apps/resin"
if [ ! -d "$resin" ]; then
echo "please install resin4 first. thanks"
exit 0
fi

if type -p java; then
    java_home=java
elif [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]];  then
    java_home="$JAVA_HOME/bin/java"
else
    echo "please install jdk first. thanks"
    exit 0
fi

version=$("$java_home" -version 2>&1 | awk -F '"' '/version/ {print $2}')
echo jdk version "$version"

servers=(${resin.server})
serverCount=${#servers[@]}
serial_no=`date +%s`
i=0
while [ $i -lt $serverCount ]
do
    server=${servers[$i]}
    echo "deploy ${name} on $server"
    host=`echo $server | cut -d: -f1`
    port=`echo $server | cut -d: -f2`

    log="${log.path}/${name}-$port"
    conf="${conf.path}/${name}-$port.xml"
    data="${data.path}/$name/$port"
    deploy="${deploy.path}/${name}/$port"
    webapp="$deploy/webapp"
    shell="${bin.path}/${name}-$port.sh";

    ssh root@$host "mkdir -p ${bin.path}"
    ssh root@$host "mkdir -p ${conf.path}"
    ssh root@$host "mkdir -p $data"
    ssh root@$host "mkdir -p $log"
    ssh root@$host "mkdir -p $webapp"

    #create deploy dir
    ssh root@$host "chown -R resin $data"
    ssh root@$host "chown -R resin $log"
    
    #stop server
    while [ `ssh resin@$host "ps -ef | grep $conf | grep -v grep | wc -l"` -gt 0 ]
    do
        ssh root@$host "$shell stop" || echo "$shell is not running"
        echo "waiting for $shell resin stop"
        sleep 1
    done
    
    #process shell script
    scp ${name}/deploy/start.sh root@$host:${bin.path}
    ssh root@$host "chmod +x ${bin.path}/start.sh"
    ssh root@$host "mv -f ${bin.path}/start.sh $shell"
    ssh root@$host "sed -i 's/##conf##/${conf//\//\\/}/' $shell"
    ssh root@$host "chown resin $shell"

    #process config file, maybe cause resin restart
    ssh root@$host "chown -R resin ${conf.path}"
    scp ${name}/deploy/resin4.xml resin@$host:${conf.path}
    ssh resin@$host "mv -f ${conf.path}/resin4.xml $conf"
    ssh resin@$host "sed -i 's/##port##/$port/' $conf"

    #unpackage war
    ssh root@$host "chown -R resin $deploy"    
    scp ${name}.war resin@$host:$deploy
    ssh resin@$host "cd $webapp && rm -rf * && /opt/apps/jdk/bin/jar xf $deploy/${name}.war"
    ssh root@$host "cp $deploy/${name}.war $deploy/${name}-${serial_no}.war"
    
    #create soft link for data resources
    #ssh resin@$host "rm -f ${data.path}/${name}/js && ln -s $webapp/js ${data.path}/${name}/js"
    #ssh resin@$host "rm -f ${data.path}/${name}/css && ln -s $webapp/css ${data.path}/${name}/css"
    #ssh resin@$host "rm -f ${data.path}/${name}/img && ln -s $webapp/img ${data.path}/${name}/img"
    #ssh resin@$host "rm -f ${data.path}/${name}/sample && ln -s $webapp/sample ${data.path}/${name}/sample"

    ssh resin@$host "$shell start"
    
    i=$((i+1))
    if [ $i -ne ${serverCount} ]; then
        sleep 5
    fi

done
echo "all done."