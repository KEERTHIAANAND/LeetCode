class Solution {
    public boolean judgeCircle(String moves) {
        int U = 1;
        int D = -1;
        int L = 1;
        int R = -1;
        int x = 0;
        int y = 0;
        for(char c : moves.toCharArray()){
            if(c == 'U')
                y+=U;
            else if(c == 'D')
                y+=D;
            else if(c == 'L')
                x+=L;
            else if(c == 'R')
                x+=R;
            else
                continue;
        }
        if(x == 0 && y == 0)
            return true;
        else
            return false;
    }
}