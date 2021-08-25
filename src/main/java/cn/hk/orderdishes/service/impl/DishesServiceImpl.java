package cn.hk.orderdishes.service.impl;

import cn.hk.orderdishes.entity.Dishes;
import cn.hk.orderdishes.service.DishesService;
import cn.hutool.core.util.RandomUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Classname DishesServiceImpl
 * @Description TODO
 * @Date 2021/7/23 9:42
 * @Author liyi
 */
@Service
public class DishesServiceImpl implements DishesService {

    /**
     * 点菜
     *
     * @param dishes
     * @param meatNum
     * @param vegetableNum
     * @return
     */
    @Override
    public Set<Dishes> doOrder(List<Dishes> dishes, Integer meatNum, Integer vegetableNum) {

        List<Dishes> meat = dishes.stream().filter(ele -> ele.getMeatVegetable() == 1).collect(Collectors.toList());
        List<Dishes> vegetable = dishes.stream().filter(ele -> ele.getMeatVegetable() == 2).collect(Collectors.toList());

        Set<Dishes> meatSet = RandomUtil.randomEleSet(meat, meatNum);
        Set<Dishes> vegetableSet = RandomUtil.randomEleSet(vegetable, vegetableNum);

        boolean b = meatSet.addAll(vegetableSet);
        return meatSet;
    }
}
