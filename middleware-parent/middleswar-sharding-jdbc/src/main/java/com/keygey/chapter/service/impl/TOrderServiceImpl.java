package com.keygey.chapter.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.keygey.chapter.entity.TOrder;
import com.keygey.chapter.mapper.TOrderMapper;
import com.keygey.chapter.service.TOrderService;
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService{

}
