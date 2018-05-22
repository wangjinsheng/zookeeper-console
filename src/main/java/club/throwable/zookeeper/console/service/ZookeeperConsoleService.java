package club.throwable.zookeeper.console.service;

import club.throwable.zookeeper.console.common.ResponseCode;
import club.throwable.zookeeper.console.common.ZookeeperConstant;
import club.throwable.zookeeper.console.dao.ZookeeperInfoDao;
import club.throwable.zookeeper.console.model.dto.*;
import club.throwable.zookeeper.console.model.entity.ZookeeperInfo;
import club.throwable.zookeeper.console.support.CuratorFrameworkManager;
import club.throwable.zookeeper.console.support.PrimaryKeyGenerator;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2018/4/24 22:43
 */
@Slf4j
@Service
public class ZookeeperConsoleService {

	@Autowired
	private CuratorFrameworkManager curatorFrameworkManager;

	@Autowired
	private ZookeeperInfoDao zookeeperInfoDao;

	@Autowired
	private PrimaryKeyGenerator primaryKeyGenerator;

	private static final RetryPolicy RETRY_POLICY = new RetryOneTime(1);

	public void registerAllLocalCuratorFramework() throws Exception {
		log.info("Begin to register all zookeeper...");
		List<ZookeeperInfo> zookeeperInfoList = zookeeperInfoDao.findAll();
		if (null != zookeeperInfoList && !zookeeperInfoList.isEmpty()) {
			for (ZookeeperInfo zookeeperInfo : zookeeperInfoList) {
				try {
					curatorFrameworkManager.registerCuratorFramework(zookeeperInfo.getId(),
						createAndStartCuratorFramework(zookeeperInfo.getConnectionString(), zookeeperInfo.getSessionTimeout()));
				} catch (Exception e) {
					log.error("Load curator failed for zookeeperInfo = {}", zookeeperInfo, e);
				}
			}
		}
	}

	public PaginationResponse<List<ZookeeperInfo>> queryZookeeperInfoByPagination(Integer pageNumber, Integer pageSize) {
		PaginationResponse<List<ZookeeperInfo>> response = new PaginationResponse<>();
		if (pageNumber <= ZookeeperConstant.DEFAULT_PAGE_NUMBER_INTEGER) {
			pageNumber = ZookeeperConstant.DEFAULT_PAGE_NUMBER_INTEGER;
		}
		response.setRows(zookeeperInfoDao.queryZookeeperInfoByPagination((pageNumber - 1) * pageSize, pageSize));
		response.setTotal(zookeeperInfoDao.countTotal());
		return response;
	}

	public Response<ZookeeperInfo> findById(String id) {
		Response<ZookeeperInfo> response = new Response<>();
		response.setCode(ResponseCode.SUCCESS.getValue());
		response.setData(zookeeperInfoDao.findById(id));
		return response;
	}

	public boolean saveZookeeperInfo(ZookeeperInfo zookeeperInfo) throws Exception {
		zookeeperInfo.setId(primaryKeyGenerator.generatePrimaryKey());
		Integer saveCount = zookeeperInfoDao.saveZookeeperInfo(zookeeperInfo);
		if (ZookeeperConstant.ONE.equals(saveCount)) {
			curatorFrameworkManager.registerCuratorFramework(zookeeperInfo.getId(),
				createAndStartCuratorFramework(zookeeperInfo.getConnectionString(), zookeeperInfo.getSessionTimeout()));
		}
		return Boolean.TRUE;
	}

	public boolean updateZookeeperInfo(ZookeeperInfo zookeeperInfo) throws Exception {
		Assert.notNull(zookeeperInfo.getId(), "Id of zookeeperInfo to update must not be null");
		log.info("Update zookeeper info,content = {}", zookeeperInfo);
		Integer updateCount = zookeeperInfoDao.updateZookeeperInfo(zookeeperInfo);
		if (ZookeeperConstant.ONE.equals(updateCount)) {
			curatorFrameworkManager.removeCuratorFramework(zookeeperInfo.getId());
			curatorFrameworkManager.registerCuratorFramework(zookeeperInfo.getId(),
				createAndStartCuratorFramework(zookeeperInfo.getConnectionString(), zookeeperInfo.getSessionTimeout()));
		}
		return Boolean.TRUE;
	}

