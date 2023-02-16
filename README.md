# vsoc-front

## 이미지 업로드

./build.sh

## 이미지 추출

./export-image.sh

production/image.tar에 저장됩니다.


vSoC 개발 정보

Repo : https://seongjun-autocrypt@bitbucket.org/autocrypt/vsoc.git

개발서버 : 20.194.61.132, autocrypt/Autocrypt123!

Jenkins : http://20.194.61.132:8080/

Domain : http://testvsoc.autocrypt.io/


docker network를 생성 후 방화벽에서 docker network를 trusted존에 추가해야 함
- firewall-cmd --permanent --zone=trusted --add-interface=docker1
- firewall-cmd --reload
