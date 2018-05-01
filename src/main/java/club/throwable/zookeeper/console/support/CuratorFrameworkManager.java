package club.throwable.zookeeper.console.support;

import org.apache.curator.framework.CuratorFramework;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2018/4/24 22:25
 */
public interface CuratorFrameworkManager {

	void registerCuratorFramework(String id, CuratorFramework curatorFramework);

	CuratorFramework getCuratorFramework(String id);

	void removeCuratorFramework(String id);

	void destroy();
}
