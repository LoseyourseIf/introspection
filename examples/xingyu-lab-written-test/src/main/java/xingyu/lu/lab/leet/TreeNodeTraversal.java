package xingyu.lu.lab.leet;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Description: TreeNodeTraversal
 * @Version: 1.0
 * @Author: XingYu.Lu
 * @CreateTime: 2024-07-17  08:54
 */
public class TreeNodeTraversal {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // 前序遍历：根节点 -> 左子树 -> 右子树
    public static void preOrderTraversal(TreeNode node) {
        if (node == null) return;
        System.out.print(node.val + " ");   // 访问根节点
        preOrderTraversal(node.left);       // 遍历左子树
        preOrderTraversal(node.right);      // 遍历右子树
    }

    // 中序遍历：左子树 -> 根节点 -> 右子树
    public static void inOrderTraversal(TreeNode node) {
        if (node == null) return;
        inOrderTraversal(node.left);       // 遍历左子树
        System.out.print(node.val + " ");  // 访问根节点
        inOrderTraversal(node.right);      // 遍历右子树
    }

    // 后序遍历：左子树 -> 右子树 -> 根节点
    public static void postOrderTraversal(TreeNode node) {
        if (node == null) return;
        postOrderTraversal(node.left);      // 遍历左子树
        postOrderTraversal(node.right);     // 遍历右子树
        System.out.print(node.val + " ");   // 访问根节点
    }

    // 层级遍历
    public static void levelOrderTraversal(TreeNode root) {
        if (root == null) return;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            System.out.print(current.val + " ");
            if (current.left != null) {
                queue.offer(current.left);
            }
            if (current.right != null) {
                queue.offer(current.right);
            }
        }
    }

    // 判断二叉树是否对称
    public static boolean isSymmetric(TreeNode root) {
        if (null == root) {
            return true;
        }
        return isMirror(root.left, root.right);
    }

    private static boolean isMirror(TreeNode node1, TreeNode node2) {
        if (null == node1 && null == node2) {
            return true;
        }
        if (null == node1 || null == node2) {
            return false;
        }
        return node1.val == node2.val &&
                isMirror(node1.left, node2.right) &&
                isMirror(node1.right, node2.left);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right = new TreeNode(3);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);

        /*
         *             1
         *           /   \
         *         2       3
         *       /  \     /  \
         *     4     5   6     7
         */

        System.out.println("——————preOrderTraversal 根 左 右——————");
        preOrderTraversal(root);
        System.out.println();
        System.out.println("——————inOrderTraversal 左 根 右——————");
        inOrderTraversal(root);
        System.out.println();
        System.out.println("——————postOrderTraversal 左 右 根——————");
        postOrderTraversal(root);
        System.out.println();
        System.out.println("——————levelOrder 层级遍历——————");
        levelOrderTraversal(root);
        System.out.println();
        System.out.println(isSymmetric(root));

        TreeNode symmetricTree = new TreeNode(1);
        symmetricTree.left = new TreeNode(2);
        symmetricTree.left.left = new TreeNode(4);
        symmetricTree.left.right = new TreeNode(5);
        symmetricTree.right = new TreeNode(2);
        symmetricTree.right.left = new TreeNode(5);
        symmetricTree.right.right = new TreeNode(4);

        /*
         *             1
         *           /   \
         *         2       2
         *       /  \     /  \
         *     4     5   5     4
         */

        System.out.println(isSymmetric(symmetricTree));

    }
}
