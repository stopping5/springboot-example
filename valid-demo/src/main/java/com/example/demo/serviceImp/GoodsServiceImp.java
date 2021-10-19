package com.example.demo.serviceImp;

import com.example.demo.entity.Goods;
import com.example.demo.dao.GoodsMapper;
import com.example.demo.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author stopping
 * @since 2020-12-22
 */
@Service
public class GoodsServiceImp extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

}
