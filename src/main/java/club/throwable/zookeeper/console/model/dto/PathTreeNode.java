package club.throwable.zookeeper.console.model.dto;

import club.throwable.zookeeper.console.common.ZookeeperConstant;
import lombok.Data;

import java.util.List;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2018/4/29 11:37
 */
@Data
public class PathTreeNode {

	/**
	 * 自定义属性
	 */
	private String parentPath;
	private String path;
	private String fullPath;

	/**
	 * bs-treeview属性
	 */
	private String text;
	private String icon;
	private String selectedIcon;
	private String color;
	private String backColor;
	private String href;
	private Boolean selectable;
	private PathTreeState state;
	private List<String> tags;
	private List<PathTreeNode> nodes;

	public static PathTreeNode createRootNode() {
		PathTreeNode pathTreeNode = new PathTreeNode();
		pathTreeNode.setParentPath(ZookeeperConstant.EMPTY_STRING);
		pathTreeNode.setPath(ZookeeperConstant.ROOT_PATH);
		pathTreeNode.setFullPath(ZookeeperConstant.ROOT_PATH);
		return pathTreeNode;
	}
}
