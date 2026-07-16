class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        if(strs == null || strs.length == 0)
            return new ArrayList<>();
        Map<String, List<String>> res = new HashMap<>();
        for(String s : strs){
            char[] charArr = s.toCharArray();
            Arrays.sort(charArr);

            String sortstr = new String(charArr);
            res.computeIfAbsent(sortstr, k-> new ArrayList<>()).add(s);        
        }
        return new ArrayList<>(res.values());
    }
}