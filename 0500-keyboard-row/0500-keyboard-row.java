class Solution {
    public String[] findWords(String[] words) {
        List<String> result = new ArrayList<>();
        String fr = "[qwertyuiop]+";
        String sr = "[asdfghjkl]+";
        String tr = "[zxcvbnm]+";

        for(String word : words){
            String lower = word.toLowerCase();
            if(lower.matches(fr) || lower.matches(sr) || lower.matches(tr))
                result.add(word);
        }
        return result.toArray(new String[0]);
    }
}