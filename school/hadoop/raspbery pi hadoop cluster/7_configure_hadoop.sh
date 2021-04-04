# 7_configure_hadoop.sh
# 실행한 노드를 네임노드로 만들고 ubuntu4를 세컨더리 네임노드로 만듧니다 
# ubuntu4를 검색, 수정하여 세컨더리 네임노드를 바꿀 수 있습니다
source /etc/environment
source /etc/profile
cd "$HADOOP_HOME"/etc/hadoop

echo \>\>\>\> EDITING core-site.xml
if [ ! -f core-site.xml.original ]; then
        cp core-site.xml core-site.xml.original
else
        cp core-site.xml.original core-site.xml
fi
sed -i '19a\
<property>\
    <name>fs.defaultFS</name>\
    <value>hdfs://'"$HOSTNAME"':9000</value>\
</property>\
<property>\
    <name>hadoop.tml.dir</name>\
    <value>/home/ubuntu/hadoop/tmp</value>\
</property>' core-site.xml
echo \>\>\>\> DONE !

echo \>\>\>\> EDITING hdfs-site.xml
if [ ! -f hdfs-site.xml.original ]; then
        cp hdfs-site.xml hdfs-site.xml.original
else
        cp hdfs-site.xml.original hdfs-site.xml
fi
sed -i '19a\
<property>\
    <name>dfs.namenode.name.dir</name>\
    <value>'"$HADOOP_HOME"'/data/dfs/nameNode</value>\
</property>\
<property>\
        <name>dfs.namenode.checkpoint.dir</name>\
        <value>'"$HADOOP_HOME"'/data/dfs/namesecondary</value>\
    </property>\
<property>\
    <name>dfs.datanode.data.dir</name>\
    <value>'"$HADOOP_HOME"'/data/dfs/dataNode</value>\
</property>\
<property>\
    <name>dfs.replication</name>\
    <value>2</value>\
</property>\
<property>\
    <name>dfs.http.address</name>\
    <value>'"$HOSTNAME"':50070</value>\
</property>\
<property>\
    <name>dfs.secondary.http.address</name>\
    <value>ubuntu4:50090</value>\
</property>\
<property>\
  <name>dfs.webhdfs.enabled</name>\
  <value>true</value>\
</property>\
' hdfs-site.xml
echo \>\>\>\> DONE !

echo \>\>\>\> EDITING mapred-site.xml
if [ ! -f mapred-site.xml.original ]; then
	cp mapred-site.xml.template mapred-site.xml
        cp mapred-site.xml mapred-site.xml.original
else
        cp mapred-site.xml.original mapred-site.xml
fi
sed -i '19a\
<property>\
   <name>mapreduce.framework.name</name>\
   <value>yarn</value>\
</property>\
<property>\
   <name>mapreduce.map.memory.mb</name>\
   <value>1024</value>\
</property>\
<property>\
   <name>mapreduce.reduce.memory.mb</name>\
   <value>1024</value>\
</property>\
<property>\
   <name>mapreduce.map.java.opts.max.heap</name>\
   <value>819</value>\
</property>\
<property>\
   <name>mapreduce.reduce.java.opts.max.heap</name>\
   <value>819</value>\
</property>\
<property>\
   <name>mapreduce.map.java.opts</name>\
   <value>-Xmx819m</value>\
</property>\
<property>\
   <name>mapreduce.reduce.java.opts</name>\
   <value>-Xmx819m</value>\
</property>\
' mapred-site.xml
echo \>\>\>\> DONE !

echo \>\>\>\> EDITING yarn-site.xml
if [ ! -f yarn-site.xml.original ]; then
        cp yarn-site.xml yarn-site.xml.original
else
        cp yarn-site.xml.original yarn-site.xml
fi
sed -i '17a\
 <property>\
    <name>yarn.nodemanager.aux-services</name>\
    <value>mapreduce_shuffle</value>\
 </property>\
 <property>\
    <name>yarn.nodemanager.aux-services.mapreduce_shuffle.class</name>\
    <value>org.apache.hadoop.mapred.ShuffleHandler</value>\
 </property>\
<property>\
    <name>yarn.nodemanager.local-dirs</name>\
    <value>'"$HADOOP_HOME"'/data/yarn/nm-local-dir</value>\
 </property>\
<property>\
    <name>yarn.resourcemanager.fs.state-store.uri</name>\
    <value>'"$HADOOP_HOME"'/data/yarn/system/rmstore</value>\
 </property>\
<property>\
    <name>yarn.resourcemanager.hostname</name>\
    <value>'"$HOSTNAME"'</value>\
 </property>\
<property>\
   <name>yarn.nodemanager.vmem-check-enabled</name>\
   <value>false</value>\
</property>\
<property>\
   <name>yarn.nodemanager.pmem-check-enabled</name>\
   <value>false</value>\
</property>\
<property>\
   <name>yarn.nodemanager.vmem-pmem-ratio</name>\
   <value>2.1</value>\
</property>\
<property>\
   <name>yarn.nodemanager.resource.memory-mb</name>\
   <value>2048</value>\
</property>\
<property>\
   <name>yarn.scheduler.minimum-allocation-mb</name>\
   <value>1024</value>\
</property>\
<property>\
   <name>yarn.scheduler.maximum-allocation-mb</name>\
   <value>2048</value>\
</property>\
' yarn-site.xml
echo \>\>\>\> DONE !

echo \>\>\>\> EDITING slaves
echo "\
ubuntu3
ubuntu4
" > slaves
echo \>\>\>\> DONE !
