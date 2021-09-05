package com.laoxin.core.utils;


import com.laoxin.core.vo.Tree;

import java.util.ArrayList;
import java.util.List;

/***
 * 将实现了Tree接口的实体树结构化
 * @author jinquanbao
 */
public class TreeUtil {



    /***
     * 将实现了Tree接口的类树结构化
     * @author jinquanbao
     */
    public static  <T extends Tree> List<? extends Tree> tree(List<? extends Tree> trees){
        List<Tree> result = new ArrayList<>();
        if(Util.isEmpty(trees)){
            return result;
        }
        for(Tree tree : trees){
                // 获取一级节点
            if (tree.getParentId() == 0) {
                result.add(tree);
            }
            result.sort((o1, o2) -> o1.getSort()- o2.getSort());
            // 为一级节点设置子节点，getChild是递归调用的
            for (Tree parent : result) {
                parent.setChilds(getChilds(trees, parent.getId()));
            }
        }
        return result;
    }



    /***
     * 递归获取字节点
     * @author jinquanbao
     */
    public static List<Tree> getChilds(List<? extends Tree> trees,long id){
        // 子节点
        List<Tree> childs = new ArrayList<>();
        for (Tree tree : trees) {
            // 遍历所有节点，将父节点id与传过来的id比较
            if ((tree.getParentId() == id)) {
                childs.add(tree);
            }
        }
        // 把子节点的子节点再循环一遍
        for (Tree tree : childs) {
            // 递归
            tree.setChilds(getChilds(trees, tree.getId()));
        }
        //排序
        childs.sort((o1, o2) -> o1.getSort()- o2.getSort());
        // 递归退出条件
        if (childs.size() == 0) {
            return null;
        }
        return childs;
    }
}
