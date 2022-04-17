package yzh.lifediary.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MyMessage <T> {

    private int code;

    private String message;
    private T data;

    public MyMessage(int code, T data , String msg) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public MyMessage() {
    }
}
