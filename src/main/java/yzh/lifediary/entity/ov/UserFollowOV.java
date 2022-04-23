package yzh.lifediary.entity.ov;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserFollowOV {
    private Integer id;
    private String iconPath;
    private String name;
    private Integer account;

    private boolean follow;

}
