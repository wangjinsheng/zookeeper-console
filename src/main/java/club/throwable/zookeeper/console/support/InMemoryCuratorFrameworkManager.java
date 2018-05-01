package club.throwable.zookeeper.console.support;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2018/4/24 22:33
 */
@Slf4j
@Component
public class InMemoryCuratorFrameworkManager implements CuratorFrameworkManager, DisposableBean {

	private static final Map<String, CuratorFramework> CURATOR_FRAMEWORK_MAP = Maps.newConcurrentMap();

	@Override
	public void registerCuratorFramework(String id, CuratorFramework curatorFramework) {
		CURATOR_FRAMEWORK_MAP.put(id, curatorFramework);
	}

	@Override
	public CuratorFramework getCuratorFramework(String id) {
		return CURATOR_FRAMEWORK_MAP.get(id);
	}

	@Override
	public void removeCuratorFramework(String id) {
		CuratorFramework curatorFramework = CURATOR_FRAMEWORK_MAP.get(id);
		if (null != curatorFramework) {
			curatorFramework.close();
		}
		CURATOR_FRAMEWORK_MAP.remove(id);
	}

	@Override
	public void destroy() {
		CURATOR_FRAMEWORK_MAP.values().forEach(
				each -> {
					try {
						each.close();
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
				});
	}
}
