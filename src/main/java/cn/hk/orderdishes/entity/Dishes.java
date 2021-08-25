package cn.hk.orderdishes.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Classname Dishes
 * @Description TODO
 * @Date 2021/7/23 10:43
 * @Author liyi
 */
@Data
@EqualsAndHashCode
public class Dishes {

    private String name;

    private Integer meatVegetable;

    private Integer price;


}
