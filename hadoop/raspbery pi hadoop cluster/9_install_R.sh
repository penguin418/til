# 9_install_R.sh
# 반드시 실행 전 sudo su를 먼저 해주세요

cd /usr/local/src
sudo wget https://cran.rstudio.com/src/base/R-4/R-4.0.2.tar.gz
tar zxvf R-4.0.2.tar.gz
cd R-4.0.2
./configure --enable-R-shlib
make && make check && make install
cd ..
exit
cd
