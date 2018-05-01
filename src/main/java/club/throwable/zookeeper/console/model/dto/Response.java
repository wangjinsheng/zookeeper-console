package club.throwable.zookeeper.console.model.dto;

import lombok.Data;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2018/4/24 22:50
 */
@Data
public class Response<T> {

	private Integer code;
	private String message;
	private T data;
}
