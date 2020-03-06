package WebIO.bean;

import java.util.List;

public class LoginBean {

    /**
     * Message : null
     * MessageCode : CheckPasswordPolicy
     * LoginResultType : 1
     * Context : {"UserLocale":"zh-CN","LogLocale":"","DBid":"5de9a87c68c63a","DatabaseType":3,"SessionId":"5yn4s5t3let12ve3lal3r5rq","UseLanguages":[{"LocaleId":2052,"LocaleName":"涓枃(绠?浣?/涓浗)","Alias":"CN"}],"UserId":110246,"UserName":"浣欏皻妫?","CustomName":"娣卞湷甯傚厓楣忎紒涓氱鐞嗘湁闄愬叕鍙?","DisplayVersion":"7.5.1450.1","DataCenterName":"RH-texs","UserToken":"320c808c-44f5-449c-9cc5-9f42c0c778d3","CurrentOrganizationInfo":{"ID":100009,"AcctOrgType":"1","Name":"娣卞湷浜挎．浼楀悎绉戞妧鏈夐檺鍏徃","FunctionIds":[101,102,103,104,107,108,109,110,111,112,113,114]},"IsCH_ZH_AutoTrans":false,"ClientType":32,"WeiboAuthInfo":{"WeiboUrl":null,"NetWorkID":null,"CompanyNetworkID":null,"Account":" @","AppKey":"FkdTqJiNeCQC0ugp","AppSecret":"yCP3ucK2IQUm2D3heHxiarq1RJZwfcnKullRSMOIEM","TokenKey":" ","TokenSecret":" ","Verify":null,"CallbackUrl":null,"UserId":" ","Charset":{"BodyName":"utf-8","EncodingName":"Unicode (UTF-8)","HeaderName":"utf-8","WebName":"utf-8","WindowsCodePage":1200,"IsBrowserDisplay":true,"IsBrowserSave":true,"IsMailNewsDisplay":true,"IsMailNewsSave":true,"IsSingleByte":false,"EncoderFallback":{"DefaultString":"锟?","MaxCharCount":1},"DecoderFallback":{"DefaultString":"锟?","MaxCharCount":1},"IsReadOnly":true,"CodePage":65001}},"UTimeZone":{"OffsetTicks":288000000000,"StandardName":"(UTC+08:00)鍖椾含锛岄噸搴嗭紝棣欐腐鐗瑰埆琛屾斂鍖猴紝涔岄瞾鏈ㄩ綈","Id":230,"Number":"1078_SYS","CanBeUsed":true},"STimeZone":{"OffsetTicks":288000000000,"StandardName":"(UTC+08:00)鍖椾含锛岄噸搴嗭紝棣欐腐鐗瑰埆琛屾斂鍖猴紝涔岄瞾鏈ㄩ綈","Id":230,"Number":"1078_SYS","CanBeUsed":true},"GDCID":"278fe222-e165-467e-b561-2fe632386c3b"}
     * KDSVCSessionId : null
     * FormId : null
     * RedirectFormParam : null
     * FormInputObject : null
     * ErrorStackTrace : null
     * Lcid : 0
     * AccessToken : null
     * KdAccessResult : null
     * IsSuccessByAPI : true
     */

    private Object Message;
    private String MessageCode;
    private int LoginResultType;
    private ContextBean Context;
    private Object KDSVCSessionId;
    private Object FormId;
    private Object RedirectFormParam;
    private Object FormInputObject;
    private Object ErrorStackTrace;
    private int Lcid;
    private Object AccessToken;
    private Object KdAccessResult;
    private boolean IsSuccessByAPI;

    public Object getMessage() {
        return Message;
    }

    public void setMessage(Object Message) {
        this.Message = Message;
    }

    public String getMessageCode() {
        return MessageCode;
    }

    public void setMessageCode(String MessageCode) {
        this.MessageCode = MessageCode;
    }

    public int getLoginResultType() {
        return LoginResultType;
    }

    public void setLoginResultType(int LoginResultType) {
        this.LoginResultType = LoginResultType;
    }

    public ContextBean getContext() {
        return Context;
    }

    public void setContext(ContextBean Context) {
        this.Context = Context;
    }

