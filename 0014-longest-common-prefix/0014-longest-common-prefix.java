class Solution {
    public String longestCommonPrefix(String[] strs) {
        if(strs == null || strs.length == 0)
            return "";
        String ref = strs[0];
        for(int i = 0; i<ref.length(); i++){
            char c = ref.charAt(i);
            for(int j = 1; j<strs.length; j++){
                if(i == strs[j].length() || strs[j].charAt(i) != c)
                    return ref.substring(0,i);
            }
        }
        return ref;
    }
}