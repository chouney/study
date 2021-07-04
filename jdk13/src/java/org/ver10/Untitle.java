package org.ver10;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

/**
 * 类型推断:
 * 局部变量类型推荐仅限于如下使用场景：
 * 本地变量初始化。
 * 增强for循环中。
 * 传统for循环中声明的索引变量。
 * Try-with-resources 变量。​
 *
 * Java官方表示，它不能用于以下几个地方：
 * 方法参数
 * 构造函数参数
 * 方法返回类型
 * 字段
 * try-catch表达式（或任何其他类型的变量声明）
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
