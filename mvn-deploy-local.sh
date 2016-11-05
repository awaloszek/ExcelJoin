#!/bin/sh
#
# Add commons cli to project-local repo
#

SRC_PATH=<path-to-commons-cli-download>
PROJECT_PATH=<local-path-to-repo-as-url>

mvn deploy:deploy-file \
    -DartifactId=commons-cli \
    -Dversion=1.3.1 \
    -DgroupId=org.apache.commons \
    -Dfile=$SRC_PATH/commons-cli-1.3.1/commons-cli-1.3.1.jar \
    -DrepositoryId=repo \
    -Durl=file://$PROJECT_PATH/repo/ \
    -DgeneratePom=true \
    -Djavadoc=$SRC_PATH/commons-cli-1.3.1/commons-cli-1.3.1-javadoc.jar \
    -Dpackaging=jar \
    -e
