package ${_package};

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
* 主类（项目启动入口）
*/
@SpringBootApplication
@MapperScan("${_package}.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class ${_artifactId}Application {

    public static void main(String[] args) {
        SpringApplication.run(${_artifactId}Application.class, args);
    }

}
