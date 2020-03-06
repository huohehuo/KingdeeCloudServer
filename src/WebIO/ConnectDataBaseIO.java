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
 * 连接数据库获取账套信息
 */
@WebServlet("/ConnectDataBaseIO")
public class ConnectDataBaseIO extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConnectDataBaseIO() {
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
//			System.out.println(request.getParameter("json"));
//			try{
////				sBean = gson.fromJson(request.getParameter("json"), ConnectSQLBean.class);
//			}catch(Exception e){
//				response.getWriter().write(CommonJson.getCommonJson(false,"服务器解析失败"));
//			}
			
			try {
				conn  = JDBCUtil.getConn(getDataBaseUrl.getUrl(server_ip, port, "K3DBConfigerRY"), user_pwd, user_name);
				sta = conn.prepareStatement(SQLInfo.GETDATABASE);
				Lg.e(this.getClass(),"ConnectDataBase--:"+SQLInfo.GETDATABASE);
				rs = sta.executeQuery();
				WebResponse connectResponseBean = new WebResponse();
				while(rs.next()){
					WebResponse.DataBaseList dBean = connectResponseBean.new DataBaseList();
					dBean.name = rs.getString("账套名称");
					dBean.Number = rs.getString("账套编码");
					dBean.dataBase = rs.getString("数据库");
					dBean.version = rs.getString("版本");
					dBean.dataBaseID = rs.getString("账套ID");
					container.add(dBean);
				}
				if(container.size()>0){
					connectResponseBean.state = true;
					connectResponseBean.size = container.size();
					connectResponseBean.backString = "successful~";
					connectResponseBean.dataBaseLists = container;
					Lg.e("请求成功：",connectResponseBean);
					response.getWriter().write(gson.toJson(connectResponseBean));
				}else{
					connectResponseBean.state = false;
					connectResponseBean.size = container.size();
					connectResponseBean.backString = "error~";
					connectResponseBean.dataBaseLists = container;
					Lg.e("请求失败：",connectResponseBean);
					response.getWriter().write(gson.toJson(connectResponseBean));
				}
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
