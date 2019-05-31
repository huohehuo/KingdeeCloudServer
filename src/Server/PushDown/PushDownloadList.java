package Server.PushDown;

import Bean.DownLoadSubListBean;
import Bean.PushDownDLBean;
import Bean.PushDownRKBean;
import Utils.CommonJson;
import Utils.JDBCUtil;
import Utils.Lg;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



//获取单据的订单信息
@WebServlet("/PushDownloadList")
public class PushDownloadList extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        Connection conn = null;
        PreparedStatement sta = null;
        ResultSet rs = null;
        ArrayList<PushDownDLBean.DLbean> container = new ArrayList<>();
        ArrayList<PushDownRKBean.Rkbean> containerRk = new ArrayList<>();
        String json = request.getParameter("json");
        DownLoadSubListBean dBean = new Gson().fromJson(json, DownLoadSubListBean.class);
        String SQL = "";
        System.out.println("获得数据:" + json);
        System.out.println("获取tag:" + dBean.tag);
        switch (dBean.tag) {
            case 1://采购订单下推外购入库单
                SQL =   "select '' as 货主编码,t3.FUNITID as 单位ID,st31.FNUMBER as 物料编码,st33.FName as 物料名称,st33.FSPECIFICATION as 规格型号,t0.FBillNo as 订单编号,convert(float,(t3.FSALQTY-t3_R.FJOINQTY)) as 订单数量,0 as 已验数量,t3_F.FTAXPRICE as 含税单价,t3.FMATERIALID as 物料ID,t3.FENTRYID as 明细唯一值,t3.FID as 明细内码,t3.FSEQ as 明细序号 from t_PUR_POOrder t0   LEFT OUTER JOIN t_PUR_POOrderEntry t3 ON t0.FID = t3.FID   LEFT OUTER JOIN t_PUR_POOrderEntry_D t3_D ON t3.FENTRYID = t3_D.FENTRYID  LEFT OUTER JOIN t_PUR_POOrderEntry_F  t3_F ON   t3.FENTRYID = t3_F.FENTRYID LEFT OUTER JOIN T_BD_MATERIAL st31 ON t3.FMATERIALID = st31.FMATERIALID left join T_BD_MATERIAL_L st33 ON t3.FMATERIALID = st33.FMATERIALID LEFT OUTER JOIN t_PUR_POOrderEntry_R t3_R ON t3.FENTRYID = t3_R.FENTRYID   where t0.FOBJECTTYPEID = 'PUR_PurchaseOrder' and t0.FDOCUMENTSTATUS = 'C' AND t0.FCANCELSTATUS = 'A' AND t0.FCLOSESTATUS = 'A' AND t3.FMRPFREEZESTATUS = 'A' AND t3.FMRPTERMINATESTATUS = 'A' AND t3.FMRPCLOSESTATUS = 'A' AND t3.FCHANGEFLAG <> 'I'  AND (t3_D.FBASEDELIVERYMAXQTY > t3_R.FBASESTOCKINQTY) AND (t3_D.FBASEDELIVERYMAXQTY > t3_R.FBASEJOINQTY)  and t0.FBillNo= '" + dBean.interID+"'";
                        break;
            case 2://销售订单下推销售出库单
//                SQL =   "select  t3.FUNITID as 单位ID, st31.FNUMBER as 物料编码,st33.FName as 物料名称,st33.FSPECIFICATION as 规格型号,t0.FBillNo as 订单编号,convert(float,(t2_R.FCANOUTQTY + (t3_D.FDELIVERYMAXQTY - t3.FQTY))) as 订单数量,0 as 已验数量,t3_F.FTAXPRICE as 含税单价,t3.FMATERIALID as 物料ID,t3.FENTRYID as 明细唯一值,t3.FID as 明细内码,t3.FSEQ as 明细序号 from T_SAL_ORDER t0   LEFT OUTER JOIN T_SAL_ORDEREntry t3 ON t0.FID = t3.FID   LEFT OUTER JOIN T_SAL_ORDEREntry_D t3_D ON t3.FENTRYID = t3_D.FENTRYID  LEFT OUTER JOIN T_SAL_ORDEREntry_F  t3_F ON   t3.FENTRYID = t3_F.FENTRYID  LEFT OUTER JOIN T_SAL_ORDERENTRY_R t2_R ON t3.FENTRYID = t2_R.FENTRYID LEFT OUTER JOIN T_BD_MATERIAL st31 ON t3.FMATERIALID = st31.FMATERIALID left join T_BD_MATERIAL_L st33 ON t3.FMATERIALID = st33.FMATERIALID LEFT OUTER JOIN T_SAL_ORDEREntry_R t3_R ON t3.FENTRYID = t3_R.FENTRYID where t0.FOBJECTTYPEID = 'SAL_SaleOrder' and t0.FDOCUMENTSTATUS = 'C' AND t0.FCANCELSTATUS = 'A' AND t0.FCLOSESTATUS = 'A' AND t3.FMRPFREEZESTATUS = 'A' AND t3.FMRPTERMINATESTATUS = 'A' AND t3.FMRPCLOSESTATUS = 'A' AND t3.FCHANGEFLAG <> 'I'  AND ((t2_R.FBASECANOUTQTY + (t3_D.FBASEDELIVERYMAXQTY - t3.FBASEUNITQTY)) > 0 OR (t2_R.FBASECANOUTQTY < 0))  and t0.FBillNo='" + dBean.interID+"'";
//                SQL =   "select  t1008.FNumber as 货主编码,t3.FUNITID as 单位ID, st31.FNUMBER as 物料编码,st33.FName as 物料名称,st33.FSPECIFICATION as 规格型号,t0.FBillNo as 订单编号,convert(float,(t2_R.FCANOUTQTY + (t3_D.FDELIVERYMAXQTY - t3.FQTY))) as 订单数量,0 as 已验数量,t3_F.FTAXPRICE as 含税单价,t3.FMATERIALID as 物料ID,t3.FENTRYID as 明细唯一值,t3.FID as 明细内码,t3.FSEQ as 明细序号 from T_SAL_ORDER t0   LEFT OUTER JOIN T_SAL_ORDEREntry t3 ON t0.FID = t3.FID   LEFT OUTER JOIN T_SAL_ORDEREntry_D t3_D ON t3.FENTRYID = t3_D.FENTRYID  LEFT OUTER JOIN T_SAL_ORDEREntry_F  t3_F ON   t3.FENTRYID = t3_F.FENTRYID  LEFT OUTER JOIN T_SAL_ORDERENTRY_R t2_R ON t3.FENTRYID = t2_R.FENTRYID LEFT OUTER JOIN T_BD_MATERIAL st31 ON t3.FMATERIALID = st31.FMATERIALID left join T_BD_MATERIAL_L st33 ON t3.FMATERIALID = st33.FMATERIALID LEFT OUTER JOIN T_SAL_ORDEREntry_R t3_R ON t3.FENTRYID = t3_R.FENTRYID left join T_ORG_Organizations t1008 on t1008.FORGID =t3.FOWNERID where t0.FOBJECTTYPEID = 'SAL_SaleOrder' and t0.FDOCUMENTSTATUS = 'C' AND t0.FCANCELSTATUS = 'A' AND t0.FCLOSESTATUS = 'A' AND t3.FMRPFREEZESTATUS = 'A' AND t3.FMRPTERMINATESTATUS = 'A' AND t3.FMRPCLOSESTATUS = 'A' AND t3.FCHANGEFLAG <> 'I'  AND ((t2_R.FBASECANOUTQTY + (t3_D.FBASEDELIVERYMAXQTY - t3.FBASEUNITQTY)) > 0 OR (t2_R.FBASECANOUTQTY < 0))  and t0.FBillNo='" + dBean.interID+"'";
                SQL =   "select  st019.FNumber as 辅助标识,st017.FNumber as 实际规格,t1008.FNumber as 货主编码,t3.FUNITID as 单位ID, st31.FNUMBER as 物料编码,st33.FName as 物料名称,st33.FSPECIFICATION as 规格型号,t0.FBillNo as 订单编号,convert(float,(t2_R.FCANOUTQTY + (t3_D.FDELIVERYMAXQTY - t3.FQTY))) as 订单数量,0 as 已验数量,t3_F.FTAXPRICE as 含税单价,t3.FMATERIALID as 物料ID,t3.FENTRYID as 明细唯一值,t3.FID as 明细内码,t3.FSEQ as 明细序号 from T_SAL_ORDER t0   LEFT OUTER JOIN T_SAL_ORDEREntry t3 ON t0.FID = t3.FID   LEFT OUTER JOIN T_SAL_ORDEREntry_D t3_D ON t3.FENTRYID = t3_D.FENTRYID  LEFT OUTER JOIN T_SAL_ORDEREntry_F  t3_F ON   t3.FENTRYID = t3_F.FENTRYID  LEFT OUTER JOIN T_SAL_ORDERENTRY_R t2_R ON t3.FENTRYID = t2_R.FENTRYID LEFT OUTER JOIN T_BD_MATERIAL st31 ON t3.FMATERIALID = st31.FMATERIALID left join T_BD_MATERIAL_L st33 ON t3.FMATERIALID = st33.FMATERIALID LEFT OUTER JOIN T_SAL_ORDEREntry_R t3_R ON t3.FENTRYID = t3_R.FENTRYID left join T_ORG_Organizations t1008 on t1008.FORGID =t3.FOWNERID" +
                        " LEFT OUTER JOIN T_BD_FLEXSITEMDETAILV st011 ON t3.FAUXPROPID = st011.FID LEFT OUTER JOIN T_BAS_ASSISTANTDATAENTRY st017 ON st011.FF100001 = st017.FEntryId LEFT OUTER JOIN T_BD_FLEXSITEMDETAILV st018 ON t3.FAUXPROPID = st018.FID LEFT OUTER JOIN T_BAS_ASSISTANTDATAENTRY st019 ON st018.FF100002 = st019.FEntryId where t0.FOBJECTTYPEID = 'SAL_SaleOrder' and t0.FDOCUMENTSTATUS = 'C' AND t0.FCANCELSTATUS = 'A' AND t0.FCLOSESTATUS = 'A' AND t3.FMRPFREEZESTATUS = 'A' AND t3.FMRPTERMINATESTATUS = 'A' AND t3.FMRPCLOSESTATUS = 'A' AND t3.FCHANGEFLAG <> 'I'  AND ((t2_R.FBASECANOUTQTY + (t3_D.FBASEDELIVERYMAXQTY - t3.FBASEUNITQTY)) > 0 OR (t2_R.FBASECANOUTQTY < 0))  and t0.FBillNo='" + dBean.interID+"'";
                        break;
            case 21://销售订单下推销售出库单
                SQL =   "select  st019.FNumber as 辅助标识,st017.FNumber as 实际规格,t1008.FNumber as 货主编码,t3.FUNITID as 单位ID, st31.FNUMBER as 物料编码,st33.FName as 物料名称,st33.FSPECIFICATION as 规格型号,t0.FBillNo as 订单编号,convert(float,(t2_R.FCANOUTQTY + (t3_D.FDELIVERYMAXQTY - t3.FQTY))) as 订单数量,0 as 已验数量,t3_F.FTAXPRICE as 含税单价,t3.FMATERIALID as 物料ID,t3.FENTRYID as 明细唯一值,t3.FID as 明细内码,t3.FSEQ as 明细序号 from T_SAL_ORDER t0   LEFT OUTER JOIN T_SAL_ORDEREntry t3 ON t0.FID = t3.FID   LEFT OUTER JOIN T_SAL_ORDEREntry_D t3_D ON t3.FENTRYID = t3_D.FENTRYID  LEFT OUTER JOIN T_SAL_ORDEREntry_F  t3_F ON   t3.FENTRYID = t3_F.FENTRYID  LEFT OUTER JOIN T_SAL_ORDERENTRY_R t2_R ON t3.FENTRYID = t2_R.FENTRYID LEFT OUTER JOIN T_BD_MATERIAL st31 ON t3.FMATERIALID = st31.FMATERIALID left join T_BD_MATERIAL_L st33 ON t3.FMATERIALID = st33.FMATERIALID LEFT OUTER JOIN T_SAL_ORDEREntry_R t3_R ON t3.FENTRYID = t3_R.FENTRYID left join T_ORG_Organizations t1008 on t1008.FORGID =t3.FOWNERID" +
                        " LEFT OUTER JOIN T_BD_FLEXSITEMDETAILV st011 ON t3.FAUXPROPID = st011.FID LEFT OUTER JOIN T_BAS_ASSISTANTDATAENTRY st017 ON st011.FF100001 = st017.FEntryId LEFT OUTER JOIN T_BD_FLEXSITEMDETAILV st018 ON t3.FAUXPROPID = st018.FID LEFT OUTER JOIN T_BAS_ASSISTANTDATAENTRY st019 ON st018.FF100002 = st019.FEntryId where t0.FOBJECTTYPEID = 'SAL_SaleOrder' and t0.FDOCUMENTSTATUS = 'C' AND t0.FCANCELSTATUS = 'A' AND t0.FCLOSESTATUS = 'A' AND t3.FMRPFREEZESTATUS = 'A' AND t3.FMRPTERMINATESTATUS = 'A' AND t3.FMRPCLOSESTATUS = 'A' AND t3.FCHANGEFLAG <> 'I'  AND ((t2_R.FBASECANOUTQTY + (t3_D.FBASEDELIVERYMAXQTY - t3.FBASEUNITQTY)) > 0 OR (t2_R.FBASECANOUTQTY < 0))  and t0.FBillNo='" + dBean.interID+"'";
                break;
            case 3://销售订单下推销售退货单
                SQL =   "select  t2_R.FBASECANRETURNQTY,t3.FUNITID 单位ID, st31.FNUMBER as 物料编码,st33.FName as 物料名称,st33.FSPECIFICATION as 规格型号,t0.FBillNo as 订单编号,ABS(t2_R.FBASECANRETURNQTY) as 订单数量,0 as 已验数量,t3_F.FTAXPRICE as 含税单价,t3.FMATERIALID as 物料ID,t3.FENTRYID as 明细唯一值,t3.FID as 明细内码,t3.FSEQ as 明细序号 from T_SAL_ORDER t0   LEFT OUTER JOIN T_SAL_ORDEREntry t3 ON t0.FID = t3.FID   LEFT OUTER JOIN T_SAL_ORDEREntry_D t3_D ON t3.FENTRYID = t3_D.FENTRYID  LEFT OUTER JOIN T_SAL_ORDEREntry_F  t3_F ON   t3.FENTRYID = t3_F.FENTRYID  LEFT OUTER JOIN T_SAL_ORDERENTRY_R t2_R ON t3.FENTRYID = t2_R.FENTRYID LEFT OUTER JOIN T_BD_MATERIAL st31 ON t3.FMATERIALID = st31.FMATERIALID left join T_BD_MATERIAL_L st33 ON t3.FMATERIALID = st33.FMATERIALID LEFT OUTER JOIN T_SAL_ORDEREntry_R t3_R ON t3.FENTRYID = t3_R.FENTRYID where t0.FOBJECTTYPEID = 'SAL_SaleOrder' and t0.FDOCUMENTSTATUS = 'C' AND t0.FCANCELSTATUS = 'A' AND t3.FMRPFREEZESTATUS = 'A' AND t3.FMRPTERMINATESTATUS = 'A' and t3.FCHANGEFLAG <> N'I'  AND (t3.FCHANGEFLAG <> N'D') AND NOT EXISTS (SELECT 1 FROM T_BD_MATERIALQUALITY B WHERE (B.FCHECKRETURN = '1' AND B.FMATERIALID = t3.FMATERIALID)) and ABS(t2_R.FBASECANRETURNQTY) > 0 and t0.FBillNo='" + dBean.interID+"'";
                        break;
            case 4://销售出库单下推销售退货单
                SQL =   "select  t3.FSTOCKID as 仓库ID,t3.FLOT_TEXT as 批号,t3.FUNITID 单位ID, st31.FNUMBER as 物料编码,st33.FName as 物料名称,st33.FSPECIFICATION as 规格型号,t0.FBillNo as 订单编号,(0) as 订单数量,t3_F.FSALBASEQTY-t2_R.FBASERETURNQTY as 已验数量,t3_F.FTAXPRICE as 含税单价,t3.FMATERIALID as 物料ID,t3.FENTRYID as 明细唯一值,t3.FID as 明细内码,t3.FSEQ as 明细序号 from T_SAL_OUTSTOCK t0   LEFT OUTER JOIN T_SAL_OUTSTOCKEntry t3 ON t0.FID = t3.FID   LEFT OUTER JOIN T_SAL_OUTSTOCKFIN t1 ON t0.FID = t1.FID LEFT OUTER JOIN T_SAL_OUTSTOCKEntry_F  t3_F ON   t3.FENTRYID = t3_F.FENTRYID  LEFT OUTER JOIN T_SAL_OUTSTOCKENTRY_R t2_R ON t3.FENTRYID = t2_R.FENTRYID LEFT OUTER JOIN T_BD_MATERIAL st31 ON t3.FMATERIALID = st31.FMATERIALID left join T_BD_MATERIAL_L st33 ON t3.FMATERIALID = st33.FMATERIALID LEFT OUTER JOIN T_SAL_OUTSTOCKEntry_R t3_R ON t3.FENTRYID = t3_R.FENTRYID where t0.FOBJECTTYPEID = 'SAL_OUTSTOCK' and t0.FDOCUMENTSTATUS = 'C' AND t0.FCANCELSTATUS = 'A' AND t1.FISGENFORIOS = '0' and  ABS(t3_F.FSALBASEQTY) > ABS(t2_R.FBASERETURNQTY) and t0.FBillNo='" + dBean.interID+"'";
                        break;
            case 5://发货通知单下推销售出库单
                SQL = "select   t3.FUNITID 单位ID,st31.FNUMBER as 物料编码,st33.FName as 物料名称,st33.FSPECIFICATION as 规格型号,t0.FBillNo as 订单编号,t3.FBASEUNITQTY-FBASEJOINOUTQTY as 订单数量,0 as 已验数量,t3_F.FTAXPRICE as 含税单价,t3.FMATERIALID as 物料ID,t3.FENTRYID as 明细唯一值,t3.FID as 明细内码,t3.FSEQ as 明细序号 from T_SAL_DELIVERYNOTICE t0   LEFT OUTER JOIN T_SAL_DELIVERYNOTICEEntry t3 ON t0.FID = t3.FID   LEFT OUTER JOIN T_SAL_DELIVERYNOTICEEntry_E t3_E ON t3.FENTRYID = t3_E.FENTRYID  LEFT OUTER JOIN T_SAL_DELIVERYNOTICEEntry_F  t3_F ON   t3.FENTRYID = t3_F.FENTRYID   LEFT OUTER JOIN T_BD_MATERIAL st31 ON t3.FMATERIALID = st31.FMATERIALID left join T_BD_MATERIAL_L st33 ON t3.FMATERIALID = st33.FMATERIALID  where t0.FOBJECTTYPEID = 'SAL_DELIVERYNOTICE' and t0.FDOCUMENTSTATUS = 'C' AND t0.FCANCELSTATUS = 'A' AND t0.FCLOSESTATUS = 'A'  and  (FBUSINESSTYPE = 'NORMAL' OR FBUSINESSTYPE = 'DRPTRANS' )  AND  t3.FBASEUNITQTY-FBASEJOINOUTQTY>0 and t0.FBillNo='" + dBean.interID+"'";
                break;
            case 6://退货通知单下推销售退货单
                SQL =   "select   t1008.FNumber as 货主编码,t3.FUNITID 单位ID,st31.FNUMBER as 物料编码,st33.FName as 物料名称,st33.FSPECIFICATION as 规格型号,t0.FBillNo as 订单编号,t3.FQTY-FJOINRETQTY as 订单数量,0 as 已验数量,t3_F.FTAXPRICE as 含税单价,t3.FMATERIALID as 物料ID,t3.FENTRYID as 明细唯一值,t3.FID as 明细内码,t3.FSEQ as 明细序号 from T_SAL_RETURNNOTICE t0   LEFT OUTER JOIN T_SAL_RETURNNOTICEEntry t3 ON t0.FID = t3.FID   LEFT OUTER JOIN T_SAL_RETURNNOTICEEntry_E t3_E ON t3.FENTRYID = t3_E.FENTRYID  LEFT OUTER JOIN T_SAL_RETURNNOTICEEntry_F  t3_F ON   t3.FENTRYID = t3_F.FENTRYID   LEFT OUTER JOIN T_BD_MATERIAL st31 ON t3.FMATERIALID = st31.FMATERIALID left join T_BD_MATERIAL_L st33 ON t3.FMATERIALID = st33.FMATERIALID  left join T_ORG_Organizations t1008 on t1008.FORGID =t3.FOWNERID where t0.FOBJECTTYPEID = 'SAL_RETURNNOTICE' and t0.FDOCUMENTSTATUS = 'C' AND t0.FCANCELSTATUS = 'A' AND  t3.FBASEUNITQTY-FBASEJOINRETQTY>0 and t0.FBillNo='" + dBean.interID+"'";
                        break;
            case 7://调拨申请单下推分布式调入单
                SQL =   "select t11.FName as FStoctName,t12.FName as FSPName,FInterID,FBillNo,FWorkShop,t2.FName as " +
                        "FDepartmentName,t1.FItemID,t1.FUnitID,FPlanCommitDate,FPlanFinishDate,(FAuxQty-FAuxCommitQty) " +
                        "as FAuxQty,(FAuxQtyForItem+FAuxQtyScrap) as FAux,'0' as FQtying,'0' as FAuxPrice,t3.FName," +
                        "t3.FModel,0 as FEntryID,t3.FNumber from ICMO t1 left join t_Department t2 on t1.FWorkShop = " +
                        "t2.FItemID left join t_ICItem t3 on t1.FItemID=t3.FItemID left join t_Stock t11 on t11.FItemID = " +
                        "t3.FDefaultLoc left join t_StockPlace t12 on t3.FSPID = t12.FSPID where  FAuxQty-FAuxCommitQty>0 " +
                        "and FStatus in(1,2) and t1.FInterID=" + dBean.interID;
                         break;
            case 8://调拨申请单下推分布式调出单
                SQL =   "select  t3.FUNITID 单位ID, st31.FNUMBER as 物料编码,st33.FName as 物料编码,st33.FSPECIFICATION as 规格型号,t0.FBillNo as 订单编号, t3.FBASEQTY - t3_E.FNOTRANSOUTREBASEQTY+t3_E.FRETRANSOUTBASEQTY + t3_E.FNOTRANSINLOBASEQTY as 订单数量,0 as 已验数量,0 as 含税单价,t3.FMATERIALID as 物料ID,t3.FENTRYID as 明细唯一值,t3.FID as 明细内码,t3.FSEQ as 明细序号,t3.FSTOCKID as 调入仓库ID,t3.FSTOCKINID as 调出仓库ID, t3.FLOT_TEXT as 批号 from T_STK_STKTRANSFERAPP t0   LEFT OUTER JOIN T_STK_STKTRANSFERAPPENTRY t3 ON t0.FID = t3.FID   LEFT OUTER JOIN T_STK_STKTRANSFERAPPEntry_E t3_E ON t3.FENTRYID = t3_E.FENTRYID  LEFT OUTER JOIN T_BD_MATERIAL st31 ON t3.FMATERIALID = st31.FMATERIALID left join T_BD_MATERIAL_L st33 ON t3.FMATERIALID = st33.FMATERIALID  where t0.FOBJECTTYPEID = 'STK_TRANSFERAPPLY' and t0.FDOCUMENTSTATUS = 'C' AND t0.FCLOSESTATUS = 'A'  AND t3.FBUSINESSEND = 'A' AND t3.FBUSINESSCLOSE = 'A' and t3.FBASEQTY - t3_E.FNOTRANSOUTREBASEQTY+t3_E.FRETRANSOUTBASEQTY + t3_E.FNOTRANSINLOBASEQTY>0 and t0.FBillNo=" + dBean.interID;
                        break;
//            case 11:
//                SQL =   "select t11.FName as FStoctName,t12.FName as FSPName,t3.FName,t3.FNumber,t3.FModel,t2.FBillNo,t1.FInterID," +
//                        "FEntryID,t1.FItemID,t1.FUnitID,convert(float,FAuxQty-FAuxCommitQty) as FAuxQty,convert(float,FAuxPrice) " +
//                        "as FAuxPrice,'0' as FQtying from ICSubContractEntry t1 left join ICSubContract t2 on " +
//                        "t1.FInterID=t2.FInterID left join t_ICItem t3 on t1.FItemID=t3.FItemID left join t_Stock t11 " +
//                        "on t11.FItemID = t3.FDefaultLoc left join t_StockPlace t12 on t3.FSPID = t12.FSPID where " +
//                        "t2.FClosed=0 and (t2.FStatus=1 or t2.FStatus=2) and t1.FAuxQty-t1.FAuxCommitQty>0 and " +
//                        "t1.FInterID=" + dBean.interID;
//                        break;
//            case 12:
//                SQL =   "select t11.FName as FStoctName,t12.FName as FSPName,t3.FName,t3.FNumber,t3.FModel,t2.FBillNo,t2.FInterID," +
//                        "t1.FOrderEntryID as FEntryID,t1.FItemID,t1.FUnitID,convert(float,t1.FAuxQtyMust-t1.FAuxQty) as FAuxQty," +
//                        "0 as FAuxPrice,'0' as FQtying from PPBOMEntry t1 left join ICSubContract t2 on t1.FICMoInterID=" +
//                        "t2.FInterID left join t_ICItem t3 on t1.FItemID=t3.FItemID left join t_Stock t11 on t11.FItemID = " +
//                        "t3.FDefaultLoc left join t_StockPlace t12 on t3.FSPID = t12.FSPID where  (t2.FStatus=1 or t2.FStatus=2) " +
//                        "and t1.FAuxQtyMust-t1.FAuxQty>0 and t2.FInterID=" + dBean.interID;
//                        break;
//            case 13:
//                SQL =   "select '' as FAuxPrice, '0' as FQtying,  t11.FName as FStoctName,t12.FName as FSPName,t4.FItemID,(t4.FAuxQtyMust+t4.FAuxQtySupply-t4.FAuxQty) " +
//                        "as FAuxQty,t4.FICMOInterID,t1.FInterID,t1.FBillNo,t5.FWorkSHop,t2.FName as FDepartmentName,t4.FItemID," +
//                        "t4.FUnitID,FPlanCommitDate,FPlanFinishDate,t3.FName,t3.FModel,t4.FEntryID,t3.FNumber  from ICMO t1 " +
//                        "left join t_Department t2 on t1.FWorkShop = t2.FItemID " +
//                        "left join PPBOMEntry t4 on t1.FInterID=t4.FICMOInterID  left join t_ICItem t3 on t4.FItemID=t3.FItemID " +
//                        "left join PPBOM t5 on t4.FInterID=t5.FInterID left join t_Stock t11 on t11.FItemID = t3.FDefaultLoc " +
//                        "left join t_StockPlace t12 on t3.FSPID = t12.FSPID where t1.FStatus in(1,2) and " +
//                        "t4.FAuxQtyMust+t4.FAuxQtySupply-t4.FAuxQty>0 and t5.FType<>1067 and t1.FInterID = " + dBean.interID;
//                        break;
//            case 14:
//                SQL =   "select t11.FName as FStoctName,t12.FName as FSPName,t3.FName,t3.FNumber,t3.FModel,t2.FBillNo,t1.FInterID,FEntryID,t1.FItemID,t1.FUnitID,convert(float,FAuxQty-FAuxCommitQty) as FAuxQty,convert(float,FAuxPrice) as FAuxPrice,'0' as FQtying " +
//                        "from POOrderEntry t1 left join POOrder t2 on t1.FInterID=t2.FInterID left join t_ICItem t3 on t1.FItemID=" +
//                        "t3.FItemID left join t_Stock t11 on t11.FItemID = t3.FDefaultLoc left join t_StockPlace t12 on t3.FSPID = t12.FSPID where t2.FClosed=0 and (t2.FStatus=1 or t2.FStatus=2) and t1.FAuxQty-t1.FAuxCommitQty>0 and " +
//                        "t1.FInterID=" + dBean.interID;
//                break;
//            case 15://销售订单下推发料通知单
//
//            SQL =   "select t11.FName as FStoctName,t12.FName as FSPName,t3.FName,t3.FNumber,t3.FModel,t2.FBillNo,t1.FInterID,FEntryID,t1.FItemID,t1.FUnitID," +
//                    "convert(float,FAuxQty-FAuxCommitQty) as FAuxQty,convert(float,FAuxPrice) as FAuxPrice,'0' as FQtying " +
//                    "from SEOrderEntry t1 left join SEOrder t2 on t1.FInterID=t2.FInterID left join t_ICItem t3 on t1.FItemID=" +
//                    "t3.FItemID left join t_Stock t11 on t11.FItemID = t3.FDefaultLoc left join t_StockPlace t12 on t3.FSPID = t12.FSPID where t2.FClosed=0 and (t2.FStatus=1 or t2.FStatus=2) and t1.FAuxQty-t1.FAuxCommitQty>0 and " +
//                    "t1.FInterID=" + dBean.interID;
//            break;
//            case 16:
//                SQL =   "select '' AS FStoctName,'' AS FSPName,'0' AS FQtying,'' AS FAuxPrice,FInterID,FBillNo,FWorkShop,t2.FName as FDepartmentName,t1.FItemID,t1.FUnitID,FPlanCommitDate,FPlanFinishDate,(FAuxQty-FAuxCommitQty) as FAuxQty," +
//                        "(FAuxQtyForItem+FAuxQtyScrap) as FAuxQtyForItem,t3.FName,t3.FModel,0 as FEntryID,t3.FNumber from ICMO t1 left join t_Department t2 on t1.FWorkShop = t2.FItemID " +
//                        "left join t_ICItem t3 on t1.FItemID=t3.FItemID where  FAuxQty-FAuxCommitQty>0 and FStatus in(1,2) and t1.FInterID =" + dBean.interID;
//                        break;
//
//            case 18://汇报单下推产品入库
//                SQL =   "select '' AS FStoctName,'' AS FSPName, t3.FName,t3.FNumber,t3.FModel,t2.FBillNo,t1.FInterID,FEntryID,t1.FItemID,t1.FUnitID," +
//                        "convert(float,FAuxQtyPass-FAuxQtySelStock) as FAuxQty,0 as FAuxPrice,'0' as FQtying from " +
//                        "ICMORptEntry t1 left join ICMORpt t2 on t1.FInterID=t2.FInterID left join t_ICItem t3 on " +
//                        "t1.FItemID=t3.FItemID where   (t2.FStatus=1 or t2.FStatus=2) and t1.FAuxQtyPass-" +
//                        "t1.FAuxQtySelStock>0  and t1.FInterID="+dBean.interID;
//                        break;
//            case 19:
//                SQL = "select '' as FSPName,'' as FStoctName, t3.FName,t3.FNumber,t3.FModel,t2.FBillNo," +
//                        "t1.FInterID,FEntryID,t1.FItemID," +
//                        "t1.FUnitID,convert(float,FAuxQty) as FAuxQty,convert(float,FAuxPrice) as " +
//                        "FAuxPrice,'0' as FQtying,t1.FDCStockID,t1.FDCSPID,t1.FBatchNo,t1.FSCStockID," +
//                        "t1.FSCSPID from ICStockBillEntry t1 left join ICStockBill t2 on t1.FInterID=" +
//                        "t2.FInterID left join t_ICItem t3 on t1.FItemID=t3.FItemID where (t2.FTranType" +
//                        "=41 AND ((t2.FCheckerID<=0 OR t2.FCheckerID  IS NULL)  AND  t2.FCancellation = " +
//                        "0)) and t1.FInterID="+dBean.interID;
//                break;
//            case 20:
//                SQL =   "select t11.FName as FStoctName,t12.FName as FSPName,t3.FName,t3.FNumber,t3.FModel,t2.FBillNo,t1.FInterID,FEntryID,t1.FItemID,t1.FUnitID,convert(float,FAuxQty-FAuxCommitQty) as FAuxQty,convert(float,FAuxPrice) as FAuxPrice,'0' as FQtying " +
//                        "from SEOutStockEntry t1 left join SEOutStock t2 on t1.FInterID=t2.FInterID left join t_ICItem t3 on t1.FItemID=" +
//                        "t3.FItemID left join t_Stock t11 on t11.FItemID = t3.FDefaultLoc left join t_StockPlace t12 on t3.FSPID = t12.FSPID where t2.FClosed=0 and (t2.FStatus=1 or t2.FStatus=2) and t1.FAuxQty-t1.FAuxCommitQty>0 and " +
//                        "t1.FInterID=" + dBean.interID;
//                break;
//            case 21:
//                SQL = "select t3.FName,t3.FNumber,t3.FModel,t2.FBillNo,t1.FID as FInterID,FEntryID,t1.FItemID," +
//                        "t1.FUnitID,convert(float,FBaseQty-FDistQty) as FAuxQty,convert(float,t1.FOrderPrice) " +
//                        "as FAuxPrice,'0' as FQtying,t1.FDCStockID,t1.FDCSPID,'' as FSCStockID,'' as FSCSPID " +
//                        "from t_rt_RequestGoodsEntry t1 left join t_rt_RequestGoods t2 on t1.FID=t2.FID left join " +
//                        "t_ICItem t3 on t1.FItemID=t3.FItemID where t1.FBaseQty-t1.FDistQty>0 and (t2.FCheckStatus>0  ) " +
//                        "and t2.FClosed=0 and FCancellation<>1 and t1.FInterID="+dBean.interID;
//                break;
//            case 22:
//                SQL = "select FBillNo,t2.FName,FDate,FDeptID as FSupplyID ,FDeptID,FEmpID,'' as FMangerID,FInterID from " +
//                        "ICStockBill t1 left join t_Department t2 on t1.FDeptID=t2.FItemID  where (t1.FTranType=2 AND " +
//                        "((t1.FCheckerID<=0 OR t1.FCheckerID  IS NULL)  AND  t1.FCancellation = 0) AND t1.FInterID="+dBean.interID+")";
//                break;
        }
        try {
            System.out.println("下载ID：" + dBean.interID);
            System.out.println("SQL:" + SQL);
            conn = JDBCUtil.getConn(request);
            sta = conn.prepareStatement(SQL);
            rs = sta.executeQuery();
            PushDownDLBean pushDownDLBean = new PushDownDLBean();
            PushDownRKBean pushDownRKBean = new PushDownRKBean();
            while (rs.next()) {
                    PushDownDLBean.DLbean dLbean = pushDownDLBean.new DLbean();
                    dLbean.FUnitID = rs.getString("单位ID");
                    dLbean.FNumber = rs.getString("物料编码");
                    dLbean.FName = rs.getString("物料名称");
                    dLbean.FModel = rs.getString("规格型号");
                    dLbean.FBillNo = rs.getString("订单编号");
                    dLbean.FQty = rs.getString("订单数量");
                    dLbean.FQtying = rs.getString("已验数量");
                    dLbean.FTaxPrice = rs.getString("含税单价");
                    dLbean.FMaterialID = rs.getString("物料ID");
                    dLbean.FEntryID = rs.getString("明细唯一值");
                    dLbean.FID = rs.getString("明细内码");
                    dLbean.FSEQ = rs.getString("明细序号");
                    dLbean.FHuoZhuNumber = rs.getString("货主编码");
                    if (dBean.tag==3){
                        dLbean.FBaseCanreturnQty=rs.getString("FBASECANRETURNQTY");
                    }
                    if (dBean.tag==4){
                        dLbean.FStockID=rs.getString("仓库ID");
                        dLbean.FBatchNo=rs.getString("批号");
                    }
                    if (dBean.tag==2||dBean.tag==21){
                        dLbean.AuxSign=rs.getString("辅助标识");
                        Lg.e("辅助表示：",rs.getString("辅助标识"));
                        dLbean.ActualModel=rs.getString("实际规格");
                    }
                    container.add(dLbean);
            }
                if (container.size() > 0) {
                    Lg.e("表体明细：",container);
//                    System.out.println("返回数据："+container.toString());
                    pushDownDLBean.list = container;
                    response.getWriter().write(CommonJson.getCommonJson(true, new Gson().toJson(pushDownDLBean)));
                } else {
                    response.getWriter().write(CommonJson.getCommonJson(false, "没找到数据"));
                }

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write(CommonJson.getCommonJson(false, "数据库错误\r\n----------------\r\n错误原因:\r\n" + e.toString()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().write(CommonJson.getCommonJson(false, "数据库错误\r\n----------------\r\n错误原因:\r\n" + e.toString()));
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
