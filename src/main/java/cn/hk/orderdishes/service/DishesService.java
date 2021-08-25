package cn.hk.orderdishes.service;

import cn.hk.orderdishes.entity.Dishes;

import java.util.List;
import java.util.Set;

/**
 * @Classname DishesService
 * @Description TODO
 * @Date 2021/7/23 9:42
 * @Author liyi
 */
public interface DishesService {
    /**
     * 点菜
     * @param dishes
     * @param meatNum
     * @param vegetableNum
     * @return
     */
    Set<Dishes> doOrder(List<Dishes> dishes, Integer meatNum, Integer vegetableNum);
}
