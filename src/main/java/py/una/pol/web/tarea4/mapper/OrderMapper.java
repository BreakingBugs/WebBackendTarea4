package py.una.pol.web.tarea4.mapper;

import org.apache.ibatis.annotations.Select;
import py.una.pol.web.tarea4.model.Order;

import java.util.List;

public interface OrderMapper {
  @Select("SELECT * FROM Order WHERE id = #{id}")
  Order getOrder(int id);

  @Select("SELECT * FROM Order")
  List<Order> getOrders();
}
