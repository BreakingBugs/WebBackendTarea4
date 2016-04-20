package py.una.pol.web.tarea4.mapper;

import org.apache.ibatis.annotations.Select;
import py.una.pol.web.tarea4.model.Customer;

import java.util.List;


public interface CustomerMapper {
  @Select("SELECT * FROM Customer WHERE id = #{id}")
  Customer getCustomer(int id);

  @Select("SELECT * FROM Customer")
  List<Customer> getCustomers();
}
