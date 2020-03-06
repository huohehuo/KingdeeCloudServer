package WebIO;

import Utils.JDBCUtil;
import Utils.Lg;
import Utils.getDataBaseUrl;
import WebIO.bean.WebResponse;
import com.google.gson.Gson;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**
 * 配置
 */
@WebServlet("/SetProptiesIO")
public class SetProptiesIO extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetProptiesIO() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		Connection conn = null;
		Connection conn1 = null;
		PreparedStatement sta = null;
		Statement sta1 = null;
		ResultSet rs = null;
//		ResultSet rs1 = null;
		String version = null;
		String VersionResulr = "";
		String server_ip = request.getParameter("server_ip");
		String port = request.getParameter("port");
		String user_name = request.getParameter("user_name");
		String user_pwd = request.getParameter("user_pwd");
		String database = request.getParameter("database");
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
		try {
//			if(request.getParameter("json")!=null&&!(request.getParameter("json").equals(""))){
//				ConnectSQLBean cBean = gson.fromJson(request.getParameter("json"), ConnectSQLBean.class);
				conn1 = JDBCUtil.getConn(getDataBaseUrl.getUrl(server_ip, port, database), user_pwd, user_name);
				sta1 = conn1.createStatement();
				System.out.println(request.getParameter("json"));
//				rs1 = sta1.executeQuery("Select FValue From t_SystemProfile Where FCategory='Base' and FKey ='ServicePack'");
//				if(rs1.next()){
//					System.out.println(1+":"+conn1+"");
//					version = rs1.getString("FValue");
//					System.out.println("version:"+version);
//					VersionResulr = version.replace(".", "");
//					System.out.println(2+"VersionResulr:"+VersionResulr);
					conn = JDBCUtil.getSQLiteConn();
//					System.out.println(3+":"+conn+"");
					sta = conn.prepareStatement("select * from p_CreateProc where FVersion=100000 order by FDescription");
//					sta.setString(1, VersionResulr);
					rs = sta.executeQuery();
					int updateNum=0;
						while (rs.next()) {
							try{
								int result = sta1.executeUpdate(rs.getString("FSqlStr"));
								System.out.println( rs.getString("FRemark")+"ִ执行结果："+result+"");
								updateNum++;
								if(result!=-1&&result!=0){
									Lg.e("result:"+result+"\r\n错误项目"+ rs.getString("FRemark"));
									response.getWriter().write(gson.toJson(new WebResponse(false,"result:"+result+"\r\n错误项目"+ rs.getString("FRemark"))));
								}
							}catch (SQLServerException e) {
								Lg.e("SQLException:"+rs.getString("FRemark")+"\r\n"+e.getMessage());
								response.getWriter().write(gson.toJson(new WebResponse(false,"SQLException:"+rs.getString("FRemark")+"\r\n"+e.getMessage())));
							}
							
						}
					if(updateNum>0&&rs.isAfterLast()){
						Lg.e("配置成功");
						response.getWriter().write(gson.toJson(new WebResponse(true,"配置成功")));
					}else{
						Lg.e("配置错误,请确认指定文件是否防止正确");
						response.getWriter().write(gson.toJson(new WebResponse(false,"配置错误,请确认指定文件是否防止正确")));
					}
//				}else{
//					response.getWriter().write(CommonJson.getCommonJson(false, "未查询到语句"));
//				}
//			}else{
//				response.getWriter().write(CommonJson.getCommonJson(false,  "Json有误"));
//			}
			
			
//		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			Lg.e("数据库错误\r\n----------------\r\n错误原因:\r\n"+e.getMessage());
			response.getWriter().write(gson.toJson(new WebResponse(false,"数据库错误\r\n----------------\r\n错误原因:\r\n"+e.getMessage())));
		}
		finally{
			try {
				if(sta!=null){
					sta.close();
				}
				if(rs!=null){rs.close();}
				if(conn!=null){conn1.close();}
				if(sta!=null){sta1.close();}
//				if(rs!=null){rs1.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
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
