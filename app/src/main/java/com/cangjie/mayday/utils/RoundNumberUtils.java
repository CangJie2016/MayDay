package com.cangjie.mayday.utils;

/**
 * Created by 李振强 on 2017/8/4.
 */

//取整工具类
public class RoundNumberUtils {
    public static String transformMoneyString(double costMoney) {
        // 取整，然后判断两个数是否相等
        double tempDouble = Math.floor(costMoney);
        //相等，返回int型，即不带小数点的字符串
        if (costMoney == tempDouble){
            return String.valueOf((int)costMoney);
        }else{
            //不相等，返回Double的字符串
            return String.valueOf(costMoney);
        }
    }
}
