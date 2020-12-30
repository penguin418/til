# 11_install_rhadoop_client
wget -O rhdfs.tar.gz https://github.com/RevolutionAnalytics/rhdfs/blob/master/build/rhdfs_1.0.8.tar.gz?raw=true
sudo -E su - -c "R -e \"
library('methods')
library('rJava')
install.packages('$PWD/rhdfs.tar.gz')\""
