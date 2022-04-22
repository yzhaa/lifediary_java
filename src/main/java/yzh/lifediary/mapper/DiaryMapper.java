package yzh.lifediary.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import yzh.lifediary.entity.Diary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import yzh.lifediary.entity.ov.DiaryItemOv;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yzh
 * @since 2022-04-12
 */
public interface DiaryMapper extends BaseMapper<Diary> {
    @Select("select count(like.id)>0 isLike,date , user.name username,diary_image.path mainPic ,diary.id id,title,like_count likeCount, " +
            "user.id userId,icon_path userIcon from diary left join `like` on  like.d_id=diary.id and like.u_id=#{id},diary_image,user " +
            "where user.id =diary.user_id and diary_image.diary_id=diary.id and diary_image.main=1  group by diary.id,diary_image.id")
    List<DiaryItemOv> getDiaryOV(@Param("id") int id);

    @Update("update diary set like_count=like_count+1 where id =#{id}")
    void addLkeCount(@Param("id") int id);

    @Update("update diary set like_count=like_count-1 where id =#{id}")
    void deLkeCount(@Param("id") int id);


    //返回的数据应该尽可能的全，前台要不要是他们的事。
    @Select("select 1 isLike,date, user.name username,diary_image.path mainPic ,diary.id id,title,like_count likeCount, " +
            "user.id userId,icon_path userIcon from diary  ,`like` ,diary_image,user " +
            "where like.u_id=#{id} and diary.id=like.d_id and user.id =diary.user_id and diary_image.diary_id=diary.id and diary_image.main=1")
    List<DiaryItemOv> getCollect(@Param("id") int id);

    @Select("select count(like.id)>0 isLike,date ,content, user.name username,diary_image.path mainPic ,diary.id id,title,like_count likeCount, " +
            "user.id userId,icon_path userIcon from diary left join `like` on  like.d_id=diary.id and like.u_id=#{id},diary_image,user " +
            " where diary.user_id=#{id} and user.id =diary.user_id and diary_image.diary_id=diary.id and diary_image.main=1  group by diary.id,diary_image.id")
    List<DiaryItemOv> getPerson(@Param("id") int id);

    //有SQL语法错误，在like那里
    @Select("select title from diary where title like \"%\"#{title}\"%\"")
    List<String> getTitle(@Param("title") String title);


    @Select("select count(like.id)>0 isLike,date ,content , user.name username,diary_image.path mainPic ,diary.id id,title,like_count likeCount, " +
            "user.id userId,icon_path userIcon from diary left join `like` on  like.d_id=diary.id and like.u_id=#{id},diary_image,user " +
            "where user.id =diary.user_id and diary_image.diary_id=diary.id and diary_image.main=1 and " +
            "(diary.title like \"%\"#{title}\"%\" or diary.content like \"%\"#{title}\"%\" or user.name like \"%\"#{title}\"%\") group by diary.id,diary_image.id")
    List<DiaryItemOv> getSearchTitle(@Param("title") String title,@Param("id") int id);
}
