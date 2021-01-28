package mybatisplusdemo.demo.serviceImp;

import mybatisplusdemo.demo.entity.User;
import mybatisplusdemo.demo.dao.UserMapper;
import mybatisplusdemo.demo.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stopping
 * @since 2020-12-21
 */
@Service
public class UserServiceImp extends ServiceImpl<UserMapper, User> implements UserService {

}
