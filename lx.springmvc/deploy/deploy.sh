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

name=${name}
base="/opt/cmt/demo"

data_home="/opt/data"
log_home="/opt/logs/resin"
bin_home="/opt/bin/resin"
conf_home="/opt/conf/resin"

servers=(${resin.server})

#
serverCount=${#servers[@]}
serial_no=`date +%s`
i=0
while [ $i -lt $serverCount ]
do
    server=${servers[$i]}
    echo "deploy $name on $server"
    host=`echo $server | cut -d: -f1`
    port=`echo $server | cut -d: -f2`

    log="${log_home}/$name-$port"
    conf="${conf_home}/$name-$port.xml"
    deploy="$base/$name/$port"
    webapp="$deploy/webapp"
    shell="${bin_home}/$name-%port.sh";

    #create deploy dir
    ssh root@$host "mkdir -p ${data_home}/$name/$port"
    ssh root@$host "chown -R resin ${data_home}/$name/$port"
    ssh root@$host "mkdir -p $log"
    ssh root@$host "chown -R resin $log"
    
    #stop server
    ssh root@$host "$shell stop" || echo "$shell is not running"
    while [ `ssh resin@$host "ps -ef | grep $conf | grep -v grep | wc -l"` -gt 0 ]
    do
        echo "waiting for $shell resin stop"
        sleep 1
    done
    
    #process shell script
    ssh root@$host "mkdir -p ${bin_home}"
    scp start.sh root@$host:${bin_home}
    ssh root@$host "chmod +x ${bin_home}/$name/start.sh"
    ssh root@$host "mv -f ${bin_home}/start.sh $shell"
    ssh root@$host "sed -i 's/##conf##/${conf//\//\\/}/' $shell"
    ssh root@$host "chown resin $shell"

    #process config file, maybe cause resin restart
    ssh root@$host "mkdir -p ${conf_home}"
    ssh root@$host "chown -R resin ${conf_home}"
    scp *.xml resin@$host:${conf_home}
    ssh resin@$host "mv -f ${conf_home}/resin4.xml $conf"
    ssh resin@$host "sed -i 's/##port##/$port/' $conf"

    #unpackage war
    ssh root@$host "mkdir -p $webapp"
    ssh root@$host "chown -R resin $deploy"
    
    scp $name.war resin@$host:$deploy
    ssh resin@$host "cd $webapp && rm -rf * && ${java_home}/bin/jar xf $deploy/$name.war"
    ssh root@$host "cp $deploy/$name.war $deploy/$name-${serial_no}.war"
    
    #create soft link for data resources
    ssh resin@$host "rm -f ${data_home}/$name/js && ln -s $webapp/js ${data_home}/$name/js"
    ssh resin@$host "rm -f ${data_home}/$name/css && ln -s $webapp/css ${data_home}/$name/css"
    ssh resin@$host "rm -f ${data_home}/$name/img && ln -s $webapp/img ${data_home}/$name/img"
    ssh resin@$host "rm -f ${data_home}/$name/sample && ln -s $webapp/sample ${data_home}/$name/sample"

    ssh resin@$host "$shell start"
    
    i=$((i+1))
    if [ $i -ne ${serverCount} ]; then
        sleep 5
    fi

done
echo "all done."