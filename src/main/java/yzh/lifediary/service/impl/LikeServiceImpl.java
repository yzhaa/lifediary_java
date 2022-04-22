package yzh.lifediary.service.impl;

import org.springframework.web.bind.annotation.PostMapping;
import yzh.lifediary.entity.Like;
import yzh.lifediary.entity.MyMessage;
import yzh.lifediary.mapper.LikeMapper;
import yzh.lifediary.service.LikeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yzh
 * @since 2022-04-12
 */
@Service
public class LikeServiceImpl extends ServiceImpl<LikeMapper, Like> implements LikeService {


}
