# 8_broadcast_conf.sh
# 네임노드가 5번 일 경우 다음의 스크립트를 써서 모든 노드에 변경사항을 전파합니다
source /etc/environment
source /etc/profile
cd $HADOOP_HOME/etc/hadoop

scp * ubuntu1:$HADOOP_HOME/etc/hadoop
scp * ubuntu2:$HADOOP_HOME/etc/hadoop
scp * ubuntu3:$HADOOP_HOME/etc/hadoop
scp * ubuntu4:$HADOOP_HOME/etc/hadoop
