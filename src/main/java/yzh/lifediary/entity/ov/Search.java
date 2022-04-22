package yzh.lifediary.entity.ov;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import yzh.lifediary.entity.Diary;
import yzh.lifediary.entity.User;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Search {
    private List<User> users;
    private List<DiaryItemOv> diarys;

}
