package club.throwable.zookeeper.console.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2018/4/24 22:29
 */
@Data
@ToString
@NoArgsConstructor
public class ZookeeperInfo {

	private String id;
	private String description;
	private String connectionString;
	private Integer sessionTimeout;
}
