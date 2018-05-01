package club.throwable.zookeeper.console.model.dto;

import lombok.Data;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2018/4/26 22:21
 */
@Data
public class PaginationResponse<T> {

	private Integer total;
	private T rows;
}
