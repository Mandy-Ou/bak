@echo off
@ ECHO.
@ ECHO ──────────────────────────────────
@ ECHO 以下是测试本机到外网的连接状况。请观察time的数值，单位是ms(毫秒)。
@ ECHO 空闲时time值应为10ms~20ms。若time=100ms以上说明下载占用了较多带宽。
@ ECHO 若每隔几行就出现Request time out说明传输不连续，网络软件容易掉线。
@ ECHO ──────────────────────────────────
ping www.baidu.com -t
