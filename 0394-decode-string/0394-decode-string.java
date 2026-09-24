class Solution {
    public String decodeString(String s) {
        Stack<Integer> numStk = new Stack<>();
        Stack<StringBuilder> strStk = new Stack<>();
        int num = 0;
        StringBuilder ans = new StringBuilder();

        for(char c : s.toCharArray()){
            if(Character.isDigit(c)){
                num = (num * 10) + (c - '0');
            }else if(c == '['){
                numStk.push(num);
                num = 0;
                strStk.push(ans);
                ans = new StringBuilder();
            }else if(c == ']'){
                String str = ans.toString();
                ans = new StringBuilder(str.repeat(numStk.pop()));
                ans = strStk.pop().append(ans);
            }else{
                ans.append(c);
            }
        }
        return ans.toString();
    }
}