if (exists (select * from sys.objects where name = 'proc_PDABarCodeSign211_Insert2'))
    drop proc proc_PDABarCodeSign211_Insert2
go
create proc proc_PDABarCodeSign211_Insert2
(
 @mainStr nvarchar(1000) --主表参数
  
)
as 
--------------开启一个模式，也就是不再刷新多少行受影响的信息，可以提高性能
set nocount on
--------------开始存储过程
begin
--------------事务选项设置为出错全部回滚
set xact_abort on
--------------开启事务
begin tran
declare  @FShortBarCode varchar(128),--短条码
         @FShortIndex varchar(128),--短流水号
         @FStockNumber varchar(128),--仓库代码
         @FCargoNumber varchar(128),--货主代码
         @FIndex int,--流水号 
         @FMATERIALID int,--商品ID
         @FUnitID int,--单位ID,
         @FBarCode varchar(128),--条码  
         @FDatePrint varchar(128),--打印日期  
         @FQty decimal(28,18),--数量,
         @FBatchNo varchar(125),--批号  
         @FKFPeriod int,@FKFDate varchar(12),
         @FDatePrintShort varchar(128),--短日期  
         @FPrintType varchar(128),--赋值
         @FRemark1 varchar(128),--入库类型
         @FRemark2 varchar(128),--出库类型,
         @FRemark3 varchar(255),--实际规格,
         @FRemark4 varchar(255),--辅助标识,
         @FRemark5 varchar(255),--货主名称,
         @FBatcnNoIndex varchar(128),--批号附加值
         @FRemark6 varchar(255),--辅助数量,
         @FRemark7 varchar(255),--生产编号
         @FRemark8 varchar(255)--设备ID
set @FDatePrint=convert(varchar(128),getdate(),20)
set @FDatePrintShort = convert(varchar(128),getdate(),112)
set @FKFPeriod=0
set @FKFDate = ''
set @FPrintType = '外购烘干板入库'
set @FRemark1 =''
set @FRemark2 =''
set @FMATERIALID = dbo.getString(@mainStr,'|',1) --商品ID 
set @FUnitID = dbo.getString(@mainStr,'|',2) --单位ID 
set @FQty = dbo.getString(@mainStr,'|',3) --数量
set @FRemark3 = dbo.getString(@mainStr,'|',4) --实际规格
set @FRemark4 = dbo.getString(@mainStr,'|',5) --辅助标识
set @FRemark7 = dbo.getString(@mainStr,'|',6) --生产编号
set @FRemark8 = dbo.getString(@mainStr,'|',7) --设备ID(PDA序列号)
set @FStockNumber = dbo.getString(@mainStr,'|',8) --仓库代码
set @FCargoNumber = dbo.getString(@mainStr,'|',9) --货主代码 
 set @FBatcnNoIndex = dbo.getString(@mainStr,'|',10) --批号附加值
set @FRemark5=@FCargoNumber
 
--辅助数量
declare @FAUXQty decimal(20,8),--辅助数量
        @FUnitQty decimal(20,8),--单位数量
        @FAUXUNITID int,--辅助单位
        @FSTOREUNITID int,--库存单位
        @FSTOREQty decimal(20,8),--库存单位数量
        @FBASEUNITID int,--基本单位
        @FBASEQty decimal(20,8),--基本单位数量
        @FCONVERTNUMERATOR decimal(20,8),--基本单位换算率
        @FCONVERTDENOMINATOR decimal(20,8),--辅助单位换算率
        @FMASTERID int --商品IDID
      select  @FMASTERID=FMASTERID  from T_BD_MATERIAL where  FMATERIALID=@FMATERIALID
      select @FBASEUNITID=FBASEUNITID from  t_BD_MaterialBase where  FMATERIALID=@FMATERIALID
      select  @FAUXUNITID=FAUXUNITID,@FSTOREUNITID=FSTOREUNITID  from T_BD_MATERIALSTOCK where  FMATERIALID=@FMATERIALID
     --基本单位数量
     if exists(select 1 from T_BD_UNITCONVERTRATE where   FMASTERID=@FMASTERID and FCURRENTUNITID=@FUnitID)
      select @FBASEQty=@FQty*FCONVERTNUMERATOR/FCONVERTDENOMINATOR from T_BD_UNITCONVERTRATE where   FMASTERID=@FMASTERID and FCURRENTUNITID=@FUnitID     
     else
       select @FBASEQty=@FQty*FCONVERTNUMERATOR/FCONVERTDENOMINATOR from T_BD_UNITCONVERTRATE where   FMASTERID=0 and FCURRENTUNITID=@FUnitID     
     if exists(select 1 from T_BD_UNITCONVERTRATE where   FMASTERID=@FMASTERID and FCURRENTUNITID=@FUnitID)
     --辅助单位数量
		  if(@FAUXUNITID=0)
		 set @FAUXQty=0
		 else
		 begin
      if exists(select 1 from T_BD_UNITCONVERTRATE where   FMASTERID=@FMASTERID and FCURRENTUNITID=@FAUXUNITID)
        select @FAUXQty=@FBASEQty*FCONVERTDENOMINATOR/FCONVERTNUMERATOR  from  T_BD_UNITCONVERTRATE where   FMASTERID=@FMASTERID and FCURRENTUNITID=@FAUXUNITID   
      else
        select @FAUXQty=@FBASEQty*FCONVERTDENOMINATOR/FCONVERTNUMERATOR  from  T_BD_UNITCONVERTRATE where   FMASTERID=0 and FCURRENTUNITID=@FAUXUNITID   
        end
     --库存单位数量 
      if exists(select 1 from T_BD_UNITCONVERTRATE where   FMASTERID=@FMASTERID and FCURRENTUNITID=@FSTOREUNITID)
       select @FSTOREQty=@FBASEQty*FCONVERTDENOMINATOR/FCONVERTNUMERATOR  from  T_BD_UNITCONVERTRATE where   FMASTERID=@FMASTERID and FCURRENTUNITID=@FSTOREUNITID   
      else
        select @FSTOREQty=@FBASEQty*FCONVERTDENOMINATOR/FCONVERTNUMERATOR  from  T_BD_UNITCONVERTRATE where   FMASTERID=0 and FCURRENTUNITID=@FSTOREUNITID   
   --  end
