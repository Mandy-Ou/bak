@echo off
color 0A
rem 修改IP地址、子网掩码、网关 
netsh interface ip set address name="本地连接" source=static addr=192.168.1.8 mask=255.255.255.0 gateway=192.168.1.1 gwmetric=1 
