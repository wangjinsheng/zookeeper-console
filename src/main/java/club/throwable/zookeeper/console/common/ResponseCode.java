package club.throwable.zookeeper.console.common;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2018/4/24 23:58
 */
public enum ResponseCode {

	SUCCESS(2000),

	FAIL(4000),

	ERROR(5000);

	private final Integer value;

	ResponseCode(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
}
