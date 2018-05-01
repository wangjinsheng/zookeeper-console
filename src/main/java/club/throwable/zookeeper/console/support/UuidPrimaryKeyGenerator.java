package club.throwable.zookeeper.console.support;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2018/4/25 22:30
 */
@Component
public class UuidPrimaryKeyGenerator implements PrimaryKeyGenerator {

	@Override
	public String generatePrimaryKey() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