	public boolean deleteZookeeperInfo(String id) {
		Assert.notNull(id, "Id of zookeeperInfo to delete must not be null");
		log.info("Delete zookeeper info,id = {}", id);
		Integer saveCount = zookeeperInfoDao.deleteZookeeperInfo(id);
		if (ZookeeperConstant.ONE.equals(saveCount)) {
			curatorFrameworkManager.removeCuratorFramework(id);
		}
		return Boolean.FALSE;
	}

	public List<PathTreeNode> buildPathTreeNodeListByPath(String id, String path) throws Exception {
		if (!path.startsWith(ZookeeperConstant.ROOT_PATH)) {
			path = String.format("%s%s", ZookeeperConstant.ROOT_PATH, path);
		}
		CuratorFramework curator = getExistCurator(id);
		List<String> children = curator.getChildren().forPath(path);
		List<PathTreeNode> pathTreeNodeList = new ArrayList<>();
		if (!children.isEmpty()) {
			for (String child : children) {
				PathTreeNode node = new PathTreeNode();
				node.setPath(child);
				node.setHref(String.format("%s%s", "#", node.getPath()));
				node.setText(node.getPath());
				node.setFullPath(ZookeeperConstant.ROOT_PATH.equals(path) ? String.format("%s%s", path, child)
					: String.format("%s%s%s", path, ZookeeperConstant.ROOT_PATH, child));
				node.setParentPath(path);

				List<String> childrenNodes = curator.getChildren().forPath(node.getFullPath());
				if (!childrenNodes.isEmpty()){
					node.setNodes(new ArrayList<>());
				}
				pathTreeNodeList.add(node);
			}
		}
		return pathTreeNodeList;
	}

