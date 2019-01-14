package Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Lg {
    public static void e(String json) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        Date curDate = new Date();
        System.out.println(format.format(curDate) + "-：\n" + json);
    }

    public static void e(Class Class, String json) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        Date curDate = new Date();
        System.out.println(format.format(curDate) +"-"+ Class.getSimpleName() + "-：\n" + json);
    }
}
