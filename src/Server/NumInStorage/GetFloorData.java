package Server.NumInStorage;

import Bean.DownloadReturnBean;
import Bean.FloorBean;
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
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by NB on 2017/8/7.
 */
@WebServlet(urlPatterns = "/GetFloorData")
public class GetFloorData extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement sta = null;
        ResultSet rs = null;
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        String json = request.getParameter("json");
        ArrayList<FloorBean> container = new ArrayList<>();
        if(json!=null&&!json.equals("")){
            try {
                DownloadReturnBean downloadReturnBean = new DownloadReturnBean();
                conn = JDBCUtil.getConn(getDataBaseUrl.getUrl(request.getParameter("sqlip")
                        , request.getParameter("sqlport"), request.getParameter("sqlname")),
                        request.getParameter("sqlpass"), request.getParameter("sqluser"));
                System.out.println(request.getParameter("sqlip")+" "
                        +request.getParameter("sqlport")+" "+request.getParameter("sqlname")
                        +" "+request.getParameter("sqlpass")+" "+request.getParameter("sqluser"));
                String SQL = "select top 100 st019.FNumber as 辅助属性,st017.FNumber as 实际规格,isnull(t5.FNumber,isnull(t_20.FNumber,t_22.FNumber)) as 货主编码,t0.FSTOCKID,t0.fmaterialid as 商品ID,t31.FSTOREUNITID as 单位ID,t2.FNumber as 商品代码,t2L.FName as 商品名称,t2L.FSPECIFICATION as 规格,t0.FNumber as 批号,ROUND( t0.FQty,t100.FPRECISION)  as 库存数,'PCS' as 单位,t3.FName as 基本单位,t4.FName as 辅助单位,t5U.FName as 库存单位  from ( select t0.FAUXPROPID,t0.FSTOCKORGID, t0.FOWNERID,t0.FBASEUNITID,t0.FMATERIALID,t0.FSTOCKID,st035.FNUMBER, sum(CAST(CASE  WHEN (T2.FSTOREURNOM = 0 OR T2.FSTOREURNUM = 0) THEN T0.FBASEQTY ELSE (CAST((T0.FBASEQTY * T2.FSTOREURNOM) AS NUMERIC(23, 10)) / T2.FSTOREURNUM) END AS NUMERIC(23, 10))) as FQty,t0.FOWNERTYPEID   from T_STK_INVENTORY t0 left join T_BD_MATERIALSTOCK t2 on t0.FMATERIALID =t2.FMATERIALID  LEFT OUTER JOIN T_BD_LOTMASTER st035 ON t0.FLOT = st035.FLOTID     group by t0.FOWNERID,t0.FMATERIALID,t0.FAUXPROPID,t0.FSTOCKID,st035.FNUMBER,t0.FBASEUNITID,t0.FSTOCKSTATUSID,t0.FSTOCKORGID,t0.FOWNERTYPEID) t0" +
                        " left join T_BD_MATERIAL t2 on t0.FMATERIALID = t2.FMaterialid left join t_bd_material_l t2L on  t2.fmaterialid=t2L.fmaterialid left join T_BD_MATERIALSTOCK t31 on t31.fmaterialid=t0.fmaterialid left join T_BD_UNIT_L t3 on t0.FBASEUNITID=t3.FUnitID left join T_BD_UNIT_L t4 on t31.FAUXUNITID=t4.FUnitID left join T_BD_UNIT_L t5U on t31.FSTOREUNITID=t5U.FUnitID left join T_ORG_Organizations t5 on t0.FOWNERID=t5.FORGID LEFT OUTER JOIN T_BD_FLEXSITEMDETAILV st011 ON t0.FAUXPROPID = st011.FID LEFT OUTER JOIN T_BAS_ASSISTANTDATAENTRY st017 ON st011.FF100001 = st017.FEntryId LEFT OUTER JOIN T_BD_FLEXSITEMDETAILV st018 ON t0.FAUXPROPID = st018.FID LEFT OUTER JOIN T_BAS_ASSISTANTDATAENTRY st019 ON st018.FF100002 = st019.FEntryId left join t_BD_Supplier t_20  on t_20.FSUPPLIERID = t0.FOWNERID left join t_BD_Customer t_22  on t_22.FCUSTID  = t0.FOWNERID left join T_BD_UNIT t100 on t5U.FUNITID=t100.FUNITID  LEFT OUTER JOIN T_ORG_Organizations st012 ON t0.FSTOCKORGID = st012.FOrgID where t0.FQty>0  and st012.FNumber = '107.03' and t0.FOWNERTYPEID = N'BD_Customer' and t2.FNumber=?";
                sta = conn.prepareStatement(SQL);
                sta.setString(1,json);
                rs = sta.executeQuery();
//                ArrayList<InStorageNumListBean.inStoreList> container = new ArrayList<>();
//                InStorageNumListBean iBean = new InStorageNumListBean();
                while (rs.next()){
                    FloorBean inBean = new FloorBean();
                    inBean.FName = rs.getString("商品名称");
                    inBean.FNumber = rs.getString("商品代码");
                    inBean.FInterID = rs.getString("商品ID");
                    inBean.FModel = rs.getString("规格");
                    inBean.FRealModel = rs.getString("实际规格");
                    inBean.FBatchNo = rs.getString("批号");
                    inBean.FUnitID = rs.getString("单位ID");
                    inBean.FUnit = rs.getString("单位");
                    inBean.FBaseUnit = rs.getString("基本单位");
                    inBean.FStoreUnit = rs.getString("库存单位");
                    inBean.FAuxUnit = rs.getString("辅助单位");
                    inBean.FAuxAttr = rs.getString("辅助属性");
                    inBean.FHuozhuNumber = rs.getString("货主编码");
                    inBean.FQty = rs.getString("库存数");
                    container.add(inBean);
                }
                if(container.size()>0){
                    downloadReturnBean.floorBeans = container;
                    writer.write(CommonJson.getCommonJson(true,new Gson().toJson(downloadReturnBean)));
                }else{
                    writer.write(CommonJson.getCommonJson(false,"未查询到数据"));
                }
            } catch (SQLException e) {
                writer.write(CommonJson.getCommonJson(false,"数据库错误\r\n----------------\r\n错误原因:\r\n"+e.toString()));
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                writer.write(CommonJson.getCommonJson(false,"服务器错误\r\n----------------\r\n错误原因:\r\n"+e.toString()));
                e.printStackTrace();
            }finally {
                JDBCUtil.close(rs,sta,conn);
            }

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
