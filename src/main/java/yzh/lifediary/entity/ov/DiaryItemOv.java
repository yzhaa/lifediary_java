package yzh.lifediary.entity.ov;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DiaryItemOv {
    private Integer id;

    private String title;


    private Integer userId;

    private String userIcon;

    private String mainPic;

    private int likeCount;

    private Boolean isLike;

    private String username;


}
