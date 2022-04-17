package yzh.lifediary.common.lang;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Message  {
    private int code;
    private String message;

    private Object data;



    public Message() {
    }
}
