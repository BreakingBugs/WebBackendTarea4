package py.una.pol.web.tarea4.mapper;

import org.apache.ibatis.annotations.Select;
import py.una.pol.web.tarea4.model.SaleOrder;

import java.util.List;

public interface SaleOrderMapper {
  @Select("SELECT * FROM SaleOrder WHERE id = #{id}")
  SaleOrder getSaleOrder(int id);

  @Select("SELECT * FROM SaleOrder")
  List<SaleOrder> getSaleOrders();
}