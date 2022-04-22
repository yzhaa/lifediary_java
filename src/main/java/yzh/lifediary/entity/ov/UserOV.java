package yzh.lifediary.entity.ov;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserOV {
    private Integer id;
    private String iconPath;
    private String name;
    private Integer account;
}
