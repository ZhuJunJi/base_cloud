package com.zhujunji.common.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TreeNode<I, E> implements Serializable {
    /**
     * id
     */
    private I id;
    /**
     * parentId
     */
    private I parentId;

    private E element;
    /**
     * childrenList
     */
    private List<TreeNode<I, E>> childrenList;

    public TreeNode(){}

    public TreeNode(I id, I parentId, E element) {
        this.id = id;
        this.parentId = parentId;
        this.element = element;
    }

    public TreeNode(I id, I parentId, E element, List<TreeNode<I, E>> childrenList) {
        this(id,parentId,element);
        this.childrenList = childrenList;
    }
}
