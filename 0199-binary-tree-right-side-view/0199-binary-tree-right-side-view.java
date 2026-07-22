/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        int size;
        TreeNode curr;
        List<Integer> li = new ArrayList<>();

        if(root == null)
            return li;

        while (!q.isEmpty()) {
            size = q.size();

            for (int i = 0; i < size; i++) {
                curr = q.poll();
                if (i == size - 1) {
                    li.add(curr.val);
                }
                if (curr.left != null)
                    q.add(curr.left);
                if (curr.right != null)
                    q.add(curr.right);
            }
        }
        return li;
    }
}