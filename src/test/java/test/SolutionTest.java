package test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SolutionTest {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }


    }
    public  static TreeNode constructBinaryTree(final int[] arr) {
        // 构建和原数组相同的树节点列表
        List<TreeNode> treeNodeList = arr.length > 0 ? new ArrayList<>(arr.length) : null;
        TreeNode root = null;
        // 把输入数值数组，先转化为二叉树节点列表
        for (int i = 0; i < arr.length; i++) {
            TreeNode node = null;
            if (arr[i] != -1) { // 用 -1 表示null
                node = new TreeNode(arr[i]);
            }
            treeNodeList.add(node);
            if (i == 0) {
                root = node;
            }
        }
        // 遍历一遍，根据规则左右孩子赋值就可以了
        // 注意这里 结束规则是 i * 2 + 1 < arr.length，避免空指针
        // 为什么结束规则不能是i * 2 + 2 < arr.length呢?
        // 如果i * 2 + 2 < arr.length 是结束条件
        // 那么i * 2 + 1这个符合条件的节点就被忽略掉了
        // 例如[2,7,9,-1,1,9,6,-1,-1,10] 这样的一个二叉树,最后的10就会被忽略掉
        for (int i = 0; i * 2 + 1 < arr.length; i++) {
            TreeNode node = treeNodeList.get(i);
            if (node != null) {
                // 线性存储转连式存储关键逻辑
                node.left = treeNodeList.get(2 * i + 1);
                //  再次判断下 不忽略任何一个节点
                if(i * 2 + 2 < arr.length)
                    node.right = treeNodeList.get(2 * i + 2);
            }
        }
        return root;
    }
    public static boolean isSubStructure(TreeNode A, TreeNode B) {
        if(A == null && B == null) return true;
        if((A == null && B != null)||(A != null && B == null)) return false;
        System.out.println(A.val+"**"+B.val);

        if(A.val == B.val) {
            System.out.println("123");
            boolean flag = juge(A,B);
            System.out.println(flag);
            if(flag) {
                return true;
            }
        }
        boolean left = isSubStructure(A.left,B);
        boolean right = isSubStructure(A.right,B);
        System.out.println("456");
        return left || right;
    }
    public static boolean juge(TreeNode A,TreeNode B){
        if((A == null && B == null)||(B == null)) return true;
        if((A == null && B != null)||(A.val != B.val))
            return false;

        boolean left = isSubStructure(A.left,B.left);
        boolean right = isSubStructure(A.right,B.right);

        return left&&right;
    }
    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ret = new ArrayList<List<Integer>>();
        if (root == null) {
            return ret;
        }

        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            List<Integer> level = new ArrayList<Integer>();
            int currentLevelSize = queue.size();
            for (int i = 1; i <= currentLevelSize; ++i) {
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            ret.add(level);
        }

        return ret;
    }
    public static TreeNode mirrorTree(TreeNode root) {
        if(root == null) return null;
        TreeNode node = root.left;
        root.left = mirrorTree(root.right);
        root.right = mirrorTree(node);
        return root;
    }

    public static void main(String[] args) {
        TreeNode A = constructBinaryTree(new int[]{4,2,7,1,3,6,9});
        TreeNode treeNode = mirrorTree(A);
        List<List<Integer>> arr = levelOrder(treeNode);
        arr.forEach((temp)->{
            for (Integer i : temp) {
                System.out.println(i);
            }
        });
    }

}
