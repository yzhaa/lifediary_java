package yzh.lifediary.entity.ov;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import yzh.lifediary.entity.Diary;
import yzh.lifediary.entity.DiaryImage;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DiaryDetails {

    private Diary diary;
    private List<DiaryImage> images;
}
