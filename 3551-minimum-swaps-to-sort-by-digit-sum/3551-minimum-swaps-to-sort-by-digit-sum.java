class Solution {
    public int minSwaps(int[] nums) {
        int n = nums.length;
        int[][] arr = new int[n][2];
        for(int i = 0; i<n; i++){
            int sum = 0, num = nums[i];
            while(num>0){
                sum+=num%10;
                num/=10;
            }
            arr[i][0] = nums[i];
            arr[i][1] = sum;
        }
        Arrays.sort(arr,(a,b) -> {
            if(a[1] != b[1]){
                return a[1] - b[1];
            }
            return a[0] - b[0];
        });
        Map<Integer, Integer>map = new HashMap<>();
        for(int i = 0; i<n; i++){
            map.put(nums[i], i);
        }
        boolean[] visited = new boolean[n];
        int swaps = 0;
        for(int i = 0; i<n; i++){
            if(visited[i] || map.get(arr[i][0]) == i)
                continue;
            int j = i;
            int cycle = 0;
            while(!visited[j]){
                visited[j] = true;
                j = map.get(arr[j][0]);
                cycle++;
            }
            swaps+=cycle-1;
        }
        return swaps;
    }
}