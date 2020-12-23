package hrdRewrite.controller;

import hrdRewrite.modle.Chessboard;
import hrdRewrite.modle.Chessman;
import hrdRewrite.modle.ChessmanStep;
import hrdRewrite.modle.TreeNode;

import java.util.*;

public class CreateTree {
    private final Set<Chessboard> chessboardSet;
    private final Chessboard root;
    StringBuffer message=new StringBuffer();
    private Stack<Chessboard> stack = new Stack<>();
    CreateTree(long state){
        chessboardSet =new HashSet<>();
        root = new Chessboard(new EnumMap<>(Chessman.class), state);
    }

    public void printAllStep(){
        System.out.println(message.toString());
        for (Chessboard temp : stack){
            System.out.println(temp);
        }
    }
    public void calculateResult(){
        TreeNode root = new TreeNode(this.root);
        ArrayList<TreeNode> nodes = new ArrayList<>();
        nodes.add(root);
        TreeNode endNode = createlayerTree(nodes,1);
        for (TreeNode tempNode = endNode;tempNode != null;tempNode=tempNode.getParent()){
            stack.add(tempNode.getChessboard());
        }
    }
    private TreeNode createlayerTree(ArrayList<TreeNode> nodes,int level){
        ArrayList<TreeNode> newNodes = new ArrayList<>();
        for (TreeNode node : nodes){
            for (ChessmanStep step : node.getSteps()){
                TreeNode newNode = new TreeNode(node,step);
                if (chessboardSet.add(newNode.getChessboard())){
                    newNodes.add(newNode);
                }
                if (newNode.isEndNode()){
                    return newNode;
                }
            }
        }
        message.append("level: ");
        message.append(level);
        message.append("newNodes: ");
        message.append(newNodes.size());
        message.append("\n");
        return createlayerTree(newNodes,level+1);
    }

}
