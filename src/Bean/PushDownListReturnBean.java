package Bean;


import java.util.ArrayList;

/**
 * Created by NB on 2017/8/23.
 */
public class PushDownListReturnBean {
    public ArrayList<PushDownListBean> list;
    public class PushDownListBean{
        public String FID;
        public String FBillNo;
        public String FDate;
        public String FSupplyID;
        public String FSupply;

        public String FName;
        public String FSaleOrgID;
        public String FStoreOrgID;
        public String FSaleManID;
        public String FSaleDeptID;
        public String FNot;
        public String FDeptID;
        public String FManagerID;
        public String FEmpID;
        public String FInterID;
        public int tag;

        public String FBillTypeName;

        @Override
        public String toString() {
            return "PushDownListBean{" +
                    "FBillNo='" + FBillNo + '\'' +
                    ", FName='" + FName + '\'' +
                    ", FDate='" + FDate + '\'' +
                    ", FSupply='" + FSupply + '\'' +
                    ", FSupplyID='" + FSupplyID + '\'' +
                    ", FDeptID='" + FDeptID + '\'' +
                    ", FManagerID='" + FManagerID + '\'' +
                    ", FEmpID='" + FEmpID + '\'' +
                    ", FInterID='" + FInterID + '\'' +
                    ", tag=" + tag +
                    '}';
        }
    }
}
