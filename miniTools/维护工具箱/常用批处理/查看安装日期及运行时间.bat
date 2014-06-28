@echo off
color 70
@ ECHO.
@ ECHO.
%SystemDrive%\windows\system32\systeminfo.exe|findstr /i "初始安装日期 系统启动时间"
@ ECHO.
@ ECHO.
echo 按任意键退出...
pause>nul