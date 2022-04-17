package yzh.lifediary.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import yzh.lifediary.entity.DiaryImage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import yzh.lifediary.entity.User;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yzh
 * @since 2022-04-12
 */
public interface DiaryImageMapper extends BaseMapper<DiaryImage> {
    //并不会将picture.path字段加到User的path字段里

}
