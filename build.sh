#!/bin/bash

BUILD_PROD_NAME=${1}
BUILD_PROD_ALL=("can" "collector" "host" "mon")

ACTIVE_PROFILE=${2}

OS=${3}

BASE_DIR=`pwd`
TIME_STAMP=`date +%Y-%m-%d_%H:%M`
DOWNLOAD_PATH=/home/autocrypt/downloads/vsoc/${TIME_STAMP}


function build_frontend ()
{
  cd ${BASE_DIR}/frontend
  npm install
  npm run build
  cd ${BASE_DIR}
}


function make_jar ()
{
  local jar_name=${1}

  cd ${BASE_DIR}/vsoc_${jar_name}
  ./gradlew clean bootjar
}

function make_docker_image ()
{
  local name=${1}
  local version=${2}

  cd ${BASE_DIR}/vsoc_${name}

  echo "remove docker vsoc_${name} image"
  docker rmi vsoc_${name}:${version}

  echo "make vsoc_${name} bootjar"
  make_jar ${name}

  echo "make docker image(vsoc_${name})"
  docker build -t vsoc_${name}:${version} .
}

function push_private_repo ()
{
  local name=${1}
  local tag=${2}

  local build_service_ip="vsocdev.autocrypt.com"
  echo "push the docker image in private repo.." 

  docker rmi ${build_service_ip}:5000/vsoc_${name}:${tag}
  docker tag vsoc_${name}:${version} ${build_service_ip}:5000/vsoc_${name}:${tag}
  docker push ${build_service_ip}:5000/vsoc_${name}:${tag}
}

function save_docker_image ()
{
  local docker_name=${1}
  local docker_version=${2}

  cd ${BASE_DIR}/vsoc_${docker_name}

  echo "save docker image(vsoc_${docker_name})"
  docker save -o vsoc_${docker_name}.tar vsoc_${docker_name}:${docker_version}
}	

function copy_package_file ()
{
  local name=${1}
  local version=${2}

  mkdir -p ${DOWNLOAD_PATH}/${name}/jar
  mkdir -p ${DOWNLOAD_PATH}/${name}/docker_image

  cp ${BASE_DIR}/vsoc_${name}/build/libs/vsoc_${name}-${version}.jar ${DOWNLOAD_PATH}/${name}/jar
  cp ${BASE_DIR}/vsoc_${name}/vsoc_${name}.tar ${DOWNLOAD_PATH}/${name}/docker_image
}

function package_product ()
{
  cd ${BASE_DIR}/package
  mkdir vsoc

  for name in ${BUILD_PROD_ALL[@]}
  do
    mv ${BASE_DIR}/vsoc_${name}/vsoc_${name}.tar vsoc
  done

  cp -r vsoc_${OS}_template/* vsoc
  cp  script/install_${OS}.sh vsoc/install.sh

  tar -zcvf vsoc.tar.gz vsoc

  mkdir -p ${DOWNLOAD_PATH}/package
  mv vsoc.tar.gz ${DOWNLOAD_PATH}/package/

  rm -rf vsoc
}

function prod_package () 
{
    local prod_name=${1}

    cd ${BASE_DIR}/vsoc_${name}
    local version=`./gradlew -q version`
    cd ${BASE_DIR}

    local service_info_path="${BASE_DIR}/package/vsoc_${OS}_template/opt/autocrypt/vsoc/bin/scripts"
    sed -i "s/vsoc_${prod_name}_version/${version}/g" ${service_info_path}/vsoc-package-info.sh

    make_docker_image ${prod_name} ${version}
    save_docker_image ${prod_name} ${version}
    copy_package_file ${prod_name} ${version}

    if [[ "${ACTIVE_PROFILE}" == "prod" ]]; then
      
      echo "push the docker image into the private repo..."
      push_private_repo ${prod_name} ${version}	
    
    fi 
}


function change_acitve_profile ()
{

  local service_sh_path="${BASE_DIR}/package/vsoc_${OS}_template/opt/autocrypt/vsoc/bin/scripts/service"
  
  for name in ${BUILD_PROD_ALL[@]}
  do
    sed -i "s/ACTIVE_PROFILES/${ACTIVE_PROFILE}/g" ${service_sh_path}/start-vsoc-${name}-service.sh
  done
}


echo "change active profile => ${ACTIVE_PROFILE}"
change_acitve_profile

if [[ "${BUILD_PROD_NAME}" == "All" ]]; then
  
  build_frontend

  for name in ${BUILD_PROD_ALL[@]}
  do
    prod_package ${name}
  done

  echo "packaging vsoc product..."
  package_product
  

else
  echo "vsoc_${BUILD_PROD_NAME} build..."
   prod_package ${BUILD_PROD_NAME}
fi


