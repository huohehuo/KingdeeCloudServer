package Server.CodeCheck;

import Bean.CodeCheckBean;
import Bean.DownloadReturnBean;
import Utils.CommonJson;
import Utils.JDBCUtil;
import Utils.Lg;
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
 * 条码检测（出库）
 */
@WebServlet("/CodeCheckForBoxReBox4P1")
public class CodeCheckForBoxReBox4P1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CodeCheckForBoxReBox4P1() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String parameter = request.getParameter("json");
		Gson gson = new Gson();
		Connection conn = null;
		PreparedStatement sta = null;
		boolean execute = true;
		ResultSet rs = null;
		ArrayList<DownloadReturnBean.WortPrintData> list = new ArrayList<>();
		if(parameter!=null&&!parameter.equals("")){
			try {
				DownloadReturnBean downloadReturnBean = new DownloadReturnBean();
				System.out.println(parameter);
				conn = JDBCUtil.getConn(getDataBaseUrl.getUrl(request.getParameter("sqlip"), request.getParameter("sqlport"), request.getParameter("sqlname")), request.getParameter("sqlpass"), request.getParameter("sqluser"));
				System.out.println(request.getParameter("sqlip")+" "+request.getParameter("sqlport")+" "+request.getParameter("sqlname")+" "+request.getParameter("sqlpass")+" "+request.getParameter("sqluser"));

				CodeCheckBean bean = gson.fromJson(parameter, CodeCheckBean.class);
				sta = conn.prepareStatement("exec proc_AssembleCatch1_check ?");

					sta.setString(1, bean.FBarCode);
//					sta.setString(2, bean.FPDAID);
//					sta.setString(3, bean.FBarCode);
//					sta.setString(4, bean.FStockID);
//					sta.setString(5, bean.FStockPlaceID);
					rs = sta.executeQuery();
					if (rs != null) {
						while (rs.next()) {
							DownloadReturnBean.WortPrintData cBean = downloadReturnBean.new WortPrintData();
							cBean.FTip					=rs.getString("说明");
////							cBean.FQtySum					=rs.getString("总数量");
////							cBean.FVolSum					=rs.getString("总体积");
//							cBean.FBoxCode					=rs.getString("箱码");
////							cBean.FDate						=rs.getString("装箱日期");
////							cBean.FUser						=rs.getString("制单人");
//							cBean.FName						=rs.getString("名称");
////							cBean.FUnit						=rs.getString("单位");
//							cBean.FLenght					=rs.getString("长度");
////							cBean.FM2Sum					=rs.getString("总面积");
//							cBean.FM2						=rs.getString("面积");
//							cBean.FItemID					=rs.getString("商品ID");
//							cBean.FUnitID					=rs.getString("单位ID");
//							cBean.FDCStockID				=rs.getString("仓库ID");
//							cBean.FDCSPID					=rs.getString("仓位ID");
//							cBean.FSTOCKORGID				=rs.getString("库存组织ID");
//							cBean.FOWNERID					=rs.getString("货主ID");

							cBean.FBatch					=rs.getString("批号");
							cBean.FModel					=rs.getString("规格");
							cBean.FM2					=rs.getString("体积");
							cBean.FQty						=rs.getString("数量");
							cBean.FWide						=rs.getString("宽度");
							cBean.FFBaoNum					=rs.getString("包数");
							cBean.FFBaoNo					=rs.getString("分包号");
							cBean.FLev						=rs.getString("等级");
							cBean.FCarNo					=rs.getString("车牌号");
							cBean.FType					=rs.getString("箱码类型");
							cBean.FStorageNumber				=rs.getString("仓库编码");
							cBean.FOrgNumber				=rs.getString("库存组织编码");
							cBean.FHuozhuNumber				=rs.getString("货主编码");

							list.add(cBean);
						}
					}

				downloadReturnBean.wortPrintDatas = list;
				Lg.e("CodeCheckForBoxOut4P1箱码出库验证返回："+list.size(),list);
				response.getWriter().write(CommonJson.getCommonJson(true,gson.toJson(downloadReturnBean)));
//				response.getWriter().write(CommonJson.getCommonJson(true, ""));
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.getWriter().write(CommonJson.getCommonJson(false,"数据库错误\r\n----------------\r\n错误原因:\r\n"+e.toString()));

			}finally {
				JDBCUtil.close(null,sta,conn);
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
