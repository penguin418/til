# 4_install_hadoop
# 여러 번 실행하면 etc/environment혹은 /etc/profile의 내용이 매우 길어질 수 있습니다
cd ~
wget "https://archive.apache.org/dist/hadoop/common/hadoop-2.8.5/hadoop-2.8.5.tar.gz"
tar -zxvf hadoop-2.8.5.tar.gz
mv hadoop-2.8.5 hadoop
sudo sed -i '/^PATH=/ s/"$/\:\/home\/ubuntu\/hadoop\/bin\:\/home\/ubuntu\/hadoop\/sbin"/' /etc/environment
sudo sed '$a\
HADOOP_CMD="/home/ubuntu/hadoop/bin/hadoop"\
HADOOP_STREAMING="/home/ubuntu/hadoop/share/hadoop/tools/lib/hadoop-streaming-2.8.5.jar"\
HADOOP_HOME="/home/ubuntu/hadoop"\
HADOOP_OPTS="-Djava.library.path=/usr/local/hadoop/lib/native"\
' /etc/environment
sudo sed '$a\
export PATH=$PATH:$JAVA_HOME/bin\
export HADOOP_PREFIX=/home/ubuntu/hadoop\
export CLASS_PATH=.\
export LD_LIBRARY_PATH=/usr/local/lib:/usr/lib/jvm/java-8-openjdk-arm64/jre/lib/aarch64/server\
' /etc/profile

# 이후 hadoop/tmp폴더를 사용해서 해당 폴더를 생성해 두어야 합니다
mkdir -p hadoop/tmp
