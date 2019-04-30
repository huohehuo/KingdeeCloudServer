package Utils;

public class MathUtil {
    //去掉末尾为.0的数
    public static String cutZero(String num){
        String string;
        if (num.endsWith(".0")){
//            Lg.e("有0");
//            Lg.e("去掉0",string.substring(0,string.length()-2));
            string = num.substring(0,num.length()-2);
        }else{
            string=num;
        }
        return string;
    }
}
