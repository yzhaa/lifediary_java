package yzh.lifediary.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import yzh.lifediary.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yzh
 * @since 2022-03-13
 */
public interface UserService extends IService<User> , UserDetailsService {


}
