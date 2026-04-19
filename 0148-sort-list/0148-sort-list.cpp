/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode() : val(0), next(nullptr) {}
 *     ListNode(int x) : val(x), next(nullptr) {}
 *     ListNode(int x, ListNode *next) : val(x), next(next) {}
 * };
 */
class Solution {
public:
    ListNode* sortList(ListNode* head) {
        if(!head || !head->next) return head;
        vector<int> values;
        ListNode* curr = head;
        while(curr){
            values.push_back(curr->val);
            curr = curr->next;
        }

        sort(values.begin(), values.end());
        curr = head;
        for(int val : values){
            curr->val = val;
            curr = curr->next;
        }
        return head;
    }
};