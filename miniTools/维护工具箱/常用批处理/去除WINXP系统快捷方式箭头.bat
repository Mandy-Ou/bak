@color d0
@title ȥ��ϵͳ��ݷ�ʽ��ͷ
@echo ���潫ҪΪ��ȥ��ϵͳ��ݷ�ʽ��С��ͷ
@echo 5���Ӻ󽫹ر���ʾ�������,�벻�ؾ���,�Ժ�����¿���
@echo ���������� ���������� ǧ��Ҫ�رմ˳���
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
@echo ���ڿ�����ʾ����,ϵͳ��ݷ�ʽ��ͷ�����
start "explorer.exe" "%windir%\explorer.exe"
