/**
 * Created by yujian on 2018/3/8.
 * 输入某二叉树的前序遍历和中序遍历的结果，
 * 请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
 */
public class CreateBinaryTree {
    public static void main(String[] args) {
        int[] pre = {1,2,4,7,3,5,6,8};
        int[] in = {4,7,2,1,5,3,8,6};
        TreeNode root = createBinaryTree(pre,0,pre.length-1,in,0,in.length-1);
        System.out.println(root.value);
    }

    public static TreeNode createBinaryTree(int[] pre, int preSta, int preEnd, int[] in, int inSta, int inEnd){
        if (preSta>preEnd|| inSta>inEnd){
            return null;
        }
        TreeNode root = new TreeNode(pre[preSta]);
        if (preSta!=preEnd){
            int index = inSta;
            while (index<inEnd&&in[index]!=pre[preSta]){
                index++;
            }
            int leftTreeLen = index-inSta;
            root.leftNode = createBinaryTree(pre, preSta+1, preSta+leftTreeLen, in, inSta, index+leftTreeLen-1);
            root.rightNode = createBinaryTree(pre, preSta+1+leftTreeLen, preEnd, in, index+1, inEnd);
        }
        return root;
    }
}

