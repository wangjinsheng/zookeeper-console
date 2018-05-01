package club.throwable.zookeeper.console;

import club.throwable.zookeeper.console.service.ZookeeperConsoleService;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2018/4/24 22:24
 */
@SpringBootApplication
public class ZookeeperConsoleApplication implements CommandLineRunner, WebMvcConfigurer {

	@Autowired
	private ZookeeperConsoleService zookeeperConsoleService;

	public static void main(String[] args) {
		SpringApplication.run(ZookeeperConsoleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		zookeeperConsoleService.registerAllLocalCuratorFramework();
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.clear();
		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setCharset(Charset.forName("UTF-8"));
		converter.setFastJsonConfig(fastJsonConfig);
		List<MediaType> mediaTypes = new ArrayList<>();
		mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		converter.setSupportedMediaTypes(mediaTypes);
		converters.add(converter);
	}
}
