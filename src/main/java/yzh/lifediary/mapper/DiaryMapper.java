package yzh.lifediary.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import yzh.lifediary.entity.Diary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import yzh.lifediary.entity.ov.DiaryItemOv;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yzh
 * @since 2022-04-12
 */
public interface DiaryMapper extends BaseMapper<Diary> {
    @Select("select count(like.id)>0 isLike, user.name username,diary_image.path mainPic ,diary.id id,title,like_count likeCount, " +
            "user.id userId,icon_path userIcon from diary left join `like` on  like.d_id=diary.id and like.u_id=5,diary_image,user " +
            "where user.id =diary.user_id and diary_image.diary_id=diary.id and diary_image.main=1  group by diary.id,diary_image.id")
    List<DiaryItemOv> getDiaryOV(@Param("id") int id);


}
