package Server.PushDown;

import Bean.PushDownListRequestBean;
import Bean.PushDownListReturnBean;
import Utils.CommonJson;
import Utils.JDBCUtil;
import Utils.getDataBaseUrl;
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

/**
 * Created by NB on 2017/8/23.
 *              获取单据列表信息
 */
@WebServlet("/PushDownList")
public class PushDownList extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        Connection conn = null;
        PreparedStatement sta = null;
        ResultSet rs = null;
        ArrayList<PushDownListReturnBean.PushDownListBean> container = new ArrayList<>();
        Gson gson = new Gson();
        String SQL = "";
        String condition = "";
        try {
            String json = request.getParameter("json");
            System.out.println("获得json："+json);
            PushDownListRequestBean pushDownListRequestBean = gson.fromJson(json, PushDownListRequestBean.class);
            if (pushDownListRequestBean.FWLUnitID != null && !pushDownListRequestBean.FWLUnitID.equals("")) {
                switch (pushDownListRequestBean.id){
                    case 1:
                        condition += "and  t0.FSUPPLIERID = " + pushDownListRequestBean.FWLUnitID;
                        break;
                    case 2:
                    case 3:
                        condition += "and  t0.FCUSTID = " + pushDownListRequestBean.FWLUnitID;
                        break;
                    case 4:
                    case 5:
                        condition += "and  t0.FCUSTOMERID = " + pushDownListRequestBean.FWLUnitID;
                        break;
                    case 6:
                        condition += "and  t0.FRECEIVECUSID = " + pushDownListRequestBean.FWLUnitID;
                        break;
                }
            }
            if (pushDownListRequestBean.code != null && !pushDownListRequestBean.code.equals("")) {
                condition += "and  t0.FBILLNO like \'%" + pushDownListRequestBean.code + "%\'";
            }
            if (pushDownListRequestBean.StartTime != null && !pushDownListRequestBean.StartTime.equals("") && pushDownListRequestBean.endTime != null && !pushDownListRequestBean.endTime.equals("")) {
                condition += "and  t0.FDate between " + "\'" + pushDownListRequestBean.StartTime + "\'" + "and" + "\'" + pushDownListRequestBean.endTime + "\'";
            }
            System.out.println("查询条件:" + condition);
            switch (pushDownListRequestBean.id) {
                case 1:
                    //采购订单下推外购入库单
                    SQL = "select  distinct t0.FID,t0.FBILLNO as 单据编号,convert(varchar(100),t0.FDate,23) as 日期,st02.FName as 往来单位,t0.FSUPPLIERID as 往来单位ID from t_PUR_POOrder t0  LEFT OUTER JOIN t_BD_Supplier_L st02 ON t0.FSUPPLIERID = st02.FSupplierId  LEFT OUTER JOIN t_PUR_POOrderEntry t3 ON t0.FID = t3.FID  LEFT OUTER JOIN T_BD_MATERIAL st31 ON t3.FMATERIALID = st31.FMATERIALID LEFT OUTER JOIN t_PUR_POOrderEntry_D t3_D ON t3.FENTRYID = t3_D.FENTRYID LEFT OUTER JOIN t_PUR_POOrderEntry_R t3_R ON t3.FENTRYID = t3_R.FENTRYID where t0.FOBJECTTYPEID = 'PUR_PurchaseOrder' and t0.FDOCUMENTSTATUS = 'C' AND t0.FCANCELSTATUS = 'A' AND t0.FCLOSESTATUS = 'A' AND t3.FMRPFREEZESTATUS = 'A' AND t3.FMRPTERMINATESTATUS = 'A' AND t3.FMRPCLOSESTATUS = 'A' AND t3.FCHANGEFLAG <> 'I'  AND (t3_D.FBASEDELIVERYMAXQTY > t3_R.FBASESTOCKINQTY) AND (t3_D.FBASEDELIVERYMAXQTY > t3_R.FBASEJOINQTY) " + condition + " order by t0.FID desc ";
                    break;
                case 2:
                    //销售订单下推销售出库单
                    SQL = "select  distinct t0.FID,t0.FBILLNO as 单据编号,convert(varchar(100),t0.FDate,23) as 日期,st02.FName as 往来单位,t0.FCUSTID as 往来单位ID from T_SAL_ORDER t0  LEFT OUTER JOIN t_BD_Customer_L st02 ON t0.FCUSTID  = st02.FCUSTID  LEFT OUTER JOIN T_SAL_ORDEREntry t3 ON t0.FID = t3.FID  LEFT OUTER JOIN T_BD_MATERIAL st31 ON t3.FMATERIALID = st31.FMATERIALID LEFT OUTER JOIN T_SAL_ORDEREntry_D t3_D ON t3.FENTRYID = t3_D.FENTRYID LEFT OUTER JOIN T_SAL_ORDEREntry_R t3_R ON t3.FENTRYID = t3_R.FENTRYID where t0.FOBJECTTYPEID = 'SAL_SaleOrder' and t0.FDOCUMENTSTATUS = 'C' AND t0.FCANCELSTATUS = 'A' AND t0.FCLOSESTATUS = 'A' AND t3.FMRPFREEZESTATUS = 'A' AND t3.FMRPTERMINATESTATUS = 'A' AND t3.FMRPCLOSESTATUS = 'A' AND t3.FCHANGEFLAG <> 'I'   AND ((t3_R.FBASECANOUTQTY + (t3_D.FBASEDELIVERYMAXQTY - t3.FBASEUNITQTY)) > 0) OR (t3_R.FBASECANOUTQTY < 0) " + condition + " order by t0.FID desc";
                    break;
                case 3:
                    //销售订单下推销售退货单
                    SQL = "select  distinct t0.FID,t0.FBILLNO as 单据编号,convert(varchar(100),t0.FDate,23) as 日期,st02.FName as 往来单位,t0.FCUSTID as 往来单位ID from T_SAL_ORDER t0  LEFT OUTER JOIN t_BD_Customer_L st02 ON t0.FCUSTID  = st02.FCUSTID  LEFT OUTER JOIN T_SAL_ORDEREntry t3 ON t0.FID = t3.FID  LEFT OUTER JOIN T_BD_MATERIAL st31 ON t3.FMATERIALID = st31.FMATERIALID LEFT OUTER JOIN T_SAL_ORDEREntry_D t3_D ON t3.FENTRYID = t3_D.FENTRYID LEFT OUTER JOIN T_SAL_ORDEREntry_R t3_R ON t3.FENTRYID = t3_R.FENTRYID where t0.FOBJECTTYPEID = 'SAL_SaleOrder' and t0.FDOCUMENTSTATUS = 'C' AND t0.FCANCELSTATUS = 'A' AND t3.FMRPFREEZESTATUS = 'A' AND t3.FMRPTERMINATESTATUS = 'A' and t3.FCHANGEFLAG <> N'I'  AND (t3.FCHANGEFLAG <> N'D') AND NOT EXISTS (SELECT 1 FROM T_BD_MATERIALQUALITY B WHERE (B.FCHECKRETURN = '1' AND B.FMATERIALID = t3.FMATERIALID)) and ABS(t3_R.FBASECANRETURNQTY) > 0 " + condition;
                    break;
                case 4://销售出库单下推销售退货单
                    SQL = "select   distinct t0.FID,t0.FBILLNO as 单据编号,convert(varchar(100),t0.FDate,23) as 日期,st02.FName as 往来单位,t0.FCUSTOMERID as 往来单位ID from T_SAL_OUTSTOCK t0   LEFT OUTER JOIN T_SAL_OUTSTOCKEntry t3 ON t0.FID = t3.FID  LEFT OUTER JOIN t_BD_Customer_L st02 ON t0.FCUSTOMERID  = st02.FCUSTID LEFT OUTER JOIN T_SAL_OUTSTOCKFIN t1 ON t0.FID = t1.FID LEFT OUTER JOIN T_SAL_OUTSTOCKEntry_F  t3_F ON   t3.FENTRYID = t3_F.FENTRYID  LEFT OUTER JOIN T_SAL_OUTSTOCKENTRY_R t2_R ON t3.FENTRYID = t2_R.FENTRYID LEFT OUTER JOIN T_BD_MATERIAL st31 ON t3.FMATERIALID = st31.FMATERIALID left join T_BD_MATERIAL_L st33 ON t3.FMATERIALID = st33.FMATERIALID LEFT OUTER JOIN T_SAL_OUTSTOCKEntry_R t3_R ON t3.FENTRYID = t3_R.FENTRYID where t0.FOBJECTTYPEID = 'SAL_OUTSTOCK' and t0.FDOCUMENTSTATUS = 'C' AND t0.FCANCELSTATUS = 'A' AND t1.FISGENFORIOS = '0' and  ABS(t3_F.FSALBASEQTY) > ABS(t2_R.FBASERETURNQTY) " + condition;
                    break;
                case 5:
                    //发货通知单下推销售出库单
                    SQL = "select  distinct t0.FID,t0.FBILLNO as 单据编号,convert(varchar(100),t0.FDate,23) as 日期,st02.FName as 往来单位,t0.FCUSTOMERID as 往来单位ID from T_SAL_DELIVERYNOTICE t0  LEFT OUTER JOIN t_BD_Customer_L st02 ON t0.FCUSTOMERID  = st02.FCUSTID  LEFT OUTER JOIN T_SAL_DELIVERYNOTICEEntry t3 ON t0.FID = t3.FID  LEFT OUTER JOIN T_BD_MATERIAL st31 ON t3.FMATERIALID = st31.FMATERIALID LEFT OUTER JOIN T_SAL_DELIVERYNOTICEEntry_E t1_E ON t3.FENTRYID = t1_E.FENTRYID   where t0.FOBJECTTYPEID = 'SAL_DELIVERYNOTICE' and t0.FDOCUMENTSTATUS = 'C' AND t0.FCANCELSTATUS = 'A' AND t0.FCLOSESTATUS = 'A'  and  (FBUSINESSTYPE = 'NORMAL' OR FBUSINESSTYPE = 'DRPTRANS' )  AND  t3.FBASEUNITQTY-FBASEJOINOUTQTY>0 " + condition + " order by t0.FID desc";
                    break;
                case 6:
                    //退货通知单下推销售退货单
                    SQL = "select  distinct t0.FID,t0.FBILLNO as 单据编号,convert(varchar(100),t0.FDate,23) as 日期,st02.FName as 往来单位,t0.FRECEIVECUSID as 往来单位ID from T_SAL_RETURNNOTICE t0  LEFT OUTER JOIN t_BD_Customer_L st02 ON t0.FRECEIVECUSID  = st02.FCUSTID  LEFT OUTER JOIN T_SAL_RETURNNOTICEEntry t3 ON t0.FID = t3.FID  LEFT OUTER JOIN T_BD_MATERIAL st31 ON t3.FMATERIALID = st31.FMATERIALID LEFT OUTER JOIN T_SAL_RETURNNOTICEEntry_E t1_E ON t3.FENTRYID = t1_E.FENTRYID   where t0.FOBJECTTYPEID = 'SAL_RETURNNOTICE' and t0.FDOCUMENTSTATUS = 'C' AND t0.FCANCELSTATUS = 'A'  AND  t3.FBASEUNITQTY-FBASEJOINRETQTY>0 " + condition + " order by t0.FID desc";
                    break;

                case 7:
                    //调拨申请单下推分布式调入单
                    SQL = "select distinct(t1.FBillNo),t2.FName,t1.FDate,FDeptID as FSupplyID ,FDeptID,FEmpID," +
                            "'' as FMangerID,t1.FInterID from ICStockBill t1 left join t_Department t2 on " +
                            "t1.FDeptID=t2.FItemID left join ICStockBillEntry t3 on t1.FInterID=t3.FInterID " +
                            "where (t1.FTranType=24 AND ((t1.FCheckerID<=0 OR t1.FCheckerID  IS NULL)  AND  " +
                            "t1.FCancellation = 0))" + condition + "order by t1.FBillNo desc";
                    break;
                case 8://调拨申请单下推分布式调出单
                    SQL = "select  distinct t0.FID,t0.FBILLNO as 单据编号,convert(varchar(100),t0.FDate,23) as 日期,'' as 往来单位,1 as 往来单位ID from T_STK_STKTRANSFERAPP t0     LEFT OUTER JOIN T_STK_STKTRANSFERAPPEntry t3 ON t0.FID = t3.FID  LEFT OUTER JOIN T_BD_MATERIAL st31 ON t3.FMATERIALID = st31.FMATERIALID LEFT OUTER JOIN T_STK_STKTRANSFERAPPEntry_E t3_E ON t3.FENTRYID = t3_E.FENTRYID   where t0.FOBJECTTYPEID = 'STK_TRANSFERAPPLY' and t0.FDOCUMENTSTATUS = 'C' AND t0.FCLOSESTATUS = 'A'  AND t3.FBUSINESSEND = 'A' AND t3.FBUSINESSCLOSE = 'A' and t3.FBASEQTY - t3_E.FNOTRANSOUTREBASEQTY+t3_E.FRETRANSOUTBASEQTY + t3_E.FNOTRANSINLOBASEQTY>0 " + condition + "  order by t0.FID desc";
                    break;



//                case 9://生产单下推产品入库
//                    SQL = "select '' AS FMangerID,'' AS FEmpID,'' AS FSupplyID,'' AS FDeptID,'' as FDate," +
//                            "FInterID,FBillNo,FWorkShop,t2.FName,FPlanCommitDate," +
//                            "row_number() over (order by t1.FBillNo desc) as row_num from ICMO t1 " +
//                            "left join t_Department t2 on t1.FWorkShop = t2.FItemID left join t_ICItem " +
//                            "t3 on t1.FItemID=t3.FItemID where  FAuxQty-FAuxCommitQty>0 and FStatus " +
//                            "in(1,2) " + condition + " and t3.FProChkMde IN (352)";
//                    break;
//                case 10://生产汇报单
//                    SQL = "select distinct  t1.FBillNo,t2.FName,t1.FDate,t1.FWorkShop as FSupplyID ,t1.FWorkShop " +
//                            "as FDeptID,'' FEmpID,'' FMangerID,t1.FInterID from ICMORpt t1 left join ICMORptEntry " +
//                            "t3 on t1.FInterID=t3.FInterID  left join t_Department t2 on t1.FWorkShop=t2.FItemID " +
//                            "where t3.FAuxQtyPass-t3.FAuxQtySelStock>0 and (t1.FStatus=1 or t1.FStatus=2)  " +
//                            "and FCancellation=0" + condition + "order by t1.FBillNo desc";
//                    break;
//                case 11://委外订单下推委外入库
//                    SQL = "select top 50 * from (select distinct  t1.FBillNo,t2.FName,t1.FDate,FSupplyID ,'' FDeptID,'' FEmpID,FMangerID,t1.FInterID from ICSubContract t1 left join t_Supplier t2 on t1.FSupplyID=t2.FItemID left join ICSubContractEntry t3 on t1.FInterID=t3.FInterID where t3.FAuxQty-t3.FAuxCommitQty>0 and (t1.FStatus=1 or t1.FStatus=2) and FCancellation=0 and t3.FMrpClosed = 0) t where 1=1  " + condition + " order by t.FBillNo desc";
//                    break;
//                case 12://委外订单下推委外出库
//                    SQL = "select top 50 * from (select distinct  t1.FBillNo,t2.FName,t1.FDate,FSupplyID ,'' FDeptID,'' FEmpID,FMangerID,t1.FInterID from ICSubContract t1 left join t_Supplier t2 on t1.FSupplyID=t2.FItemID left join PPBOMEntry t3 on t1.FInterID=t3.FICMOInterID where t3.FAuxQtyMust+FAuxQtySupply-t3.FAuxQty>0 and (t1.FStatus=1 or t1.FStatus=2) and FCancellation=0  ) t where 1=1  " + condition + " order by t.FBillNo desc";
//                    break;
//                case 13://生产任务单下推生产领料
//                    SQL = "select top 50 * from (select distinct(t4.FICMOInterID),t1.FInterID,t1.FBillNo," +
//                            "t5.FWorkSHop,'' as FMangerID,'' as FEmpID,'' as FSupplyID,'' as FDeptID," +
//                            "t2.FName as FName,t1.FItemID,t1.FUnitID,FPlanCommitDate as FDate  from ICMO " +
//                            "t1 left join t_Department t2 on t1.FWorkShop = t2.FItemID  left join PPBOMEntry " +
//                            "t4 on t1.FInterID=t4.FICMOInterID left join PPBOM t5 on t4.FInterID=t5.FInterID " +
//                            "where t1.FStatus in(1,2) and t4.FAuxQtyMust+t4.FAuxQtySupply-t4.FAuxQty>0 and " +
//                            "t5.FType<>1067) t  where 1=1  " + condition + " order by t.FBillNo desc";
//                    break;
//                case 14://采购订单下推收料通知单
//                    SQL = "select * from(select distinct(t1.FBillNo),t2.FName,t1.FDate,FSupplyID ,FDeptID," +
//                            "FEmpID,FMangerID,t1.FInterID from POOrder t1 left join t_Supplier t2 on " +
//                            "t1.FSupplyID=t2.FItemID left join POOrderEntry t3 on t1.FInterID=t3.FInterID " +
//                            "where t3.FAuxQty-t3.FAuxCommitQty>0 and (t1.FStatus=1 or t1.FStatus=2) and " +
//                            "t1.FClosed=0 and FCancellation=0) t where 1=1 " + condition + " order by " +
//                            "t.FBillNo desc";
//                    break;
//                case 15://销售订单下推发料通知单
//                    SQL = "select * from(select distinct(t1.FBillNo),t2.FName,t1.FDate,FCustID as FSupplyID ," +
//                            "FDeptID,FEmpID,FMangerID,t1.FInterID from SEOrder t1 left join t_Organization " +
//                            "t2 on t1.FCustID=t2.FItemID left join SEOrderEntry t3 on t1.FInterID=t3.FInterID " +
//                            "where t3.FAuxQty-t3.FAuxCommitQty>0 and (t1.FStatus=1 or t1.FStatus=2) and " +
//                            "t1.FClosed=0 and FCancellation=0) t where 1=1 " + condition + " order by " +
//                            "t.FBillNo desc";
//                    break;
//                case 16://生产任务单下推生生产汇报单
//                    SQL = "select '' AS FMangerID,'' AS FEmpID,'' AS FSupplyID,'' AS FDeptID,'' AS FDate,FInterID,FBillNo,FWorkShop," +
//                            "t2.FName,FPlanCommitDate," +
//                            "row_number() over (order by t1.FBillNo desc) as row_num from ICMO t1 " +
//                            "left join t_Department t2 on t1.FWorkShop = t2.FItemID left join t_ICItem " +
//                            "t3 on t1.FItemID=t3.FItemID where  FAuxQty-FAuxCommitQty>0 and FStatus in(1,2) " +
//                            "" + condition + " and t3.FProChkMde IN (351,353)";
//                    break;
//                case 18://汇报单下推产品入库
//                    if (request.getParameter("version").contains("500")) {
//                        SQL = "select distinct  t1.FBillNo,t2.FName,t1.FDate,t1.FWorkShop as FSupplyID,t1.FWorkShop " +
//                                "as FDeptID,'' FEmpID,'' FMangerID,t1.FInterID from ICMORpt t1 left join ICMORptEntry " +
//                                "t3 on t1.FInterID=t3.FInterID  left join t_Department t2 on t1.FWorkShop=t2.FItemID " +
//                                "where t3.FAuxQtyPass-t3.FAuxQtySelStock>0 and (t1.FStatus=1 or t1.FStatus=2) "
//                                + condition + " and FCancellation=0 ";
//                    } else {
//                        SQL = "select distinct  t1.FBillNo,t2.FName,t1.FDate,t3.FWorkShopID as FSupplyID ,t3.FWorkShopID " +
//                                "as FDeptID,'' FEmpID,'' FMangerID,t1.FInterID from ICMORpt t1 left join ICMORptEntry t3 on t1.FInterID=t3.FInterID  " +
//                                "left join t_Department t2 on t3.FWorkShopID=t2.FItemID where t3.FAuxQtyPass-t3.FAuxQtySelStock>0 " +
//                                "and (t1.FStatus=1 or t1.FStatus=2) " + condition + " and FCancellation=0 ";
//                    }
//                    break;
//                case 19://调拨验货
//                    SQL = "select distinct(t1.FBillNo),t2.FName,t1.FDate,FDeptID FSupplyID ," +
//                            "FDeptID,FEmpID,'' as FMangerID,t1.FInterID from ICStockBill t1 " +
//                            "left join t_Department t2 on t1.FDeptID=t2.FItemID left join " +
//                            "ICStockBillEntry t3 on t1.FInterID=t3.FInterID where " +
//                            "(t1.FTranType=41 AND ((t1.FCheckerID<=0 OR t1.FCheckerID  IS NULL)  " +
//                            "AND  t1.FCancellation = 0 " + condition + "))";
//                    break;
//                case 20:
//                    //下载发货通知调拨
//                    SQL = "select * from (select distinct(t1.FBillNo),t2.FName,t1.FDate,FCustID " +
//                            "as FSupplyID ,FDeptID,FEmpID,'' as FMangerID,t1.FInterID from " +
//                            "SEOutStock t1 left join t_Organization t2 on t1.FCustID=t2.FItemID " +
//                            "left join SEOutStockEntry t3 on t1.FInterID=t3.FInterID where " +
//                            "t3.FAuxQty-t3.FAuxCommitQty>0 and (t1.FStatus=1 or t1.FStatus=2) " +
//                            "and t1.FClosed=0 and FCancellation=0) t  where 1=1 " + condition + " order " +
//                            "by t.FBillNo desc";
//                    break;
//                case 21://要货单下推调拨单
//                    SQL = "select distinct(t1.FBillNo),t2.FName,t1.FBillDate as FDate,FSrcBrID " +
//                            "as FSupplyID ,t1.FDstBrID as FDeptID,FEmpID,'' FMangerID,t1.FID " +
//                            "as FInterID from t_rt_RequestGoods t1 left join t_SonCompany t2 " +
//                            "on t1.FSrcBrID=t2.FItemID left join t_rt_RequestGoodsEntry t3 on " +
//                            "t1.FID=t3.FID where t3.FBaseQty-t3.FDistQty>0 and (t1.FCheckStatus>0  ) " +
//                            "and t1.FClosed=0 and FCancellation<>1 " + condition + "ORDER BY t1.FBillNo ASC ";
//                    break;
//                case 22://产品入库验货
//                    SQL = "select distinct(t1.FBillNo),t2.FName,t1.FDate,FDeptID as FSupplyID ,FDeptID,FEmpID,'' " +
//                            "as FMangerID,t1.FInterID from ICStockBill t1 left join t_Department t2 on t1.FDeptID=t2.FItemID " +
//                            "left join ICStockBillEntry t3 on t1.FInterID=t3.FInterID where (t1.FTranType=2 AND " +
//                            "((t1.FCheckerID<=0 OR t1.FCheckerID  IS NULL)  AND  t1.FCancellation = 0)) " + condition + "ORDER BY t1.FBillNo ASC ";

            }
            System.out.println("下推查找语句：" + SQL);
            conn = JDBCUtil.getConn(request);
            sta = conn.prepareStatement(SQL);
            rs = sta.executeQuery();
            PushDownListReturnBean pushDownListReturnBean = new PushDownListReturnBean();
            while (rs.next()) {
                PushDownListReturnBean.PushDownListBean pushDownListBean = pushDownListReturnBean.new PushDownListBean();
                pushDownListBean.FDate = rs.getString("日期");
                pushDownListBean.FBillNo = rs.getString("单据编号");
                pushDownListBean.FSupply = rs.getString("往来单位");
                pushDownListBean.FSupplyID = rs.getString("往来单位ID");
                pushDownListBean.FID = rs.getString("FID");

//                pushDownListBean.FDeptID = rs.getString("FDeptID");
//                pushDownListBean.FEmpID = rs.getString("FEmpID");
//                pushDownListBean.FManagerID = rs.getString("FMangerID");
//                pushDownListBean.FInterID = rs.getString("FInterID");
//                pushDownListBean.FName = rs.getString("FName");
                pushDownListBean.tag = pushDownListRequestBean.id;
                container.add(pushDownListBean);
            }
            if (container.size() > 0) {
                System.out.println("返回数据："+container.toString());
                pushDownListReturnBean.list = container;
                response.getWriter().write(CommonJson.getCommonJson(true, gson.toJson(pushDownListReturnBean)));
            } else {
                response.getWriter().write(CommonJson.getCommonJson(false, "未找到数据"));
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
