@echo off
color 0A
rem �޸�IP��ַ���������롢���� 
netsh interface ip set address name="��������" source=static addr=192.168.1.8 mask=255.255.255.0 gateway=192.168.1.1 gwmetric=1 
