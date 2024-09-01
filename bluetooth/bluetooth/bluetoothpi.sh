#!/bin/bash
echo "==============================================================================="
echo "使用华中科技大学的源"
sudo cp -f /home/pi/bluetooth/sources.list /etc/apt/sources.list 

echo "bluetooth config!"
echo "install apt"

sudo apt-get update -y # 更新源列表
sudo apt-get upgrade -y # 升级系统软件
echo "=============================install vim========================================" 
sudo apt-get install -y vim 
sudo cp -f /home/pi/bluetooth/.vimrc /root/.vimrc
sudo apt-get install -y bluetooth bluez pulseaudio-module-bluetooth python-gobject python-gobject-2 bluez-tools
echo "配置安装的软件"
echo "============================配置分组规则======================================="
sudo usermod -a -G lp pi
sudo usermod -a -G pulse-access pi
sudo usermod -a -G pulse-access root

echo "=========================蓝牙启用A2DP功能======================================"
sudo cp -f /home/pi/bluetooth/audio.conf /etc/bluetooth/audio.conf
sudo cp -f /home/pi/bluetooth/daemon.conf /etc/pulse/daemon.conf
echo "============================修改名字==========================================="
sudo cp -f /home/pi/bluetooth/main.conf /etc/bluetooth/main.conf
echo "修改设备里面的名字的方法还有确定这里预留待"
bluetoothMac=$(ls /var/lib/bluetooth/)
sudo cp -f /home/pi/bluetooth/config /var/lib/bluetooth/*/config

echo "=============================配置蓝牙接入======================================"
sudo cp -f /home/pi/bluetooth/99-input.rules /etc/udev/rules.d/99-input.rules
sudo mkdir /usr/lib/udev
sudo cp -f /home/pi/bluetooth/bluetooth /usr/lib/udev/bluetooth
sudo chmod 774 /usr/lib/udev/bluetooth

echo "===========================开启蓝牙自动发现功能================================"
sudo cp -f /home/pi/bluetooth/bluetooth-agent /etc/init.d/bluetooth-agent
sudo chmod 755 /etc/init.d/bluetooth-agent
sudo insserv -d bluetooth-agent
echo "===========================命令行以pi来登陆===================================="
sudo cp -f /home/pi/bluetooth/inittab /etc/inittab
sudo cp -f /home/pi/bluetooth/pulseaudio /etc/default/pulseaudio 

echo "===========================开启蓝牙自发现======================================"
sudo hciconfig hci0 piscan
sudo start-stop-daemon -S -x /usr/bin/bluetooth-agent -c pi -b -- 0000


