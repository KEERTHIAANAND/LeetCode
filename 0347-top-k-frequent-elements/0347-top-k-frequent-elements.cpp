class Solution {
public:
    vector<int> topKFrequent(vector<int>& nums, int k) {
        int size = nums.size();
        unordered_map<int, int>count;
        for(int n : nums){
            count[n]++;
        }

        vector<vector<int>>buckets(size+1);
        for(auto const& [num,freq] : count){
            buckets[freq].push_back(num);
        }
        vector<int>res;
        for(int i = size; i>=0 && res.size()<k; i--){
            for(int num : buckets[i]){
                res.push_back(num);
                if(res.size() == k) break;
            }
        }
        return res;
    }
};