class Solution {
    public String convert(String s, int numRows) {
        if(numRows == 1 || numRows >= s.length())
            return s;
        
        StringBuilder ans = new StringBuilder(); //final ans

        StringBuilder[] rows = new StringBuilder[numRows];

        for(int i = 0; i<numRows; i++){
            rows[i] = new StringBuilder();
        }
        int row = 0;
        boolean down = false;

        for(char c : s.toCharArray()){
            rows[row].append(c);
            if(row == 0 || row == numRows-1)
                down = !down;
            if(down)
                row++;
            else
                row--;
        }
        for(int i = 0; i<numRows; i++){
            ans.append(rows[i]);
        }
        return ans.toString();
    }
}