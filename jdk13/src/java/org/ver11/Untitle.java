package org.ver11;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * Lambda参数的局部变量语法
 * 如果lambda表达式的其中一个形式参数使用了var，那所有的参数都必须使用var。
 *
 * @author chriszhang
 * @version 1.0
 * @date 2020/3/10
 */
public class Untitle {

    public static void testCase(){
        //本地变量初始化
        var str = "this is string";
        var integer = 3;
        var encoder = Base64.getEncoder();

        //增强for循环
        for (var s=1;s<=3;s++){
            var encoder1 = Base64.getEncoder();
        }

        //try-with-source
        try(var InputStream = new FileInputStream(Untitle.class.getResource("/").getPath()+"/a.txt")){
            //
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
