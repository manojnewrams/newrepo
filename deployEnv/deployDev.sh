#!/bin/bash

USER=$1
APP_NAME=$2
TAG_FOR_IMAGES=$3

if [ -z "$USER" ] || [ -z "$APP_NAME" ] || [ -z "$TAG_FOR_IMAGES" ]; then
 echo "Alguna de las variables no existe"
 exit 1
fi

echo $USER
echo $APP_NAME
echo $TAG_FOR_IMAGES

output=$(docker ps | grep -i develop | cut -d " " -f 1)

if [ ! -z "$output" ]; then

  docker stop $(docker ps | grep -i develop | cut -d " " -f 1)

    if [ "$?" -eq 0 ]; then
      echo "Eliminado con exito"
    else 
      echo "Existe algún error" 
      exit 2
    fi    
else
 echo "La imagen no existe"
fi

docker run --rm -d -p 8081:8081 $USER/$APP_NAME:$TAG_FOR_IMAGES