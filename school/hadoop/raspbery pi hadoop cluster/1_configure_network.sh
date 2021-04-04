# 1_configure_network.sh
# 로컬호스트는 주석처리하고, 정적 ip를 추가합니다
echo \>\>\>\> UPDATE NETWORK CONFIGURATION
sudo sed -i -e 's/^\#127.0.0.1*/# 127.0.0.1/' /etc/hosts
sudo sed -i '3a\
192.168.7.10   ubuntu1\
192.168.7.20   ubuntu2\
192.168.7.30   ubuntu3\
192.168.7.40   ubuntu4\
192.168.7.50   ubuntu5' /etc/hosts
echo \>\>\>\> DONE !

sudo service ufw stop
