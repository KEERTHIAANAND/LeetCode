class Solution {
public:
    int maxArea(vector<int>& height) {
        int size = height.size();
        int area = 0;
        int left = 0;
        int right = size - 1;
        for(int i = 0; i<size; i++){
            int wid = abs(right - left);
            int ht = min(height[left], height[right]);
            int sol = wid * ht;
            if(area<sol)
                area = sol;
            if(height[left]<height[right])
                left++;
            else
                right--;
        }
        return area;
    }
};