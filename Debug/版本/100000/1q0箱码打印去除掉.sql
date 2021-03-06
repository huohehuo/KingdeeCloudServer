if (exists (select * from sys.objects where name = 'proc_PDACountOff_Insert1'))
    drop proc proc_PDACountOff_Insert1
go
create proc proc_PDACountOff_Insert1
(
 @mainStr varchar(1000)  
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

declare @FID int,     --单号id 
        @Fdate datetime,       --日期  
        @FBillerID int,   --制单人id
        @FBillNo varchar(50),--单号 
        @FCarNum varchar(128),--车牌号
        @FHuZhu varchar(128),--货主编码
        @FHuoZhuNumber varchar(128),--货主编码
          @FBatchNo varchar(255), --批次
        @FStockNumber varchar(255),--仓库代码 
        @FDCStockID int, --仓库id
  @FDCSPID int, --仓位id 
  @FSTOCKORGID int,--库存组织ID
  @FOWNERID int,  --货主ID
        @FName varchar(255),--名称(取物料名称前2个维度)
        @FSourceID int,--源单单号
        @FShortDate varchar(128),
        @FRemark varchar(200)--摘要 
set @FBillerID = dbo.getString(@mainStr,'|',1) --操作员
set @FCarNum = dbo.getString(@mainStr,'|',2) --车牌号 
set @FHuZhu = dbo.getString(@mainStr,'|',3) --货主描述
set @FSourceID = dbo.getString(@mainStr,'|',4) --源单单号 ID（生产领料单ID）
set @FStockNumber =dbo.getString(@mainStr,'|',5) --仓库代码
set @FDCStockID =dbo.getString(@mainStr,'|',6) --仓库ID
set @FSTOCKORGID =dbo.getString(@mainStr,'|',7) --库存组织ID
set @FOWNERID =dbo.getString(@mainStr,'|',8) --货主ID
 select @FHuoZhuNumber=FNumber from T_ORG_Organizations where FORGID = @FOWNERID
set @Fdate = convert(varchar(20),GETDATE(),20)
set @FShortDate =convert(nvarchar(60),getdate(),112)
------------------------------------------------------------得到箱码
declare  @FIndex int,  --(循环标) 
         @FNum varchar(50) --流水号
        set @FBillNo ='' 
select  @FName=  st31.FName from    T_SP_PICKMTRL t0   LEFT OUTER JOIN T_SP_PICKMTRLData t3 ON t0.FID = t3.FID  LEFT OUTER JOIN T_BD_MATERIAL_L st31 ON t3.FMATERIALID = st31.FMATERIALID where t0.FID =@FSourceID
set  @FName = replace(@FName, reverse(substring(reverse(@FName),1,charindex('*',reverse(@FName)) )) ,'')
select @FIndex=isnull(MAX(FIndex),0)+1 from t_AssembleRecord1 where FShortDate=@FShortDate
set @FNum='000000000'+CONVERT(varchar(20),@FIndex)
set @FNum=right(@FNum,6)  
set @FBillNo='ZZ'+@FShortDate+@FNum
 

INSERT INTO t_AssembleRecord1(FBillNo,FIndex,FDate,FBillerID,FStatus,FShortDate,FCarNum,FHuZhu,FName,FSourceID,FType) values (@FBillNo,@FIndex,@FDate,@FBillerID,0,@FShortDate,@FCarNum,@FHuZhu,@FName,@FSourceID,0)
select @FID=MAX(FID) from t_AssembleRecord1
 
 --批号生成 
 declare @FDatePrintShort varchar(8),
         @FShortIndex varchar(50)
set @FDatePrintShort = RIGHT( convert(nvarchar(6),getdate(),112),4)
select @FIndex=isnull(max(FIndex),0)+1 from t_PDABatchNoIndex1 where FShortDate = @FDatePrintShort and FHuoZhuNumber=@FHuoZhuNumber and FStockNumber=@FStockNumber
if exists(select 1 from t_PDABatchNoIndex1 where FShortDate = @FDatePrintShort  and FHuoZhuNumber=@FHuoZhuNumber and FStockNumber=@FStockNumber)
begin
  update t_PDABatchNoIndex1 set FIndex=@FIndex where FShortDate = @FDatePrintShort  and FHuoZhuNumber=@FHuoZhuNumber and FStockNumber=@FStockNumber
end
else
  begin
   insert into t_PDABatchNoIndex1(FMATERIALID,FStockNumber,FHuoZhuNumber,FShortDate,FIndex)
   values(0,@FStockNumber,@FHuoZhuNumber,@FDatePrintShort,@FIndex)
  end
set @FShortIndex='000000000000'+convert(varchar(12),@FIndex)
set @FBatchNo = @FStockNumber+@FDatePrintShort+right(@FShortIndex,3)
 
--装箱
select @FID= FID  from t_AssembleRecord where FBillNo=@FBillNo
 insert into t_AssembleRecord1Entry(FID,FItemID,FUnitID,FQty,FDCStockID,FDCSPID,FBatchNo,FSTOCKORGID,FOWNERID,FWidth,FLength,FLevel,FVolume)
	select @FID,t3.FMATERIALID,t3.FUNITID,t3.FSTOCKACTUALQTY,@FDCStockID,@FDCSPID,@FBatchNo,@FSTOCKORGID,@FOWNERID,convert(int, dbo.getString(st33.FNumber,'.',6)),dbo.getString(st33.FNumber,'.',7),dbo.getString(st33.FNumber,'.',4),convert(decimal(20,4), FSECACTUALQTY)  from T_SP_PICKMTRLData t3 left join T_BD_MATERIAL st33 ON t3.FMATERIALID = st33.FMATERIALID where t3.FID = @FSourceID
--装箱
--批号生成
------------------------------------------------------------得到箱码
 	select t1.FWidth as 宽度,t1.FPackets as 包数,t1.FStockName as 仓库,convert(float, t6.FQty) as 总数量,convert(decimal(20,4),t6.FVolume) as 总面积,t1.FBillNo as 箱码,t1.FDate as 装箱日期,t3.FName as 制单人,t1.FName as 名称,t1.FHuZhu as 货主描述,t1.FCarNum as 车号,t5.FName as 单位, t4.FSPECIFICATION as 规格,t2.FBatchNo as 批号,t2.FLevel as 等级,convert(decimal(28,0),t2.FQty-isnull(t2.FQtyOut,0)) as 数量,convert(decimal(20,4),t2.FVolume-isnull(t2.FVolumeOut,0)) as 面积 from t_AssembleRecord1 t1 inner join t_AssembleRecord1entry t2 on t1.FID =t2.FID left join  T_SEC_user  t3 on t1.FBillerID=t3.FUSERID left join t_bd_material_l t4 on  t2.FItemID=t4.fmaterialid left join  T_BD_UNIT_L t5 ON  t2.FUNITID = t5.FUNITID inner join (select sum(FQty-isnull(FQtyOut,0)) as FQty,sum(FVolume-isnull(FVolumeOut,0)) as FVolume,FID from t_AssembleRecord1entry where FQty>isnull(FQtyOut,0)  group by FID) t6 on t1.FID =t6.FID  where t1.FBillNo = @FBillNo order by convert(int,t2.FWidth),t2.FLevel,convert(int,FLength) desc
 
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