    public Object getKDSVCSessionId() {
        return KDSVCSessionId;
    }

    public void setKDSVCSessionId(Object KDSVCSessionId) {
        this.KDSVCSessionId = KDSVCSessionId;
    }

    public Object getFormId() {
        return FormId;
    }

    public void setFormId(Object FormId) {
        this.FormId = FormId;
    }

    public Object getRedirectFormParam() {
        return RedirectFormParam;
    }

    public void setRedirectFormParam(Object RedirectFormParam) {
        this.RedirectFormParam = RedirectFormParam;
    }

    public Object getFormInputObject() {
        return FormInputObject;
    }

    public void setFormInputObject(Object FormInputObject) {
        this.FormInputObject = FormInputObject;
    }

    public Object getErrorStackTrace() {
        return ErrorStackTrace;
    }

    public void setErrorStackTrace(Object ErrorStackTrace) {
        this.ErrorStackTrace = ErrorStackTrace;
    }

    public int getLcid() {
        return Lcid;
    }

    public void setLcid(int Lcid) {
        this.Lcid = Lcid;
    }

    public Object getAccessToken() {
        return AccessToken;
    }

    public void setAccessToken(Object AccessToken) {
        this.AccessToken = AccessToken;
    }

    public Object getKdAccessResult() {
        return KdAccessResult;
    }

    public void setKdAccessResult(Object KdAccessResult) {
        this.KdAccessResult = KdAccessResult;
    }

    public boolean isIsSuccessByAPI() {
        return IsSuccessByAPI;
    }

    public void setIsSuccessByAPI(boolean IsSuccessByAPI) {
        this.IsSuccessByAPI = IsSuccessByAPI;
    }

    public static class ContextBean {
        /**
         * UserLocale : zh-CN
         * LogLocale :
         * DBid : 5de9a87c68c63a
         * DatabaseType : 3
         * SessionId : 5yn4s5t3let12ve3lal3r5rq
         * UseLanguages : [{"LocaleId":2052,"LocaleName":"涓枃(绠?浣?/涓浗)","Alias":"CN"}]
         * UserId : 110246
         * UserName : 浣欏皻妫?
         * CustomName : 娣卞湷甯傚厓楣忎紒涓氱鐞嗘湁闄愬叕鍙?
         * DisplayVersion : 7.5.1450.1
         * DataCenterName : RH-texs
         * UserToken : 320c808c-44f5-449c-9cc5-9f42c0c778d3
         * CurrentOrganizationInfo : {"ID":100009,"AcctOrgType":"1","Name":"娣卞湷浜挎．浼楀悎绉戞妧鏈夐檺鍏徃","FunctionIds":[101,102,103,104,107,108,109,110,111,112,113,114]}
         * IsCH_ZH_AutoTrans : false
         * ClientType : 32
         * WeiboAuthInfo : {"WeiboUrl":null,"NetWorkID":null,"CompanyNetworkID":null,"Account":" @","AppKey":"FkdTqJiNeCQC0ugp","AppSecret":"yCP3ucK2IQUm2D3heHxiarq1RJZwfcnKullRSMOIEM","TokenKey":" ","TokenSecret":" ","Verify":null,"CallbackUrl":null,"UserId":" ","Charset":{"BodyName":"utf-8","EncodingName":"Unicode (UTF-8)","HeaderName":"utf-8","WebName":"utf-8","WindowsCodePage":1200,"IsBrowserDisplay":true,"IsBrowserSave":true,"IsMailNewsDisplay":true,"IsMailNewsSave":true,"IsSingleByte":false,"EncoderFallback":{"DefaultString":"锟?","MaxCharCount":1},"DecoderFallback":{"DefaultString":"锟?","MaxCharCount":1},"IsReadOnly":true,"CodePage":65001}}
         * UTimeZone : {"OffsetTicks":288000000000,"StandardName":"(UTC+08:00)鍖椾含锛岄噸搴嗭紝棣欐腐鐗瑰埆琛屾斂鍖猴紝涔岄瞾鏈ㄩ綈","Id":230,"Number":"1078_SYS","CanBeUsed":true}
         * STimeZone : {"OffsetTicks":288000000000,"StandardName":"(UTC+08:00)鍖椾含锛岄噸搴嗭紝棣欐腐鐗瑰埆琛屾斂鍖猴紝涔岄瞾鏈ㄩ綈","Id":230,"Number":"1078_SYS","CanBeUsed":true}
         * GDCID : 278fe222-e165-467e-b561-2fe632386c3b
         */

