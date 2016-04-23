package py.una.pol.web.tarea4.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import py.una.pol.web.tarea4.model.Sale;

import java.util.List;

public interface SaleMapper {
  @Select("SELECT * FROM Sale WHERE id = #{id}")
  Sale getSale(int id);

  @Select("SELECT * FROM Sale")
  List<Sale> getSales();

  @Insert("INSERT INTO Sale(date, amount, customer_id) VALUES (now(), #{amount}, #{customer.id})")
  @Options(useGeneratedKeys=true)
  int insertSale(Sale s);

  @Update("UPDATE sale SET amount=#{amount} WHERE id=#{id}")
  void updateSale(Sale s);
}
