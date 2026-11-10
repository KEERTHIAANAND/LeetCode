class Solution {
    public int climbStairs(int n, int[] costs) {
        int[] dp = new int[n+1];
        dp[0] = 0;
        for(int j = 1; j<=n; j++){
            int min = Integer.MAX_VALUE;
            for(int k = 1; k <= 3; k++){
                int i = j - k;
                if(i >= 0){
                    int curr = dp[i] + costs[j-1] + (j - i) * (j - i);
                    min = Math.min(min, curr);
                }
            }
            dp[j] = min;
        }
        return dp[n];
    }
}