set @FRemark6 = @FAUXQty
--批号生成
if exists(select 1  from T_BD_MATERIALSTOCK where FISBATCHMANAGE =1)
begin
select @FIndex=isnull(max(FIndex),0)+1 from t_PDABatchNoIndex where FShortDate = @FDatePrintShort
if exists(select 1 from t_PDABatchNoIndex where FShortDate = @FDatePrintShort)
begin
  update t_PDABatchNoIndex set FIndex=@FIndex where FShortDate = @FDatePrintShort
end
else
  begin
   insert into t_PDABatchNoIndex(FMATERIALID,FStockNumber,FCargoNumber,FShortDate,FIndex)
   values(@FMATERIALID,@FStockNumber,@FCargoNumber,@FDatePrintShort,@FIndex)
  end
set @FShortIndex='000000000000'+convert(varchar(12),@FIndex)
--set @FBatchNo = @FStockNumber+@FCargoNumber+@FDatePrintShort+right(@FShortIndex,3)
set @FBatchNo = @FStockNumber+@FDatePrintShort+right(@FShortIndex,3)+@FBatcnNoIndex
end
else
begin
set @FBatchNo=''
end
--批号生成
--条码生成
select @FIndex=isnull(max(FIndex),0)+1 from t_PDABarCodeSign where FItemID=@FMATERIALID and FDatePrintShort = @FDatePrintShort
set @FShortBarCode='000000000000'+convert(varchar(12),@FMATERIALID)
set @FShortIndex='000000000000'+convert(varchar(12),@FIndex)
set @FBarCode = right(@FShortBarCode,8)+@FDatePrintShort+right(@FShortIndex,6)
 
--条码生成
 insert into t_PDABarCodeSign(FIndex,FInterID,FEntryID,FItemID,FUnitID,FBarCode,FDatePrint,FQty,FBatchNo,FKFPeriod,FKFDate,FDatePrintShort,FPrintType,FBillNo,FInterIDAssemble,FPathLab,FRemark,FRemark1,FRemark2,FRemark3,FRemark4,FRemark5,FRemark6,FRemark7,FRemark8,F_TypeID,F_FFF_Volume)
                    values(@FIndex,0,0,@FMATERIALID,@FUnitID,@FBarCode,@FDatePrint,@FQty,@FBatchNo,@FKFPeriod,@FKFDate,@FDatePrintShort,@FPrintType,'',0,'','',@FRemark1,@FRemark2,@FRemark3,@FRemark4,@FRemark5,@FRemark6,@FRemark7,@FRemark8,33,@FQty)
create table #Tmp11112 --创建临时表#Tmp
( 
    FBarCode varchar(128),
    FBatchNo varchar(128),
   
)
select @FSTOREQty=ROUND( @FSTOREQty,FPRECISION) from T_BD_UNIT   where FUNITID=@FSTOREUNITID
select @FBASEQty=ROUND( @FBASEQty,FPRECISION) from T_BD_UNIT   where FUNITID=@FBASEUNITID
insert into #Tmp11112(FBarCode,FBatchNo) values(@FBarCode,@FBatchNo)
select FBarCode as 条码,FBatchNo as 批号,convert(float,isnull(@FAUXQty,0)) as 辅助数量,convert(float,@FBASEQty) as 基本单位数量,convert(float,@FSTOREQty) as 库存单位数量  from #Tmp11112
drop table #Tmp11112 
commit tran 
return;
--------------存在错误
if(0<>@@error)
	goto error1
--------------回滚事务	
error1:
	rollback tran;
--------------结束存储过程
end

