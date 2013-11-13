#!/bin/sh
#
# build-js.sh
#

# js build

UGLY="uglifyjs -nc -v"
JS_APP="angular.min.js angular-route.min.js angular-resource.min.js app.js"
JS_OUT_APP=app.min.js
JS_DIR=~/space/trekk/src/main/webapp/WEB-INF/resources/js

cd $JS_DIR

cat $JS_APP | $UGLY -o "$JS_OUT_APP"

