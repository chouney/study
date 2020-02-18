package org.core;

import org.springframework.util.StringUtils;

import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main{
    public static void main(String args[]) throws IOException {
        parseAndQuest("input.in");
    }


    public static void parseAndQuest(String inputDataPath) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(
                Main.class.getResource("/").getPath()+"/"+inputDataPath
        )));
        Pattern pattern = Pattern.compile("\"GET (.*?) HTTP/1\\.0\"");
        Pattern pattern1 = Pattern.compile("arrCity=(.*?)&.*&?depCity=(.*?)&.*&?goDate=(.*?)&");
        String questFormat = "http://l-ttsirouteroff1.f.cn2.qunar.com:8080/innerapi/twell/mt/debug?wrappers=xnz.th.qunar.com&fromCity=%s&toCity=%s&fromDate=%s&adultNum=-1&childNum=-1&cabinClass=ALL&%E6%90%9C%E7%B4%A2=%E6%8F%90%E4%BA%A4";
        String line;
        int totalCount = 0;
        int correctCount = 0;
        int realSuccessCount = 0;
        Set<Integer> set = new HashSet<>();
        while(!StringUtils.isEmpty(line = bufferedReader.readLine())){
            Matcher matcher = pattern.matcher(line);
            if(matcher.find()){
                String url = matcher.group(1);
                //子匹配
                Matcher matcher1 = pattern1.matcher(url);
                System.out.println(url);
                if(matcher1.find()){
                    String arrCity = matcher1.group(1);
                    System.out.println(arrCity);
                    String depCity = matcher1.group(2);
                    System.out.println(depCity);
                    String goDate = matcher1.group(3);
                    System.out.println(goDate);
                    if(set.contains((arrCity+","+depCity+","+goDate).hashCode())){
                        continue;
                    }
                    set.add((arrCity+","+depCity+","+goDate).hashCode());
                    if(calCount(arrCity) && calCount(depCity) && calCount(goDate)){
                        //成功统计
                        String reqUrl = String.format(questFormat, URLEncoder.encode(arrCity,"utf-8"),URLEncoder.encode(depCity,"utf-8"),URLEncoder.encode(goDate,"utf-8"));

                        correctCount++;
                    }
                }
                totalCount++;
            }
        }
        System.out.println(correctCount);
        System.out.println(totalCount);
        System.out.println(new BigDecimal(correctCount).
                divide(new BigDecimal(totalCount),4,BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100)).setScale(2,BigDecimal.ROUND_HALF_UP)+"%");
    }

    private static boolean calCount(String data){
        return !StringUtils.isEmpty(data) && data.split(",").length == 3;
    }






}