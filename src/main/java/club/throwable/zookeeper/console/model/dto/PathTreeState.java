package club.throwable.zookeeper.console.model.dto;

import lombok.Data;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2018/4/29 11:38
 */
@Data
public class PathTreeState {

	private Boolean checked;
	private Boolean disabled;
	private Boolean expanded;
	private Boolean selected;
}
