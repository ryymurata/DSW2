#!/bin/bash

cp -ru LataVelhaInc/src/main/resources/static/images "`ls -dtr1 /tmp/tomcat-docbase* | tail -1`"