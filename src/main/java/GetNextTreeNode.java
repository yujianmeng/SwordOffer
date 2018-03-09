/**
 * Created by yujian on 2018/3/9.
 * 给定一个二叉树和其中的一个结点，请找出中序遍历顺序的下一个结点并且返回。
 * 注意，树中的结点不仅包含左右子结点，同时包含指向父结点的指针。
 */
public class GetNextTreeNode {

    public TreeNode getNext(TreeNode pNode){
        if (pNode == null)
            return null;
        if (pNode.rightNode!=null){
            pNode = pNode.rightNode;
            while (pNode.leftNode!=null)
                pNode = pNode.leftNode;
            return pNode;
        }
        else {
            TreeNode parent = pNode.parent;
            while (parent!=null){
                if (parent.leftNode==pNode)
                    return parent;
                else {
                    pNode = parent;
                    parent = parent.parent;
                }
            }
        }
        return null;
    }
}
