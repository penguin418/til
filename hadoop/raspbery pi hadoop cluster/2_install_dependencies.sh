# 2_install_dependencies
# java
sudo apt update && sudo apt install -y openjdk-8-jdk
# 하둡을 위한 dependencies
sudo apt install -y gfortran libreadline6-dev libx11-dev libxt-dev libpng-dev libjpeg-dev libcairo2-dev xvfb libbz2-dev libzstd-dev liblzma-dev libcurl4-openssl-dev texinfo texlive texlive-fonts-extra screen wget libpcre2-dev g++ make 
# R을 위한 dependencies
sudo apt-get install -y ant apparmor-utils build-essential cmake debsigs dpkg-sig expect fakeroot git-core gnupg libapparmor1 libbz2-dev libgl1-mesa-dev libgstreamer-plugins-base1.0-0 libgstreamer1.0-0 libjpeg62 libpam-dev libpango1.0-dev libssl-dev libxslt1-dev pkg-config unzip uuid-dev zlib1g-dev libblas-dev liblapack-dev libjpeg-dev liblzma-dev r-cran-rjava apparmor-utils build-essential cmake debsigs dpkg-sig expect fakeroot git-core gnupg libapparmor1 libbz2-dev libgl1-mesa-dev libgstreamer-plugins-base1.0-0 libgstreamer1.0-0 libjpeg62 libpam-dev libpango1.0-dev libssl-dev libxslt1-dev pkg-config unzip uuid-dev zlib1g-dev libgit2-dev
sudo sed -i '$a JAVA_HOME="\/usr\/lib\/jvm\/java-8-openjdk-arm64\/"' /etc/environment
echo \>\>\>\>  DONE !
