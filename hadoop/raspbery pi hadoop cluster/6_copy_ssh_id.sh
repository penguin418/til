# 6_copy_ssh_id
# 5개가 모두 연결되어야 합니다
# 네임노드에서만 실행해도 됩니다

sudo apt install sshpass

echo \>\>\>\> CREATING SSH KEY ...
sudo rm -rf .ssh
cat /dev/zero | ssh-keygen -N ''
wait $!

ssh-keyscan -H ubuntu1 >> .ssh/known_hosts
sshpass -p "R2020hjp" ssh ubuntu1 -y 'exit'
sshpass -p "R2020hjp" ssh-copy-id ubuntu1
wait $!

ssh-keyscan -H ubuntu2 >> .ssh/known_hosts
sshpass -p "R2020hjp" ssh ubuntu2 -y 'exit'
sshpass -p "R2020hjp" ssh-copy-id ubuntu2
#wait $!

ssh-keyscan -H ubuntu3 >> .ssh/known_hosts
sshpass -p "R2020hjp" ssh ubuntu3 -y 'exit'
sshpass -p "R2020hjp" ssh-copy-id ubuntu3
#wait $!

ssh-keyscan -H ubuntu4 >> .ssh/known_hosts
sshpass -p "R2020hjp" ssh ubuntu4 -y 'exit'
sshpass -p "R2020hjp" ssh-copy-id ubuntu4
wait $!

ssh-keyscan -H ubuntu5 >> .ssh/known_hosts
sshpass -p "R2020hjp" ssh ubuntu5 -y 'exit'
sshpass -p "R2020hjp" ssh-copy-id ubuntu5
wait $!
echo \>\>\>\> DONE !
