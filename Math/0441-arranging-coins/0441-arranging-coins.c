int arrangeCoins(int n) {
    int r=0;
    while(r<n){
        r=r+1;
        n-=r;
    }
    return r;
}