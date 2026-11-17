bool squareIsWhite(char* coordinates) {
    int row=coordinates[0];
    int col=coordinates[1];
    return (row + col) % 2 == 1;
}