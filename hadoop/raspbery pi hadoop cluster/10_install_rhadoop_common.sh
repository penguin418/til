# 10_install_rhadoop_common.sh
wget -O rmr2.tar.gz https://github.com/RevolutionAnalytics/rmr2/releases/download/3.3.1/rmr2_3.3.1.tar.gz
sudo -E su - -c "R -e \"
install.packages(c('Rcpp','RJSONIO','digest','functional','reshape2','stringr','plyr','caTools'), repos='http://cran.rstudio.com/')
install.packages('$PWD/rmr2.tar.gz')\""

wget -O plyrmr.tar.gz https://github.com/RevolutionAnalytics/plyrmr/releases/download/0.6.0/plyrmr_0.6.0.tar.gz
sudo -E su - -c "R -e \"
install.packages(c('dplyr', 'R.methodsS3', 'Hmisc', 'lazyeval', 'rjson', 'devtools', 'usethis'), repos='http://cran.rstudio.com/')
library('devtools')\
install_github('RevolutionAnalytics/memoise')\
install.packages('$PWD/plyrmr.tar.gz')\""

sudo -E su - -c "R -e \"
install.packages('rJava', repos='http://cran.rstudio.com/')\""
