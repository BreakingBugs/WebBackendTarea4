package py.una.pol.web.tarea4.mapper;

import org.apache.ibatis.annotations.Select;
import py.una.pol.web.tarea4.model.Sale;

import java.util.List;

public interface SaleMapper {
  @Select("SELECT * FROM Sale WHERE id = #{id}")
  Sale getSale(int id);

  @Select("SELECT * FROM Sale")
  List<Sale> getSales();
}
