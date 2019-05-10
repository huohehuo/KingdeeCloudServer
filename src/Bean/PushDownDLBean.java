package Bean;

import java.util.ArrayList;

/**
 * Created by NB on 2017/8/24.
 *
 *              下载bean  销售出库单验货
 *
 */
public class PushDownDLBean {
    public ArrayList<DLbean> list;
    public class DLbean{
        public String FSEQ;
        public String FID;
        public String FMaterialID;
        public String FEntryID;
        public String FUnitID;
        public String FNumber;
        public String FName;
        public String FModel;
        public String FBillNo;
        public String FQty;
        public String FQtying;
        public String FTaxPrice;
        public String FStockID;
        public String FBatchNo;
        public String FHuoZhuNumber;
        public String FBaseCanreturnQty;
        public String AuxSign;//辅助标识
        public String ActualModel;//实际规格

//
//        public String FItemID;
//        public String FAuxQty;
//        public String FSCStockID;
//        public String FDCSPID;
//        public String FDCStockName;
//        public String FDCSPName;
//        public String FKFPeriod;
//        public String FAuxPrice;
//        public String FKFDate;

//        @Override
//        public String toString() {
//            return "DLbean{" +
//                    "FName='" + FName + '\'' +
//                    ", FNumber='" + FNumber + '\'' +
//                    ", FModel='" + FModel + '\'' +
//                    ", FBillNo='" + FBillNo + '\'' +
//                    ", FInterID='" + FInterID + '\'' +
//                    ", FEntryID='" + FEntryID + '\'' +
//                    ", FItemID='" + FItemID + '\'' +
//                    ", FUnitID='" + FUnitID + '\'' +
//                    ", FAuxQty='" + FAuxQty + '\'' +
//                    ", FQtying='" + FQtying + '\'' +
//                    ", FDCStockID='" + FDCStockID + '\'' +
//                    ", FSCStockID='" + FSCStockID + '\'' +
//                    ", FDCSPID='" + FDCSPID + '\'' +
//                    ", FDCStockName='" + FDCStockName + '\'' +
//                    ", FDCSPName='" + FDCSPName + '\'' +
//                    ", FBatchNo='" + FBatchNo + '\'' +
//                    ", FKFPeriod='" + FKFPeriod + '\'' +
//                    ", FAuxPrice='" + FAuxPrice + '\'' +
//                    ", FKFDate='" + FKFDate + '\'' +
//                    '}';
//        }
    }
}
