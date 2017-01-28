<div id="table-of-contents">
<h2>Table of Contents</h2>
<div id="text-table-of-contents">
<ul>
<li><a href="#org59047bd">1. Introduction</a></li>
<li><a href="#org223a2d0">2. Structure</a></li>
<li><a href="#org1c55844">3. How to build the project</a></li>
<li><a href="#org664a7fa">4. How to run the examples</a>
<ul>
<li><a href="#orgdeaf868">4.1. How to run SparkCore example</a></li>
<li><a href="#org0c06f92">4.2. How to run SparkSQL example in cluster mode</a></li>
</ul>
</li>
</ul>
</div>
</div>
Sparkexample


<a id="org59047bd"></a>

# Introduction

This repository contains a project used as an example during a presentation
about Spark that I made for the 74th jugpadova meeting
(<http://www.jugpadova.it/articles/2017/01/18/jug-meeting-74-spark-xtend-iot>).

In addiction to this there are the slides and a docker file which creates an
image that contains a Spark 2.1.0 binary ready to use. 


<a id="org223a2d0"></a>

# Structure

-   presentation: contains the slides
-   docker: contains the dockerfile and an init script for the containers
    created from the dockerfile image
-   src: java source files


<a id="org1c55844"></a>

# How to build the project

You need to install:

-   java 8 (see <http://openjdk.java.net/install/>)
-   maven (see <http://maven.apache.org/install.html>)

Then:

    cd /tmp
    git clone https://github.com/gtrev/sparkexample.git
    cd sparkexample
    mvn package


<a id="org664a7fa"></a>

# How to run the examples

You need to install Spark. First ownload it from
<http://spark.apache.org/downloads.html>, you can choose the predefined version
(at the time of writing the predefined version is 2.1.0).

Then you need to extract the files. In the rest of this tutorial we assume
that $SPARKHOME points to the directory where you extracted the binary file.


<a id="orgdeaf868"></a>

## How to run SparkCore example

To run in standalone mode use this command:

    cd sparkexample
    mvn clean package
    $SPARKHOME/bin/spark-submit --class it.gtrev.jugpadova.sparkexample.SparkCore ./target/sparkexample-1.0-SNAPSHOT.jar

In order to see what happened you need to enable logging and start the
history server. Make sure that directory **/tmp/spark-events** exists,
otherwise the history server will not start:

    mkdir /tmp/spark-events
    cd $SPARKHOME/sbin
    ./start-history-server.sh

then use this command:

    cd sparkexample
    $SPARKHOME/bin/spark-submit --class it.gtrev.jugpadova.sparkexample.SparkCore --conf spark.eventLog.enabled=true --class it.gtrev.jugpadova.sparkexample.SparkCore ./target/sparkexample-1.0-SNAPSHOT.jar

The history server is listening at: <http://localhost:18080>


<a id="org0c06f92"></a>

## How to run SparkSQL example in cluster mode

You need a working installation of docker (see
<https://docs.docker.com/engine/installation/>)

Download the lastfm files from
<http://www.dtic.upf.edu/~ocelma/MusicRecommendationDataset/lastfm-360K.html>,
then extract the archive in *var/tmp* directory

Use the docker file that is in **docker** directory to build a docker image:

    cd sparkexample/docker
    docker build -t java-8-spark .

Start two or more (depending on your computer characterstics) docker containers: 

    docker run -it --name worker1 java-8-spark /bin/bash
    docker run -it --name worker2 java-8-spark /bin/bash

Copy lastfm files in each container using the init-container.sh script

    cd sparkexample/docker
    bash init-container.sh

Start the master node:

    cd $SPARKHOME/sbin
    ./start-master.sh

Then start the worker for each docker container:

    /spark/sbin/start-slave.sh -c 2 -m 2G spark://172.17.0.1:7077

The last part of the command is the url of the master node. The IP value may
be different depending on which subnet is created by docker.

Finally submit the application using spark-submit:

    $SPARKHOME/bin/spark-submit --master spark://172.17.0.1:7077 --conf spark.eventLog.enabled=true --class it.gtrev.jugpadova.sparkexample.SparkSQL ./target/sparkexample-1.0-SNAPSHOT.jar

