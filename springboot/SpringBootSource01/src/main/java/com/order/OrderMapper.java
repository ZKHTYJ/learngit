package com.order;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
    // @Insert("insert into order_number(NAME, AGE) values(#{name}, #{age});")
    @Insert("insert into order_number values(null, #{number});")
    int insertOrder(@Param("number") String number);
}
