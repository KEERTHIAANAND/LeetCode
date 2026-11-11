class Solution {
    public int maximum69Number (int num) {
        char[] digi = String.valueOf(num).toCharArray();
        for(int i = 0; i< digi.length; i++){
            if(digi[i] == '6'){
                digi[i] = '9';
                break;
            }
        }
        return Integer.parseInt(new String(digi));
    }
}