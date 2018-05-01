package club.throwable.zookeeper.console.controller;

import club.throwable.zookeeper.console.common.ResponseCode;
import club.throwable.zookeeper.console.model.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2018/4/25 22:10
 */
@Slf4j
@ControllerAdvice
public class ZookeeperConsoleControllerAdvice {

	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	public Response<String> exceptionHandler(Exception e) {
		log.error("Encounter global error!", e);
		Response<String> response = new Response<>();
		response.setCode(ResponseCode.ERROR.getValue());
		response.setMessage(e.getMessage());
		return response;
	}
}
