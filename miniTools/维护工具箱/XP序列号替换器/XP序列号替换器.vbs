ON ERROR RESUME NEXT

Dim VOL_PROD_KEY
if Wscript.arguments.count<1 then
   VOL_PROD_KEY=InputBox("�������ܰ��������� WindowsXP �����кš�"&vbCr&vbCr&"���к������Լ�Ѱ�Ҳ����롣�����г��Ĳ�һ��������ǰ�õ����кţ�������ʽ�ο���"&vbCr&vbCr&"�������µ����кţ�","WindowsXP���кŸ�����","MRX3F-47B9T-2487J-KWKMF-RPWBY")
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
      Wscript.echo "�޸ĳɹ���������������ַ��΢����վ������֤��ף�����ˣ�"
   end if

   if err <> 0 then
      Wscript.echo "�滻ʧ�ܣ�����������к�����"
      Err.Clear
   end if

Next
	
