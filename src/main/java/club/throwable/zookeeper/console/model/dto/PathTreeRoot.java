package club.throwable.zookeeper.console.model.dto;

import club.throwable.zookeeper.console.common.ZookeeperConstant;

import java.util.ArrayList;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2018/4/29 11:45
 */
public class PathTreeRoot extends ArrayList<PathTreeNode> {

	public PathTreeRoot() {
		PathTreeNode pathTreeNode = new PathTreeNode();
		pathTreeNode.setParentPath(ZookeeperConstant.EMPTY_STRING);
		pathTreeNode.setPath(ZookeeperConstant.ROOT_PATH);
		pathTreeNode.setFullPath(ZookeeperConstant.ROOT_PATH);
		pathTreeNode.setText(ZookeeperConstant.ROOT_PATH);
		this.add(pathTreeNode);
	}

	public void removeRootNode() {
		this.remove(0);
	}

	public PathTreeNode getRootNode(){
		return this.get(0);
	}
}
