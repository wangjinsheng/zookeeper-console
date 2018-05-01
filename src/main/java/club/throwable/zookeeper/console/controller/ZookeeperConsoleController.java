package club.throwable.zookeeper.console.controller;

import club.throwable.zookeeper.console.common.ResponseCode;
import club.throwable.zookeeper.console.common.ZookeeperConstant;
import club.throwable.zookeeper.console.model.dto.NodeInfo;
import club.throwable.zookeeper.console.model.dto.PaginationResponse;
import club.throwable.zookeeper.console.model.dto.PathTreeRoot;
import club.throwable.zookeeper.console.model.dto.Response;
import club.throwable.zookeeper.console.model.entity.ZookeeperInfo;
import club.throwable.zookeeper.console.service.ZookeeperConsoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2018/4/24 22:28
 */
@Slf4j
@Controller
public class ZookeeperConsoleController {

	@Autowired
	private ZookeeperConsoleService zookeeperConsoleService;

	@GetMapping(value = {"/", "/index"})
	public String index() {
		return "index";
	}

	@GetMapping(value = "/zookeeper", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationResponse<List<ZookeeperInfo>> queryZookeeperInfo(
			@RequestParam(name = "page", required = false, defaultValue = ZookeeperConstant.DEFAULT_PAGE_NUMBER) Integer pageNumber,
			@RequestParam(name = "rows", required = false, defaultValue = ZookeeperConstant.DEFAULT_PAGE_SIZE) Integer pageSize) {
		return zookeeperConsoleService.queryZookeeperInfoByPagination(pageNumber, pageSize);
	}

	@DeleteMapping(value = "/zookeeper/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Response<Boolean> deleteZookeeper(@PathVariable(name = "id") String id) {
		Response<Boolean> response = new Response<>();
		response.setCode(ResponseCode.SUCCESS.getValue());
		response.setData(zookeeperConsoleService.deleteZookeeperInfo(id));
		return response;
	}

	@PostMapping(value = "/zookeeper", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Response<Boolean> postZookeeper(
			@RequestParam(name = "id", required = false, defaultValue = ZookeeperConstant.EMPTY_STRING) String id,
			@RequestParam(name = "description") String description,
			@RequestParam(name = "connectionString") String connectionString,
			@RequestParam(name = "sessionTimeout") Integer sessionTimeout) throws Exception {
		Response<Boolean> response = new Response<>();
		ZookeeperInfo zookeeperInfo = new ZookeeperInfo();
		zookeeperInfo.setId(id);
		zookeeperInfo.setDescription(description);
		zookeeperInfo.setConnectionString(connectionString);
		zookeeperInfo.setSessionTimeout(sessionTimeout);
		if (ZookeeperConstant.EMPTY_STRING.equals(zookeeperInfo.getId())) {
			response.setData(zookeeperConsoleService.saveZookeeperInfo(zookeeperInfo));
		} else {
			response.setData(zookeeperConsoleService.updateZookeeperInfo(zookeeperInfo));
		}
		response.setCode(ResponseCode.SUCCESS.getValue());
		return response;
	}

	@GetMapping(value = "/tree", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PathTreeRoot queryPathTree(@RequestParam(name = "id") String id,
									  @RequestParam(name = "path", required = false,
											  defaultValue = ZookeeperConstant.EMPTY_STRING) String path) throws Exception {
		PathTreeRoot root = new PathTreeRoot();
		if (ZookeeperConstant.ROOT_PATH.equals(path) || ZookeeperConstant.EMPTY_STRING.equals(path)) {
			root.getRootNode().setNodes(zookeeperConsoleService.buildPathTreeNodeListByPath(id, path));
		} else {
			root.removeRootNode();
			root.addAll(zookeeperConsoleService.buildPathTreeNodeListByPath(id, path));
		}
		return root;
	}

	@GetMapping(value = "/node", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Response<NodeInfo> queryNodeInfo(@RequestParam(name = "id") String id,
											@RequestParam(name = "path") String path) throws Exception {
		Response<NodeInfo> response = new Response<>();
		response.setCode(ResponseCode.SUCCESS.getValue());
		response.setData(zookeeperConsoleService.getNodeInfo(id, path));
		return response;
	}

	@PostMapping(value = "/node", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Response<Boolean> updateNodeData(@RequestParam(name = "id") String id,
											@RequestParam(name = "path") String path,
											@RequestParam(name = "data", required = false) String data) throws Exception {
		Response<Boolean> response = new Response<>();
		response.setCode(ResponseCode.SUCCESS.getValue());
		response.setData(zookeeperConsoleService.updateNodeData(id, path, data));
		return response;
	}

	@PostMapping(value = "/node/delete", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Response<Boolean> updateNodeData(@RequestParam(name = "id") String id,
											@RequestParam(name = "path") String path) throws Exception {
		Response<Boolean> response = new Response<>();
		response.setCode(ResponseCode.SUCCESS.getValue());
		response.setData(zookeeperConsoleService.deleteNode(id, path));
		return response;
	}
}