        private String UserLocale;
        private String LogLocale;
        private String DBid;
        private int DatabaseType;
        private String SessionId;
        private int UserId;
        private String UserName;
        private String CustomName;
        private String DisplayVersion;
        private String DataCenterName;
        private String UserToken;
        private CurrentOrganizationInfoBean CurrentOrganizationInfo;
        private boolean IsCH_ZH_AutoTrans;
        private int ClientType;
        private WeiboAuthInfoBean WeiboAuthInfo;
        private UTimeZoneBean UTimeZone;
        private STimeZoneBean STimeZone;
        private String GDCID;
        private List<UseLanguagesBean> UseLanguages;

        public String getUserLocale() {
            return UserLocale;
        }

        public void setUserLocale(String UserLocale) {
            this.UserLocale = UserLocale;
        }

        public String getLogLocale() {
            return LogLocale;
        }

        public void setLogLocale(String LogLocale) {
            this.LogLocale = LogLocale;
        }

        public String getDBid() {
            return DBid;
        }

        public void setDBid(String DBid) {
            this.DBid = DBid;
        }

        public int getDatabaseType() {
            return DatabaseType;
        }

        public void setDatabaseType(int DatabaseType) {
            this.DatabaseType = DatabaseType;
        }

        public String getSessionId() {
            return SessionId;
        }

        public void setSessionId(String SessionId) {
            this.SessionId = SessionId;
        }

        public int getUserId() {
            return UserId;
        }

