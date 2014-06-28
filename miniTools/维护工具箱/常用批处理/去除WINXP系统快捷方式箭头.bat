@color d0
@title 去除系统快捷方式箭头
@echo 下面将要为你去除系统快捷方式的小箭头
@echo 5秒钟后将关闭显示桌面进程,请不必惊慌,稍后会重新开启
@echo 菜鸟朋友们 不懂的朋友 千万不要关闭此程序
@echo Windows Registry Editor Version 5.00>>1.reg
@echo [HKEY_CLASSES_ROOT\lnkfile]>>1.reg
@echo "IsShortcut"=->>1.reg
@echo [HKEY_CLASSES_ROOT\piffile]>>1.reg
@echo "IsShortcut"=->>1.reg
@echo [HKEY_CLASSES_ROOT\InternetShortcut]>>1.reg
@echo "IsShortcut"=->>1.reg
@echo [HKEY_LOCAL_MACHINE\SOFTWARE\Classes\InternetShortcut]>>1.reg
@echo "IsShortcut"=->>1.reg
regedit/s 1.reg
del 1.reg
taskkill /f /im Explorer.exe
@echo 正在开启显示桌面,系统快捷方式箭头已清除
start "explorer.exe" "%windir%\explorer.exe"
