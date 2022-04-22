package yzh.lifediary;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.http.converter.HttpMessageConverter;

@SpringBootApplication
@ServletComponentScan
@MapperScan("yzh.lifediary.mapper")


public class LifeDiaryApplication {

    public static void main(String[] args) {
        SpringApplication.run(LifeDiaryApplication.class, args);
    }



}
