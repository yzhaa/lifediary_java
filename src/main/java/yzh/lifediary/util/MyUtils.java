package yzh.lifediary.util;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import yzh.lifediary.common.lang.Message;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

public class MyUtils {
    public static void      jsonResponse(HttpServletResponse response, int code, String  msg, Serializable data) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Message message = new Message();
        message.setCode(code);
        message.setMessage(msg);
        message.setData(data);

        response.getWriter().write(JSON.toJSONStringWithDateFormat(message, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat));

        response.flushBuffer();
    }
}
