package WebIO;

import Utils.*;
import WebIO.bean.RequestBean;
import WebIO.bean.WebResponse;
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
 * 获取物料总行数
 */
@WebServlet("/ProductGetCountIO")
public class ProductGetCountIO extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductGetCountIO() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		Connection conn = null;
		PreparedStatement sta = null;
		ResultSet rs = null;
//		ConnectSQLBean sBean = null;
		Gson gson = new Gson();
		String parameter="";
		RequestBean bean=null;
		try{
			parameter = HttpRequestUtils.ReadAsChars(request);//解密数据
			bean= new Gson().fromJson(parameter, RequestBean.class);
			Lg.e("解析post",bean);
		}catch (Exception e){
			response.getWriter().write(gson.toJson(new WebResponse(false,"上传失败,请求体解析错误")));
		}
		if (null == bean){
			response.getWriter().write(gson.toJson(new WebResponse(false,"上传失败,请求体解析错误")));
			return;
		}
		String server_ip = bean.server_ip;
		String port = bean.port;
		String user_name = bean.user_name;
		String user_pwd = bean.user_pwd;
		String database = bean.database;
		String rule1 = bean.rule1;
		ArrayList<WebResponse.DataBaseList> container = new ArrayList<>();
		if (null == server_ip || "".equals(server_ip)){
			response.getWriter().write(gson.toJson(new WebResponse(false,"服务器ip不能为空")));
			return;
		}
		if (null == port || "".equals(port)){
			response.getWriter().write(gson.toJson(new WebResponse(false,"端口不能为空")));
			return;
		}
		if (null == user_name || "".equals(user_name)){
			response.getWriter().write(gson.toJson(new WebResponse(false,"登录名不能为空")));
			return;
		}
		if (null == user_pwd || "".equals(user_pwd)){
			response.getWriter().write(gson.toJson(new WebResponse(false,"用户密码不能为空")));
			return;
		}
		if (null == database || "".equals(database)){
			response.getWriter().write(gson.toJson(new WebResponse(false,"配置的账套不能为空")));
			return;
		}
		if (null == rule1){
			rule1="";
		}
		Lg.e("条件"+rule1);
			try {
				conn  = JDBCUtil.getConn(getDataBaseUrl.getUrl(server_ip, port, database), user_pwd, user_name);
				String SQL = "select count(1) AS 总行数 from T_BD_MATERIAL t0 left join t_bd_material_l t1 on (t0.fmaterialid=t1.fmaterialid AND t1.FLocaleId = 2052) left join t_BD_MaterialBase t2 on t2.fmaterialid=t0.fmaterialid  left join T_BD_MATERIALSTOCK t3 on t3.fmaterialid=t0.fmaterialid left join T_BD_MATERIALSALE t4 on t4.fmaterialid=t0.fmaterialid left join T_BD_MATERIALPURCHASE t5 on t5.fmaterialid=t0.fmaterialid left join T_BD_MATERIALPRODUCE t6 on t6.fmaterialid=t0.fmaterialid  LEFT OUTER JOIN T_ORG_Organizations_L st02_L ON (t0.FUSEORGID = st02_L.FOrgID AND st02_L.FLocaleId = 2052)  where (t0.FDOCUMENTSTATUS = 'C' AND t0.FFORBIDSTATUS = 'A') AND t0.FFORBIDSTATUS = 'A' " + rule1;
				Lg.e("请求spl:"+SQL);
				sta = conn.prepareStatement(SQL);
				rs = sta.executeQuery();
				WebResponse connectResponseBean = new WebResponse();
				while(rs.next()){
					connectResponseBean.size = MathUtil.toInt(rs.getString("总行数"));
				}
				connectResponseBean.state = true;
				connectResponseBean.backString = "successful~";
				Lg.e("请求成功：",connectResponseBean);
				response.getWriter().write(gson.toJson(connectResponseBean));
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				Lg.e("数据库连接失败，请重新检查..."+e.getMessage());
				response.getWriter().write(gson.toJson(new WebResponse(false,"数据库连接失败，请重新检查..."+e.getMessage())));
			}finally {
				JDBCUtil.close(rs,sta,conn);
			}
		

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
