class Solution {
    public long maxAlternatingSum(int[] nums) {
        long[] sq = new long[nums.length];
        for(int i = 0; i<nums.length; i++){
            sq[i] = (long) nums[i] * nums[i];
        }
        Arrays.sort(sq);
        long sum = 0;
        int n = nums.length;

        for(int i = 0; i<n; i++){
            if(i < n/2){
                sum-=sq[i];
            }
            else{
                sum+=sq[i];
            }
        }
        return sum;
    }
}