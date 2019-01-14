package Bean;

import java.util.ArrayList;

public class DownloadReturnBean {
    public ArrayList<bibiezhong> bibiezhongs;//�ұ��
    public ArrayList<department> department;//���ű�
    public ArrayList<employee> employee;//ְԱ��
    public ArrayList<wavehouse> wavehouse;//��λ��
    public ArrayList<InstorageNum> InstorageNum;//����
    public ArrayList<storage> storage;//�ֿ��
    public ArrayList<Unit> units;//��λ��
    public ArrayList<BarCode> BarCode;//�����
    public ArrayList<suppliers> suppliers;//��Ӧ�̱�
    public ArrayList<payType> payTypes;//���㷽ʽ��
    public ArrayList<product> products;//��Ʒ���ϱ�
    public ArrayList<User> User;//�û���Ϣ��
    public ArrayList<Client> clients;//�ͻ���Ϣ��
    public ArrayList<GetGoodsDepartment> getGoodsDepartments;//������λ
    public ArrayList<purchaseMethod> purchaseMethod;//����/�ɹ���ʽ��
    public ArrayList<yuandanType> yuandanTypes;//Դ������
    public ArrayList<wanglaikemu> wanglaikemu;//������Ŀ
    public ArrayList<PriceMethod> priceMethods;//�۸�����
    public ArrayList<InStorageType> inStorageTypes;
    public ArrayList<buyer> buyers;
    public ArrayList<StoreMan> storeMans;
    public ArrayList<SaleMan> saleMans;
    public ArrayList<Org> orgs;
    public int size;

    public class InStorageType {
        public String FID;
        public String FName;
    }
    public class Org{
        public String FOrgID;
        public String FNumber;
        public String FName;
    }
    public class buyer{
        public String FID;
        public String FNumber;
        public String FName;
        public String FDeptID;
        @Override
        public String toString() {
            return "buyer{" +
                    "FID='" + FID + '\'' +
                    ", FNumber='" + FNumber + '\'' +
                    ", FName='" + FName + '\'' +
                    ", FDeptID='" + FDeptID + '\'' +
                    '}';
        }
    }
    public class StoreMan{
        public String FID;
        public String FNumber;
        public String FName;
        public String FDeptID;

        @Override
        public String toString() {
            return "StoreMan{" +
                    "FID='" + FID + '\'' +
                    ", FNumber='" + FNumber + '\'' +
                    ", FName='" + FName + '\'' +
                    ", FDeptID='" + FDeptID + '\'' +
                    '}';
        }
    }
    public class SaleMan{
        public String FID;
        public String FNumber;
        public String FName;
        public String FDeptID;
        @Override
        public String toString() {
            return "SaleMan{" +
                    "FID='" + FID + '\'' +
                    ", FNumber='" + FNumber + '\'' +
                    ", FName='" + FName + '\'' +
                    ", FDeptID='" + FDeptID + '\'' +
                    '}';
        }
    }

    public class bibiezhong {
        public String FCurrencyID;
        public String FNumber;
        public String FName;
        public String FExChangeRate;
        public String fClassTypeId;
    }

    public class department {
        public String FItemID;
        public String FISSTOCK;
        public String FNumber;
        public String FName;

        @Override
        public String toString() {
            return "department{" +
                    "FItemID='" + FItemID + '\'' +
                    "FISSTOCK='" + FISSTOCK + '\'' +
                    ", FNumber='" + FNumber + '\'' +
                    ", FName='" + FName + '\'' +
                    '}';
        }
        //        public String FparentID;
    }

    public class employee {
        public String FItemID;
        public String FNumber;
        public String FName;
        public String FDpartmentID;
        public String FEmpGroup;
        public String FEmpGroupID;
    }

    public class wavehouse {
        public String FSPID;
        public String FNumber;
        public String FName;
//        public String FSPGroupID;
//        public String FFullName;
//        public String FLevel;
//        public String FDetail;
//        public String FParentID;
//        public String FClassTypeID;
//        public String FDefaultSPID;

    }

    public class InstorageNum {
        public String FItemID;
        public String FStockID;
        public String FQty;
        public String FStoreState;
        public String FStoreOrgID;
        public String FBatchNo;
        public String FUnitID;

    }

