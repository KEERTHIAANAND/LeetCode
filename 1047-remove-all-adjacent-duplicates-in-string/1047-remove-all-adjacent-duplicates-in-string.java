class Solution {
    public String removeDuplicates(String s) {
        Stack<Character> res = new Stack<>();
        for(int i = 0; i<s.length(); i++){
            if(!res.isEmpty() && res.peek() == s.charAt(i)){
                res.pop();
            }else{
                res.push(s.charAt(i));
            }
        }
        StringBuilder fstr = new StringBuilder();
        for(char c : res){
            fstr.append(c);
        }
        return fstr.toString();
    }
}