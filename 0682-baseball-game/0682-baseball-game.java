class Solution {
    public int calPoints(String[] operations) {
        int size = operations.length;
        int sum = 0;
        ArrayList<Integer> store = new ArrayList<>();
        for(int i = 0; i<size; i++){
            if(operations[i].equals("+")){
                store.add((store.get(store.size()-1)) + (store.get(store.size() - 2)));
            }
            else if(operations[i].equals("C")){
                if(!store.isEmpty()){
                    store.remove(store.size() - 1);
                }
            }
            else if(operations[i].equals("D")){
                store.add(2*(store.get(store.size() - 1)));
            }
            else{
                store.add(Integer.parseInt(operations[i]));
            }
        }
        for(int x : store){
            sum+=x;
        }
        return sum;
    }
}