@echo off
PUSHD %~dp0
SET CurDir=%CD%
PATH "%WINDIR%\SYSTEM32"
REG ADD "HKCU\SOFTWARE\EasyBoot Systems\UltraISO\5.0" /f /ve /t REG_SZ /d "%CurDir%"
REG ADD HKCR\UltraISO\DefaultIcon /ve /t REG_SZ /d "%CurDir%\UltraISO.EXE,0" /f>NUL
REG ADD HKCR\UltraISO\shell\open\command /ve /t REG_SZ /d "\"%CurDir%\UltraISO.EXE\" "\"%%1\" /f>NUL
REG ADD "HKCU\SOFTWARE\EasyBoot Systems\UltraISO\5.0" /v DefViewer /t REG_SZ /d "%SystemRoot%\system32\NOTEPAD.EXE" /f>NUL
ftype UltraISO="%CurDir%\ultraiso.exe" "%%1"
assoc .iso=UltraISO
assoc .bin=UltraISO
assoc .isz=UltraISO
assoc .cue=UltraISO
assoc .mds=UltraISO
assoc .mdf=UltraISO
assoc .nrg=UltraISO
assoc .img=UltraISO
assoc .ima=UltraISO
regsvr32 /s "%CurDir%\isoshell.dll"
cd drivers
isocmd.exe -number 1
isocmd.exe -i


