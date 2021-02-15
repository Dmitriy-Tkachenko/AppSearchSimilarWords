package com.university.project;

public class Algorithms {
    public int distLevenshtein(String s1, String s2) {
        if (s1.equals(s2))
        return 0;

        if (s1.length() == 0)
            return s2.length();

        if (s2.length() == 0)
            return s1.length();

        int[][]dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            dp[i][0] = i;
        }

        for (int i = 0; i <= s2.length(); i++) {
            dp[0][i] = i;
        }

        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                    int d1 = dp[i - 1][j] + 1;
                    int d2 = dp[i][j - 1] + 1;
                    int d3 = dp[i - 1][j - 1];
                    if (s1.charAt(i - 1) != s2.charAt(j - 1)) {
                        d3 += 1;
                    }
                    dp[i][j] = Math.min(Math.min(d1, d2), d3 );
            }
        }
        return dp[s1.length()][s2.length()];
    }
}
