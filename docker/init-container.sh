#!/bin/bash

USER=`whoami`
DIR=/var/tmp/lastfm-dataset-360K/
file1=usersha1-artmbid-artname-plays.tsv
file2=usersha1-profile.tsv
CMD="/spark/sbin/start-slave.sh -c 2 -m 2G spark://172.17.0.1:7077"

if [ $USER != "root" ] ; then
    echo -e "eseguire lo script come utente root"
    exit 1
fi

for i in `docker ps -q` ; do
    docker cp "${DIR}"/"${file1}" "${i}":"${DIR}"
    docker cp "${DIR}"/"${file2}" "${i}":"${DIR}"
done

#for i in `docker ps -q` ; do
#    docker exec -d "${i}" ${CMD}
#done



