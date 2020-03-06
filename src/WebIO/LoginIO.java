package WebIO;

import Utils.HttpRequestUtils;
import Utils.Lg;
import WebIO.bean.LoginBean;
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
import java.sql.Statement;
import java.util.*;

/**
 * Servlet implementation class SetPropties
 */
@WebServlet("/LoginIO")
public class LoginIO extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginIO() {
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
//		String user_name = request.getParameter("user_name");
//		String user_pwd = request.getParameter("user_pwd");
//		ArrayList<WebResponse.DataBaseList> container = new ArrayList<>();
//		if (null == user_name || "".equals(user_name)){
//			response.getWriter().write(gson.toJson(new WebResponse(false,"登录名不能为空")));
//			return;
//		}
//		if (null == user_pwd || "".equals(user_pwd)){
//			response.getWriter().write(gson.toJson(new WebResponse(false,"用户密码不能为空")));
//			return;
//		}
//		try {
			//组装登录数据
			List<String> jParas = new ArrayList<>();
			jParas.add("5de9a87c68c63a");
			jParas.add("余尚森");
			jParas.add("888888");
			jParas.add("2052");
//			String[] strings = new String[4];
//			strings[0]="5de9a87c68c63a";
//			strings[1]="余尚森";
//			strings[2]="888888";
//			strings[3]="2052";
//			Lg.e("得到拼接",strings);
			String s=HttpRequestUtils.requestPost("http://120.77.206.67/K3Cloud/Kingdee.BOS.WebApi.ServicesStub.AuthService.ValidateUser.common.kdsvc",getMap(new Gson().toJson(jParas)));
			LoginBean bean = new Gson().fromJson(s, LoginBean.class);
			Lg.e("获取登录数据",bean.getContext().getUserName());
//			String ss=HttpRequestUtils.doPost("http://120.77.206.67/K3Cloud/Kingdee.BOS.WebApi.ServicesStub.AuthService.ValidateUser.common.kdsvc",getMap(new Gson().toJson(strings)));

//			String s=HttpRequestUtils.sendPost("http://120.77.206.67/K3Cloud/Kingdee.BOS.WebApi.ServicesStub.AuthService.ValidateUser.common.kdsvc",getMap("{\"values\":[\"5de9a87c68c63a\",\"余尚森\",\"888888\",2052]}").toString());
//			String s= HttpClientTool.doPost("http://120.77.206.67/K3Cloud/Kingdee.BOS.WebApi.ServicesStub.AuthService.ValidateUser.common.kdsvc",getMap(jParas.toString()));
//			String s= HttpRequestUtils.doPost("http://120.77.206.67/K3Cloud/Kingdee.BOS.WebApi.ServicesStub.AuthService.ValidateUser.common.kdsvc", getMap(jParas.toString()));
//			String s=HttpRequestUtils.sendPost("http://120.77.206.67/K3Cloud/Kingdee.BOS.WebApi.ServicesStub.AuthService.ValidateUser.common.kdsvc",getMap("[\"5de9a87c68c63a\",\"物流-凌圣章\",\"rongyuan998\",2052]").toString());
			Lg.e("得到数据:"+s);








////			if(request.getParameter("json")!=null&&!(request.getParameter("json").equals(""))){
////				ConnectSQLBean cBean = gson.fromJson(request.getParameter("json"), ConnectSQLBean.class);
//				conn1 = JDBCUtil.getConn(getDataBaseUrl.getUrl(server_ip, port, database), user_pwd, user_name);
//				sta1 = conn1.createStatement();
//				System.out.println(request.getParameter("json"));
////				rs1 = sta1.executeQuery("Select FValue From t_SystemProfile Where FCategory='Base' and FKey ='ServicePack'");
////				if(rs1.next()){
////					System.out.println(1+":"+conn1+"");
////					version = rs1.getString("FValue");
////					System.out.println("version:"+version);
////					VersionResulr = version.replace(".", "");
////					System.out.println(2+"VersionResulr:"+VersionResulr);
//					conn = JDBCUtil.getSQLiteConn();
////					System.out.println(3+":"+conn+"");
//					sta = conn.prepareStatement("select * from p_CreateProc where FVersion=100000 order by FDescription");
////					sta.setString(1, VersionResulr);
//					rs = sta.executeQuery();
//					int updateNum=0;
//						while (rs.next()) {
//							try{
//								int result = sta1.executeUpdate(rs.getString("FSqlStr"));
//								System.out.println( rs.getString("FRemark")+"ִ执行结果："+result+"");
//								updateNum++;
//								if(result!=-1&&result!=0){
//									Lg.e("result:"+result+"\r\n错误项目"+ rs.getString("FRemark"));
//									response.getWriter().write(gson.toJson(new WebResponse(false,"result:"+result+"\r\n错误项目"+ rs.getString("FRemark"))));
//								}
//							}catch (SQLServerException e) {
//								Lg.e("SQLException:"+rs.getString("FRemark")+"\r\n"+e.getMessage());
//								response.getWriter().write(gson.toJson(new WebResponse(false,"SQLException:"+rs.getString("FRemark")+"\r\n"+e.getMessage())));
//							}
//
//						}
//					if(updateNum>0&&rs.isAfterLast()){
//						Lg.e("配置成功");
//						response.getWriter().write(gson.toJson(new WebResponse(true,"配置成功")));
//					}else{
//						Lg.e("配置错误,请确认指定文件是否防止正确");
//						response.getWriter().write(gson.toJson(new WebResponse(false,"配置错误,请确认指定文件是否防止正确")));
//					}
////				}else{
////					response.getWriter().write(CommonJson.getCommonJson(false, "未查询到语句"));
////				}
////			}else{
////				response.getWriter().write(CommonJson.getCommonJson(false,  "Json有误"));
////			}
//
//
//		
//		} catch (ClassNotFoundException | SQLException e) {
//			// TODO Auto-generated catch block
//			Lg.e("数据库错误\r\n----------------\r\n错误原因:\r\n"+e.getMessage());
//			response.getWriter().write(gson.toJson(new WebResponse(false,"数据库错误\r\n----------------\r\n错误原因:\r\n"+e.getMessage())));
//		}
//		finally{
//			try {
//				if(sta!=null){
//					sta.close();
//				}
//				if(rs!=null){rs.close();}
//				if(conn!=null){conn1.close();}
//				if(sta!=null){sta1.close();}
////				if(rs!=null){rs1.close();}
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//
//		}
	}

	private static Map<String, String> getMap(String json) {
		Map<String, String> jObj = new HashMap<>();
		UUID uuid = UUID.randomUUID();
		int hashCode = uuid.toString().hashCode();
		jObj.put("format", "1");
		jObj.put("useragent", "ApiClient");
		jObj.put("rid", hashCode+"");
		jObj.put("parameters", chinaToUnicode(json));
		jObj.put("timestamp", new Date().toString());
		jObj.put("v", "1.0");
		Lg.e("请求："+ jObj.toString());
		return jObj;
	}
	/**
	 * 把中文转成Unicode码
	 *
	 * @param str
	 * @return
	 */
	public static String chinaToUnicode(String str) {
		String result = "";
		for (int i = 0; i < str.length(); i++) {
			int chr1 = (char) str.charAt(i);
			if (chr1 >= 19968 && chr1 <= 171941) {// 汉字范围 \u4e00-\u9fa5 (中文)
				result += "\\u" + Integer.toHexString(chr1);
			} else {
				result += str.charAt(i);
			}
		}
		return result;
	}
		
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