        public void setUserId(int UserId) {
            this.UserId = UserId;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getCustomName() {
            return CustomName;
        }

        public void setCustomName(String CustomName) {
            this.CustomName = CustomName;
        }

        public String getDisplayVersion() {
            return DisplayVersion;
        }

        public void setDisplayVersion(String DisplayVersion) {
            this.DisplayVersion = DisplayVersion;
        }

        public String getDataCenterName() {
            return DataCenterName;
        }

        public void setDataCenterName(String DataCenterName) {
            this.DataCenterName = DataCenterName;
        }

        public String getUserToken() {
            return UserToken;
        }

        public void setUserToken(String UserToken) {
            this.UserToken = UserToken;
        }

        public CurrentOrganizationInfoBean getCurrentOrganizationInfo() {
            return CurrentOrganizationInfo;
        }

        public void setCurrentOrganizationInfo(CurrentOrganizationInfoBean CurrentOrganizationInfo) {
            this.CurrentOrganizationInfo = CurrentOrganizationInfo;
        }

        public boolean isIsCH_ZH_AutoTrans() {
            return IsCH_ZH_AutoTrans;
        }

        public void setIsCH_ZH_AutoTrans(boolean IsCH_ZH_AutoTrans) {
            this.IsCH_ZH_AutoTrans = IsCH_ZH_AutoTrans;
        }

        public int getClientType() {
            return ClientType;
        }

        public void setClientType(int ClientType) {
            this.ClientType = ClientType;
        }

        public WeiboAuthInfoBean getWeiboAuthInfo() {
            return WeiboAuthInfo;
        }

        public void setWeiboAuthInfo(WeiboAuthInfoBean WeiboAuthInfo) {
            this.WeiboAuthInfo = WeiboAuthInfo;
        }

        public UTimeZoneBean getUTimeZone() {
            return UTimeZone;
        }

        public void setUTimeZone(UTimeZoneBean UTimeZone) {
            this.UTimeZone = UTimeZone;
        }

        public STimeZoneBean getSTimeZone() {
            return STimeZone;
        }

        public void setSTimeZone(STimeZoneBean STimeZone) {
            this.STimeZone = STimeZone;
        }

        public String getGDCID() {
            return GDCID;
        }

        public void setGDCID(String GDCID) {
            this.GDCID = GDCID;
        }

        public List<UseLanguagesBean> getUseLanguages() {
            return UseLanguages;
        }

        public void setUseLanguages(List<UseLanguagesBean> UseLanguages) {
            this.UseLanguages = UseLanguages;
        }

        public static class CurrentOrganizationInfoBean {
            /**
             * ID : 100009
             * AcctOrgType : 1
             * Name : 娣卞湷浜挎．浼楀悎绉戞妧鏈夐檺鍏徃
             * FunctionIds : [101,102,103,104,107,108,109,110,111,112,113,114]
             */

            private int ID;
            private String AcctOrgType;
            private String Name;
            private List<Integer> FunctionIds;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getAcctOrgType() {
                return AcctOrgType;
            }

            public void setAcctOrgType(String AcctOrgType) {
                this.AcctOrgType = AcctOrgType;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public List<Integer> getFunctionIds() {
                return FunctionIds;
            }

            public void setFunctionIds(List<Integer> FunctionIds) {
                this.FunctionIds = FunctionIds;
            }
        }

        public static class WeiboAuthInfoBean {
            /**
             * WeiboUrl : null
             * NetWorkID : null
             * CompanyNetworkID : null
             * Account :  @
             * AppKey : FkdTqJiNeCQC0ugp
             * AppSecret : yCP3ucK2IQUm2D3heHxiarq1RJZwfcnKullRSMOIEM
             * TokenKey :
             * TokenSecret :
             * Verify : null
             * CallbackUrl : null
             * UserId :
             * Charset : {"BodyName":"utf-8","EncodingName":"Unicode (UTF-8)","HeaderName":"utf-8","WebName":"utf-8","WindowsCodePage":1200,"IsBrowserDisplay":true,"IsBrowserSave":true,"IsMailNewsDisplay":true,"IsMailNewsSave":true,"IsSingleByte":false,"EncoderFallback":{"DefaultString":"锟?","MaxCharCount":1},"DecoderFallback":{"DefaultString":"锟?","MaxCharCount":1},"IsReadOnly":true,"CodePage":65001}
             */

            private Object WeiboUrl;
            private Object NetWorkID;
            private Object CompanyNetworkID;
            private String Account;
            private String AppKey;
            private String AppSecret;
            private String TokenKey;
            private String TokenSecret;
            private Object Verify;
            private Object CallbackUrl;
            private String UserId;
            private CharsetBean Charset;

            public Object getWeiboUrl() {
                return WeiboUrl;
            }

            public void setWeiboUrl(Object WeiboUrl) {
                this.WeiboUrl = WeiboUrl;
            }

            public Object getNetWorkID() {
                return NetWorkID;
            }

            public void setNetWorkID(Object NetWorkID) {
                this.NetWorkID = NetWorkID;
            }

            public Object getCompanyNetworkID() {
                return CompanyNetworkID;
            }

            public void setCompanyNetworkID(Object CompanyNetworkID) {
                this.CompanyNetworkID = CompanyNetworkID;
            }

            public String getAccount() {
                return Account;
            }

            public void setAccount(String Account) {
                this.Account = Account;
            }

            public String getAppKey() {
                return AppKey;
            }

            public void setAppKey(String AppKey) {
                this.AppKey = AppKey;
            }

            public String getAppSecret() {
                return AppSecret;
            }

            public void setAppSecret(String AppSecret) {
                this.AppSecret = AppSecret;
            }

            public String getTokenKey() {
                return TokenKey;
            }

            public void setTokenKey(String TokenKey) {
                this.TokenKey = TokenKey;
            }

            public String getTokenSecret() {
                return TokenSecret;
            }

            public void setTokenSecret(String TokenSecret) {
                this.TokenSecret = TokenSecret;
            }

            public Object getVerify() {
                return Verify;
            }

            public void setVerify(Object Verify) {
                this.Verify = Verify;
            }

            public Object getCallbackUrl() {
                return CallbackUrl;
            }

            public void setCallbackUrl(Object CallbackUrl) {
                this.CallbackUrl = CallbackUrl;
            }

            public String getUserId() {
                return UserId;
            }

            public void setUserId(String UserId) {
                this.UserId = UserId;
            }

            public CharsetBean getCharset() {
                return Charset;
            }

            public void setCharset(CharsetBean Charset) {
                this.Charset = Charset;
            }

            public static class CharsetBean {
                /**
                 * BodyName : utf-8
                 * EncodingName : Unicode (UTF-8)
                 * HeaderName : utf-8
                 * WebName : utf-8
                 * WindowsCodePage : 1200
                 * IsBrowserDisplay : true
                 * IsBrowserSave : true
                 * IsMailNewsDisplay : true
                 * IsMailNewsSave : true
                 * IsSingleByte : false
                 * EncoderFallback : {"DefaultString":"锟?","MaxCharCount":1}
                 * DecoderFallback : {"DefaultString":"锟?","MaxCharCount":1}
                 * IsReadOnly : true
                 * CodePage : 65001
                 */

                private String BodyName;
                private String EncodingName;
                private String HeaderName;
                private String WebName;
                private int WindowsCodePage;
                private boolean IsBrowserDisplay;
                private boolean IsBrowserSave;
                private boolean IsMailNewsDisplay;
                private boolean IsMailNewsSave;
                private boolean IsSingleByte;
                private EncoderFallbackBean EncoderFallback;
                private DecoderFallbackBean DecoderFallback;
                private boolean IsReadOnly;
                private int CodePage;

                public String getBodyName() {
                    return BodyName;
                }

                public void setBodyName(String BodyName) {
                    this.BodyName = BodyName;
                }

                public String getEncodingName() {
                    return EncodingName;
                }

                public void setEncodingName(String EncodingName) {
                    this.EncodingName = EncodingName;
                }

                public String getHeaderName() {
                    return HeaderName;
                }

                public void setHeaderName(String HeaderName) {
                    this.HeaderName = HeaderName;
                }

                public String getWebName() {
                    return WebName;
                }

                public void setWebName(String WebName) {
                    this.WebName = WebName;
                }

                public int getWindowsCodePage() {
                    return WindowsCodePage;
                }

                public void setWindowsCodePage(int WindowsCodePage) {
                    this.WindowsCodePage = WindowsCodePage;
                }

                public boolean isIsBrowserDisplay() {
                    return IsBrowserDisplay;
                }

                public void setIsBrowserDisplay(boolean IsBrowserDisplay) {
                    this.IsBrowserDisplay = IsBrowserDisplay;
                }

                public boolean isIsBrowserSave() {
                    return IsBrowserSave;
                }

                public void setIsBrowserSave(boolean IsBrowserSave) {
                    this.IsBrowserSave = IsBrowserSave;
                }

                public boolean isIsMailNewsDisplay() {
                    return IsMailNewsDisplay;
                }

                public void setIsMailNewsDisplay(boolean IsMailNewsDisplay) {
                    this.IsMailNewsDisplay = IsMailNewsDisplay;
                }

                public boolean isIsMailNewsSave() {
                    return IsMailNewsSave;
                }

                public void setIsMailNewsSave(boolean IsMailNewsSave) {
                    this.IsMailNewsSave = IsMailNewsSave;
                }

                public boolean isIsSingleByte() {
                    return IsSingleByte;
                }

                public void setIsSingleByte(boolean IsSingleByte) {
                    this.IsSingleByte = IsSingleByte;
                }

                public EncoderFallbackBean getEncoderFallback() {
                    return EncoderFallback;
                }

                public void setEncoderFallback(EncoderFallbackBean EncoderFallback) {
                    this.EncoderFallback = EncoderFallback;
                }

                public DecoderFallbackBean getDecoderFallback() {
                    return DecoderFallback;
                }

                public void setDecoderFallback(DecoderFallbackBean DecoderFallback) {
                    this.DecoderFallback = DecoderFallback;
                }

                public boolean isIsReadOnly() {
                    return IsReadOnly;
                }

                public void setIsReadOnly(boolean IsReadOnly) {
                    this.IsReadOnly = IsReadOnly;
                }

                public int getCodePage() {
                    return CodePage;
                }

                public void setCodePage(int CodePage) {
                    this.CodePage = CodePage;
                }

                public static class EncoderFallbackBean {
                    /**
                     * DefaultString : 锟?
                     * MaxCharCount : 1
                     */

                    private String DefaultString;
                    private int MaxCharCount;

                    public String getDefaultString() {
                        return DefaultString;
                    }

                    public void setDefaultString(String DefaultString) {
                        this.DefaultString = DefaultString;
                    }

                    public int getMaxCharCount() {
                        return MaxCharCount;
                    }

                    public void setMaxCharCount(int MaxCharCount) {
                        this.MaxCharCount = MaxCharCount;
                    }
                }

                public static class DecoderFallbackBean {
                    /**
                     * DefaultString : 锟?
                     * MaxCharCount : 1
                     */

                    private String DefaultString;
                    private int MaxCharCount;

                    public String getDefaultString() {
                        return DefaultString;
                    }

                    public void setDefaultString(String DefaultString) {
                        this.DefaultString = DefaultString;
                    }

                    public int getMaxCharCount() {
                        return MaxCharCount;
                    }

                    public void setMaxCharCount(int MaxCharCount) {
                        this.MaxCharCount = MaxCharCount;
                    }
                }
            }
        }

        public static class UTimeZoneBean {
            /**
             * OffsetTicks : 288000000000
             * StandardName : (UTC+08:00)鍖椾含锛岄噸搴嗭紝棣欐腐鐗瑰埆琛屾斂鍖猴紝涔岄瞾鏈ㄩ綈
             * Id : 230
             * Number : 1078_SYS
             * CanBeUsed : true
             */

            private long OffsetTicks;
            private String StandardName;
            private int Id;
            private String Number;
            private boolean CanBeUsed;

            public long getOffsetTicks() {
                return OffsetTicks;
            }

            public void setOffsetTicks(long OffsetTicks) {
                this.OffsetTicks = OffsetTicks;
            }

            public String getStandardName() {
                return StandardName;
            }

            public void setStandardName(String StandardName) {
                this.StandardName = StandardName;
            }

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public String getNumber() {
                return Number;
            }

            public void setNumber(String Number) {
                this.Number = Number;
            }

            public boolean isCanBeUsed() {
                return CanBeUsed;
            }

            public void setCanBeUsed(boolean CanBeUsed) {
                this.CanBeUsed = CanBeUsed;
            }
        }

        public static class STimeZoneBean {
            /**
             * OffsetTicks : 288000000000
             * StandardName : (UTC+08:00)鍖椾含锛岄噸搴嗭紝棣欐腐鐗瑰埆琛屾斂鍖猴紝涔岄瞾鏈ㄩ綈
             * Id : 230
             * Number : 1078_SYS
             * CanBeUsed : true
             */

            private long OffsetTicks;
            private String StandardName;
            private int Id;
            private String Number;
            private boolean CanBeUsed;

            public long getOffsetTicks() {
                return OffsetTicks;
            }

            public void setOffsetTicks(long OffsetTicks) {
                this.OffsetTicks = OffsetTicks;
            }

            public String getStandardName() {
                return StandardName;
            }

            public void setStandardName(String StandardName) {
                this.StandardName = StandardName;
            }

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public String getNumber() {
                return Number;
            }

            public void setNumber(String Number) {
                this.Number = Number;
            }

            public boolean isCanBeUsed() {
                return CanBeUsed;
            }

            public void setCanBeUsed(boolean CanBeUsed) {
                this.CanBeUsed = CanBeUsed;
            }
        }

        public static class UseLanguagesBean {
            /**
             * LocaleId : 2052
             * LocaleName : 涓枃(绠?浣?/涓浗)
             * Alias : CN
             */

            private int LocaleId;
            private String LocaleName;
            private String Alias;

            public int getLocaleId() {
                return LocaleId;
            }

            public void setLocaleId(int LocaleId) {
                this.LocaleId = LocaleId;
            }

            public String getLocaleName() {
                return LocaleName;
            }

            public void setLocaleName(String LocaleName) {
                this.LocaleName = LocaleName;
            }

            public String getAlias() {
                return Alias;
            }

            public void setAlias(String Alias) {
                this.Alias = Alias;
            }
        }
    }
}
