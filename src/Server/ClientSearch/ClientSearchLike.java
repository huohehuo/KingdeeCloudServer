package Server.ClientSearch;

import Bean.DownloadReturnBean;
import Utils.CommonJson;
import Utils.JDBCUtil;
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
 * Created by NB on 2017/8/7.
 */
@WebServlet(urlPatterns = "/ClientSearchLike")
public class ClientSearchLike extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String parameter = request.getParameter("json");
        String version = request.getParameter("version");
        String SQL = "";
        Gson gson = new Gson();
        Connection conn = null;
        PreparedStatement sta = null;
        ResultSet rs = null;
        ArrayList<DownloadReturnBean.Client> container = new ArrayList<DownloadReturnBean.Client>();
        System.out.println(parameter);
        if (parameter != null) {
            try {
                conn = JDBCUtil.getConn(request);
                SQL = "SELECT t0.FCUSTID as 客户ID,t0.FNUMBER as 客户编码,t1.FNAME as 客户名称 FROM t_BD_Customer t0 LEFT OUTER JOIN t_BD_Customer_L t1 ON (t0.FCUSTID = t1.FCUSTID AND t1.FLocaleId = 2052) WHERE ((t0.FFORBIDSTATUS = 'A' AND t0.FUSEORGID = 1) AND t0.FUSEORGID IN (1, 0)) and (t0.FNUMBER like '%"+parameter+"%' or t1.FNAME like '%"+parameter+"%')";
                sta = conn.prepareStatement(SQL);
                System.out.println("SQL:"+SQL);
                rs = sta.executeQuery();
                DownloadReturnBean downloadReturnBean = new DownloadReturnBean();
                if(rs!=null){
                    int i = rs.getRow();
                    System.out.println("rs的长度"+i);
                    while (rs.next()) {
                        DownloadReturnBean.Client bean = downloadReturnBean.new Client();
                        bean.FItemID = rs.getString("客户ID");
                        bean.FNumber = rs.getString("客户编码");
                        bean.FName = rs.getString("客户名称");
                        container.add(bean);
                    }
                    System.out.println("获得客户数据："+container.toString());
                    downloadReturnBean.clients = container;
                    response.getWriter().write(CommonJson.getCommonJson(true,gson.toJson(downloadReturnBean)));
                }else{
                    response.getWriter().write(CommonJson.getCommonJson(false,"未查询到数据"));
                }


            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().write(CommonJson.getCommonJson(false,"数据库错误\r\n----------------\r\n错误原因:\r\n"+e.toString()));

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                response.getWriter().write(CommonJson.getCommonJson(false,"数据库错误\r\n----------------\r\n错误原因:\r\n"+e.toString()));

            }

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