	public boolean deleteNode(String id, String path) throws Exception {
		Assert.notNull(id, "Id to delete Node to delete must not be null");
		CuratorFramework curator = getExistCurator(id);
		if (checkNodeExist(curator, path)) {
			curator.delete().deletingChildrenIfNeeded().forPath(path);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public boolean updateNodeData(String id, String path, String data) throws Exception {
		Assert.notNull(id, "Id to save Node to delete must not be null");
		Assert.notNull(path, "Path to save Node to delete must not be null");
		CuratorFramework curator = getExistCurator(id);
		if (checkNodeExist(curator, path)) {
			return null != curator.setData().forPath(path, data.getBytes(ZookeeperConstant.CHARSET));
		} else {
			if (StringUtils.hasLength(data)) {
				curator.create().creatingParentsIfNeeded()
					.withMode(CreateMode.PERSISTENT)
					.withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
					.forPath(path, data.getBytes(ZookeeperConstant.CHARSET));
			} else {
				curator.create().creatingParentsIfNeeded()
					.withMode(CreateMode.PERSISTENT)
					.withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
					.forPath(path);
			}
			return Boolean.TRUE;
		}
	}

	public NodeInfo getNodeInfo(String id, String path) throws Exception {
		CuratorFramework curator = getExistCurator(id);
		NodeInfo nodeInfo = new NodeInfo();
		nodeInfo.setId(id);
		nodeInfo.setPath(path);
		nodeInfo.setStatMetadata(collectStatMetadata(curator, path));
		AclInfo aclInfo = new AclInfo();
		StringBuilder ids = new StringBuilder();
		StringBuilder schemas = new StringBuilder();
		StringBuilder perms = new StringBuilder();
		List<ZookeeperAclMetadata> aclMetadata = collectAclMetadata(curator, path);
		for (ZookeeperAclMetadata metadata : aclMetadata) {
			ids.append(metadata.getId()).append(";");
			schemas.append(metadata.getScheme()).append(";");
			perms.append(metadata.getPerms()).append(";");
		}
		if (ids.length() > 0) {
			aclInfo.setId(ids.substring(0, ids.lastIndexOf(";")));
		}
		if (perms.length() > 0) {
			aclInfo.setPerms(perms.substring(0, perms.lastIndexOf(";")));
		}
		if (schemas.length() > 0) {
			aclInfo.setScheme(schemas.substring(0, schemas.lastIndexOf(";")));
		}
		nodeInfo.setAclMetadata(aclInfo);
		nodeInfo.setData(getNodeData(curator, path));
		return nodeInfo;
	}

	private String getNodeData(CuratorFramework curator, String path) throws Exception {
		byte[] bytes = curator.getData().forPath(path);
		if (bytes == null){
			return "";
		}
		return new String(bytes, Charset.forName(ZookeeperConstant.CHARSET));
	}

	private ZookeeperStatMetadata collectStatMetadata(CuratorFramework curator, String path) throws Exception {
		ZookeeperStatMetadata metadata = new ZookeeperStatMetadata();
		Stat stat = curator.checkExists().forPath(path);
		if (null != stat) {
			metadata.setCzxid(stat.getCzxid());
			metadata.setCtime(stat.getCtime());
			metadata.setMzxid(stat.getMzxid());
			metadata.setMtime(stat.getMtime());
			metadata.setVersion(stat.getVersion());
			metadata.setCversion(stat.getCversion());
			metadata.setAversion(stat.getAversion());
			metadata.setEphemeralOwner(stat.getEphemeralOwner());
			metadata.setDataLength(stat.getDataLength());
			metadata.setNumChildren(stat.getNumChildren());
			metadata.setPzxid(stat.getPzxid());
		}
		return metadata;
	}

	private List<ZookeeperAclMetadata> collectAclMetadata(CuratorFramework curator, String path) throws Exception {
		List<ZookeeperAclMetadata> aclMetadata = Lists.newArrayList();
		if (checkNodeExist(curator, path)) {
			List<ACL> acls = curator.getACL().forPath(path);
			if (null != acls && !acls.isEmpty()) {
				for (ACL acl : acls) {
					ZookeeperAclMetadata data = new ZookeeperAclMetadata();
					aclMetadata.add(data);
					data.setId(acl.getId().getId());
					data.setScheme(acl.getId().getScheme());
					StringBuilder permsBuilder = new StringBuilder();
					int perms = acl.getPerms();
					if ((perms & ZooDefs.Perms.READ) == ZooDefs.Perms.READ) {
						permsBuilder.append("Read, ");
					}
					if ((perms & ZooDefs.Perms.WRITE) == ZooDefs.Perms.WRITE) {
						permsBuilder.append("Write, ");
					}
					if ((perms & ZooDefs.Perms.CREATE) == ZooDefs.Perms.CREATE) {
						permsBuilder.append("Create, ");
					}
					if ((perms & ZooDefs.Perms.DELETE) == ZooDefs.Perms.DELETE) {
						permsBuilder.append("Delete, ");
					}
					if ((perms & ZooDefs.Perms.ADMIN) == ZooDefs.Perms.ADMIN) {
						permsBuilder.append("Admin, ");
					}
					if (permsBuilder.length() > 0) {
						data.setPerms(permsBuilder.substring(0, permsBuilder.lastIndexOf(",")));
					}
				}
			}
		}
		return aclMetadata;
	}

	private CuratorFramework createAndStartCuratorFramework(String connectionString, Integer sessionTimeout) throws Exception {
		CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
			.connectString(connectionString)
			.sessionTimeoutMs(sessionTimeout)
			.retryPolicy(RETRY_POLICY)
			.build();
		curatorFramework.start();
		return curatorFramework;
	}

	private boolean checkNodeExist(CuratorFramework curator, String path) throws Exception {
		return null != curator.checkExists().forPath(path);
	}

	private CuratorFramework getExistCurator(String id) {
		CuratorFramework curatorFramework = curatorFrameworkManager.getCuratorFramework(id);
		Assert.notNull(curatorFramework, "CuratorFramework must not be null for id = " + id);
		return curatorFramework;
	}
}
