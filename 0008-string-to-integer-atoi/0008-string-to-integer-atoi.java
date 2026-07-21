class Solution {
    public int myAtoi(String s) {

        if(s == null || s.length() == 0)
            return 0;

        int i = 0;
        int sign = 1;
        long ans = 0;

        while(i<s.length() && s.charAt(i) == ' ')
            i++;
        
        if(i<s.length() && (s.charAt(i) == '+' || s.charAt(i) == '-')){
            if(s.charAt(i) == '-'){
                sign = -1;
                
            }else
                sign = 1;
            i++;
        }
        
        while(i < s.length()){
            char c = s.charAt(i);

            if(c < '0' || c > '9')
                break;
            
            ans = ans * 10 + (c - '0');
            if(sign == 1 && ans > Integer.MAX_VALUE)
                return Integer.MAX_VALUE;
            if(sign == -1 && -ans < Integer.MIN_VALUE)
                return Integer.MIN_VALUE;
            i++;
        }

        return (int) (ans * sign);
    }
}