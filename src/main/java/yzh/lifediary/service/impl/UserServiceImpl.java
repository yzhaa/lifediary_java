package yzh.lifediary.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import yzh.lifediary.entity.User;
import yzh.lifediary.mapper.UserMapper;
import yzh.lifediary.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yzh
 * @since 2022-03-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>  implements UserService {

    @Autowired
    PasswordEncoder passwordEncoder;


    //返回User即可，然后会根据这个进行比对（user调用getPassname）
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account", s);
        User user = baseMapper.selectOne(wrapper);

        if (user == null) {
            throw new UsernameNotFoundException("用户不存在！");
        }
        return user;
    }
}
