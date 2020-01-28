#!/bin/bash

USER=$1
APP_NAME=$2
CI_COMMIT_SHORT_SHA=$3

if [ -z "$USER" ] || [ -z "$APP_NAME" ] || [ -z "$CI_COMMIT_SHORT_SHA" ]; then
 echo "Alguna de las variables no existe"
 exit 1
fi

echo $USER
echo $APP_NAME
echo $CI_COMMIT_SHORT_SHA

docker stop $(docker ps | grep -i jar | cut -d " " -f 1)

if [ "$?" -eq 0 ]; then
  echo "Eliminado con exito"
else 
  echo "Existe algún error, probablemente la imagen no exista" 
  exit 2
fi    

docker run --rm -d -p 8080:8080 $USER/$APP_NAME:$CI_COMMIT_SHORT_SHA