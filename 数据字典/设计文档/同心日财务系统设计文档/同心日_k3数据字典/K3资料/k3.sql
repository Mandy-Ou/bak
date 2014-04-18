select * from t_Account		/* ¿ÆÄ¿±í */

select * from  t_Currency

select * from t_MeasureUnit	

select * from t_unitgroup
	
select * from  t_settle

select * from  t_ItemClass where FItemClassId = 2024

select * from t_Item where FitemId = 121


select * from t_VoucherGroup

select * from t_Voucher

select * from t_VoucherEntry where FVoucherID = 5

select * from t_ItemDetail WHERE FDetailID IN (7,2)

select * from t_ItemDetailV WHERE FDetailID IN (7)


select * from t_Log

select * from t_UserType

select * from t_UserDict

SELECT * FROM USERS

select * from information_schema.columns where table_name='t_AcctGroup'

select newid()

select * from t_tableDescription where FTableName like '%t_AcctGroup%'

select * from t_FieldDescription where FTableId=10001

select * from t_Log

Select FClassID,FGroupID,FName AS FName, Fmodifytime,UUID From t_AcctGroup Order By FClassID, FGroupID


select * from t_AcctGroup

select * from t_Emp