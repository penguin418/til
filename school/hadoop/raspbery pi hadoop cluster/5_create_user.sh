5_create_user
# ubuntu server 설치 기준, 기본 id는 모두 ubuntu로 동일한 상황을 가정했으나
# 만약 동일 아이디를 생성해야 하는 경우 아래 스크립트를 사용할 수 있습니다

adduser --disabled-login --gecos "" hadoop_user 
echo hadoop_user:R2020hjp | chpasswd
sudo usermod -aG hadoop_user hadoop_user
sudo adduser hadoop_user sudo

# 만약 하둡을 /usr/local/hadoop에 설치한 경우 해당 위치에 권한이 필요합니다
if [[ -d /usr/local/hadoop/ ]]; then
	sudo chown hadoop_user:root -R /usr/local/hadoop
	sudo chmod g+rwx -R /usr/local/hadoop
fi
