package WebIO;

import Utils.HttpRequestUtils;
import Utils.JDBCUtil;
import Utils.Lg;
import Utils.getDataBaseUrl;
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
 * 获取物料的相应数据
 */
@WebServlet("/ProductGetDataIO")
public class ProductGetDataIO extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductGetDataIO() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//"json\u003d{  \"server_ip\": \"233z13987d.51mypc.cn\",  \"port\": \"49717\",  \"user_name\": \"sa\",  \"user_pwd\": \"rongyuan@888\",  \"database\": \"AIS20191206085503\",  \"rule1\": \"and (t0.FNumber like \u0027%01%\u0027 or t1.FName like \u0027%01%\u0027)\",  \"rule2\": \"1\",  \"rule3\": \"100\"}"
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
		String rule2 = bean.rule2;
		String rule3 = bean.rule3;
		ArrayList<WebResponse.Product> container = new ArrayList<>();
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
		if (null == rule2 || "".equals(rule2)){
			response.getWriter().write(gson.toJson(new WebResponse(false,"查询条件不完整，请重新检查")));
			return;
		}
		if (null == rule3 || "".equals(rule3)){
			response.getWriter().write(gson.toJson(new WebResponse(false,"查询条件不完整，请重新检查")));
			return;
		}
			try {
				conn  = JDBCUtil.getConn(getDataBaseUrl.getUrl(server_ip, port, database), user_pwd, user_name);
				String SQL = "select * from (select st02.FNumber as 使用组织编码,st02_L.FName as 使用组织,t0.FNumber as 编码,t1.FName as 商品名称,t1.FSPECIFICATION as 规格型号,case when FISBATCHMANAGE=1 then '是' else '否' end as 是否启用批号管理,case when FISKFPERIOD=1 then '是' else '否' end as 是否开启保质期管理,FEXPPERIOD as 保质期,t0.FMNEMONICCODE as 助记码,t2.FBASEUNITID as FUnitID,t1.FMaterialid as FItemID,t0.FMASTERID,ROW_NUMBER()over(order by t0.FUSEORGID ,t0.FNumber) as row  from T_BD_MATERIAL t0 left join t_bd_material_l t1 on (t0.fmaterialid=t1.fmaterialid AND t1.FLocaleId = 2052) left join t_BD_MaterialBase t2 on t2.fmaterialid=t0.fmaterialid  left join T_BD_MATERIALSTOCK t3 on t3.fmaterialid=t0.fmaterialid left join T_BD_MATERIALSALE t4 on t4.fmaterialid=t0.fmaterialid left join T_BD_MATERIALPURCHASE t5 on t5.fmaterialid=t0.fmaterialid left join T_BD_MATERIALPRODUCE t6 on t6.fmaterialid=t0.fmaterialid  LEFT OUTER JOIN T_ORG_Organizations_L st02_L ON (t0.FUSEORGID = st02_L.FOrgID AND st02_L.FLocaleId = 2052) LEFT OUTER JOIN T_ORG_Organizations st02 on st02_L.FORGID=st02.FORGID where  (t0.FDOCUMENTSTATUS = 'C' AND t0.FFORBIDSTATUS = 'A') AND t0.FFORBIDSTATUS = 'A'  " + rule1+ ")t where t.row between '" + rule2 + "' and '" + rule3+ "' order by t.编码";
				Lg.e("请求spl:"+SQL);
				sta = conn.prepareStatement(SQL);
				rs = sta.executeQuery();
				WebResponse connectResponseBean = new WebResponse();
				while(rs.next()){
					WebResponse.Product dBean = connectResponseBean.new Product();
					dBean.FUseOrgNumber = rs.getString("使用组织编码");
					dBean.FUseOrg = rs.getString("使用组织");
					dBean.FNumber = rs.getString("编码");
					dBean.FName = rs.getString("商品名称");
					dBean.FModel = rs.getString("规格型号");
					dBean.FIsOpenBatch = rs.getString("是否启用批号管理");
					dBean.FIsOpenPeriod = rs.getString("是否开启保质期管理");
					dBean.FKFPeriod = rs.getString("保质期");
					dBean.FRemark = rs.getString("助记码");
					dBean.FUnitID = rs.getString("FUnitID");
					dBean.FItemID = rs.getString("FItemID");
					dBean.FMASTERID = rs.getString("FMASTERID");
					dBean.FRow = rs.getString("row");
					container.add(dBean);
				}
				connectResponseBean.state = true;
				connectResponseBean.size = container.size();
				connectResponseBean.backString = "successful~";
				connectResponseBean.products = container;
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
