class Solution {
    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stk = new Stack<>();
        for (int i = 0; i < asteroids.length; i++) {
            if (asteroids[i] > 0) {
                stk.push(asteroids[i]);
                continue;
            }
            boolean destroyed = false;
            while (!stk.isEmpty() && stk.peek() > 0 && stk.peek() < Math.abs(asteroids[i])) {
                stk.pop();
            }

            if(!stk.isEmpty() && stk.peek() > 0 && stk.peek() == Math.abs(asteroids[i])){
                stk.pop();
                destroyed = true;
            }
            else if(!stk.isEmpty() && stk.peek() > 0 && stk.peek() > Math.abs(asteroids[i])){
                destroyed = true;
            }
            if(!destroyed){
                stk.push(asteroids[i]);
            }
        }
        int[] res = new int[stk.size()];
        for(int i = stk.size() - 1; i>= 0; i--){
            res[i] = stk.pop();
        }
        return res;
    }
}