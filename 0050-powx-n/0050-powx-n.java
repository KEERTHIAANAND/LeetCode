class Solution {
    public double myPow(double x, int n) {
        long bin = n;
        Boolean isnegative = false; 
        if(bin < 0){
            isnegative = true; 
            bin = -bin;
        }
        double ans = 1;
        double current = x;
        while(bin > 0){
            if(bin % 2 == 1){
                ans*=current;
            }
            current *= current;
            bin /= 2;
        }
        return isnegative ? (1.0/ans): ans;
    }
}