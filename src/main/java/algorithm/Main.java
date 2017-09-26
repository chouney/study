package algorithm;


import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

public class Main {
    private void calculate(int[][] l, int[][] u, int[][] ul, int i, int j) {
        if (j - 1 >= 0) {
            l[i][j] = l[i][j - 1] + 1;
        } else {
            l[i][j] = 1;
        }
        if (i - 1 >= 0) {
            u[i][j] = u[i - 1][j] + 1;
        } else {
            u[i][j] = 1;
        }
        int lrLen = Math.min(l[i][j], u[i][j]);
        if (i - 1 >= 0 && j - 1 >= 0) {
            ul[i][j] = Math.min(lrLen, ul[i - 1][j - 1] + 1);
        } else {
            ul[i][j] = 1;
        }
    }

    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }
        int rlen = matrix.length;
        int clen = matrix[0].length;
        int[][] l = new int[rlen][clen];
        int[][] u = new int[rlen][clen];
        int[][] ul = new int[rlen][clen];
        int max = 0;
        for (int i = 0; i < rlen; i++) {
            for (int j = 0; j < clen; j++) {
                if (matrix[i][j] == '1') {
                    calculate(l, u, ul, i, j);
                    max = Math.max(max, ul[i][j]);
                }
            }
        }
        return max * max;
    }

    public int lengthOfLongestSubstring(String s) {
        char[] chs = s.toCharArray();
        int hash[] = new int[26];
        int max = 0;
        int last = -1, i = 0;
        for (; i < chs.length; i++) {
            if (hash[chs[i] - 'a'] != 0) {
                while (chs[++last] != chs[i]) hash[chs[last] - 'a'] = 0;
                hash[chs[last] - 'a'] = 0;
            }
            max = max > i - last ? max : i - last;
            hash[chs[i] - 'a'] = 1;
        }
        return max;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {//注意while处理多个case
            String a = in.next();
            int b = in.nextInt();
            BigDecimal r = new BigDecimal(a);
            BigDecimal result = new BigDecimal(1);

            for(int i = 0;i<b;i++){
                result = result.multiply(r,MathContext.UNLIMITED);
            }
            System.out.println(result.toEngineeringString());
//            System.out.println(new BigDecimal(a).pow(b,MathContext.UNLIMITED).toEngineeringString());
        }
    }


    private static int findLCS(String a, String b) {
        char[] ach = a.toCharArray();
        char[] bch = b.toCharArray();
        int[][] dp = new int[ach.length][bch.length];
        dp[0][0] = ach[0] == bch[0] ? 1 : 0;
        for (int i = 1; i < ach.length; i++) {
            for (int j = 1; j < bch.length; j++) {
                if (ach[i] == bch[j]) dp[i][j] = dp[i - 1][j - 1] + 1;
                else dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        return dp[ach.length - 1][bch.length - 1];
    }

    public static void helper(int[][] src, boolean[] city, int source, int index, int count, int cityNum, Map result) {
        if (count == 0) {
            result.put("count", count);
            result.put("cityNum", cityNum);
            return;
        }
        for (int i = 0; i < src.length; i++) {
            if (i == index || src[index][i] == 0 || i == source) continue;
            if (!city[i]) {
                city[i] = true;
                cityNum++;
            }
            helper(src, city, index, i, --count, cityNum, result);
            if (result.getOrDefault("count", 0).equals(0)) return;
        }
    }

}