    public class storage {
        public String FItemID;
        public String FName;
        public String FNumber;
        public String FIsOpenWaveHouse;
        public String FallowFStore;//允许负库存
//        public String FEmpID;
//        public String FTypeID;
//        public String FSPGroupID;
//        public String FGroupID;
//        public String FStockGroupID;
//        public String FIsStockMgr;
//        public String FUnderStock;
    }


    public class Unit {
        public String FMeasureUnitID;
        public String FNumber;
        public String FName;
//        public String FUnitGroupID;
//        public String FCoefficient;
    }

    public class BarCode {
        public String FName;
        public String FTypeID;
        public String FItemID;
        public String FBarCode;
        public String FNumber;
        public String FUnitID;

        @Override
        public String toString() {
            return "BarCode{" +
                    "FName='" + FName + '\'' +
                    ", FTypeID='" + FTypeID + '\'' +
                    ", FItemID='" + FItemID + '\'' +
                    ", FBarCode='" + FBarCode + '\'' +
                    ", FNumber='" + FNumber + '\'' +
                    ", FUnitID='" + FUnitID + '\'' +
                    '}';
        }
    }

    public class suppliers {
        public String FItemID;
        public String FNumber;
        public String FName;
//        public String FItemClassID;
//        public String FParentID;
//        public String FLevel;
//        public String FDetail;
//        public String FAddress;
//        public String FPhone;
//        public String FEmail;

        @Override
        public String toString() {
            return "suppliers{" +
                    "FItemID='" + FItemID + '\'' +
//                    ", FItemClassID='" + FItemClassID + '\'' +
                    ", FNumber='" + FNumber + '\'' +
//                    ", FParentID='" + FParentID + '\'' +
//                    ", FLevel='" + FLevel + '\'' +
//                    ", FDetail='" + FDetail + '\'' +
                    ", FName='" + FName + '\'' +
//                    ", FAddress='" + FAddress + '\'' +
//                    ", FPhone='" + FPhone + '\'' +
//                    ", FEmail='" + FEmail + '\'' +
                    '}';
        }
    }

    public class payType {
        public String FItemID;
        public String FName;
        public String FNumber;
    }


    public class product {
        public String FProduceUnitID;
        public String FPurchaseUnitID;
        public String FPurchasePriceUnitID;
        public String FSaleUnitID;
        public String FSalePriceUnitID;
        public String FStoreUnitID;
        public String FAuxUnitID;
        public String FStockID;
        public String FStockPlaceID;
        public String FIsBatchManage;
        public String FIsKFperiod;
        public String FExpperiod;
        public String FExpUnit;
        public String FIsPurchase;
        public String FIsSale;
        public String FIsInventory;
        public String FIsProduce;
        public String FIsSubContract;
        public String FIsAsset;
        public String FBaseUnitID;
        public String FFWeightUnitID;
        public String FVolumeUnitID;
        public String FBarcode;
        public String FGrossWeight;
        public String FNetWeight;
        public String FLenght;
        public String FWidth;
        public String FHeight;
        public String FVolume;
        public String FMaterialid;
        public String FNumber;
        public String FoldNumber;
        public String FName;
        public String FModel;
        public String FMnemoniccode;
    }

    public class User {
        public String FUserID;
        public String FName;
        public String FPassWord;
        public String FEmpID;
    }

    public class Client {
        public String FItemID;
        public String FNumber;
        public String FName;
//        public String FItemClassID;
//        public String FParentID;
//        public String FLevel;
//        public String FDetail;
//        public String FAddress;
//        public String FPhone;
//        public String FEmail;
//        public String FTypeID;
    }

    public class GetGoodsDepartment {
        public String FItemID;
        public String FDeleted;
        public String FNumber;
        public String FName;
        public String FDetail;
    }

    public class purchaseMethod {
        public String FTypeID;
        public String FItemID;
        public String FNumber;
        public String FName;
    }

    public class yuandanType {
        public String FID;
        public String FName_CHS;
    }


    public class wanglaikemu {
        public String FAccountID;
        public String FNumber;
        public String FFullName;
    }

    public class PriceMethod {
        public String FInterID;
        public String FEntryID;
        public String FPri;
        public String FPrice;
        public String FName;
        public String FItemID;
        public String FUnitID;
        public String FRelatedID;
        public String FBegQty;
        public String FEndQty;
        public String FBegDate;
        public String FEndDate;
    }


}
