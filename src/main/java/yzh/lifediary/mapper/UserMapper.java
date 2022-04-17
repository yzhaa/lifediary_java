package yzh.lifediary.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import yzh.lifediary.entity.Role;
import yzh.lifediary.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import javax.annotation.processing.RoundEnvironment;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yzh
 * @since 2022-03-13
 */
public interface UserMapper extends BaseMapper<User> {
//    #{param}：会进行预编译，而且进行类型匹配，最后进行变量替换，括号中可以添加映射类型如
//#{param,javaType=int,jdbcType=NUMERIC}
//
//    ${param}：只实现字符串拼接，并不进行数据类型匹配
    @Select("select * from role,user_role where role.id =user_role.rid and uid=#{id}")
    List<Role> getUserRoles(@Param("id") int id);

    //并不会将picture.path字段加到User的path字段里
    @Select("select user.*, picture.path from picture,user where user.icon =picture.id and user.account=123")
    User getUserWithIcon(@Param("account") String account);
}
