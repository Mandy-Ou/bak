ON ERROR RESUME NEXT

Dim VOL_PROD_KEY
if Wscript.arguments.count<1 then
   VOL_PROD_KEY=InputBox("本程序能帮助您更换 WindowsXP 的序列号。"&vbCr&vbCr&"序列号由您自己寻找并输入。下面列出的不一定是您当前用的序列号，仅作格式参考。"&vbCr&vbCr&"请输入新的序列号：","WindowsXP序列号更换器","MRX3F-47B9T-2487J-KWKMF-RPWBY")
   if VOL_PROD_KEY="" then
     Wscript.quit
   end if
else
   VOL_PROD_KEY = Wscript.arguments.Item(0)
end if

VOL_PROD_KEY = Replace(VOL_PROD_KEY,"-","") 'remove hyphens if any

for each Obj in GetObject("winmgmts:{impersonationLevel=impersonate}").InstancesOf ("win32_WindowsProductActivation")

   result = Obj.SetProductKey (VOL_PROD_KEY)

   if err = 0 then
      Wscript.echo "修改成功。请点击附带的网址到微软网站进行验证。祝您好运！"
   end if

   if err <> 0 then
      Wscript.echo "替换失败！您输入的序列号有误。"
      Err.Clear
   end if

Next
	
