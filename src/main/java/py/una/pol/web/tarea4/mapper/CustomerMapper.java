package py.una.pol.web.tarea4.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import py.una.pol.web.tarea4.model.Customer;

import java.util.List;


public interface CustomerMapper {
  @Select("SELECT * FROM Customer WHERE id = #{id}")
  Customer getCustomer(int id);

  @Select("SELECT * FROM Customer")
  List<Customer> getCustomers();

  @Insert("INSERT INTO customer(name, amountToPay) VALUES (#{name}, #{amountToPay})")
  void insertCustomer(Customer c);

  @Update("UPDATE customer SET name=#{name}, amountToPay=#{amountToPay} WHERE id=#{id}")
  void updateCustomer(Customer c);

  @Delete("DELETE FROM customer WHERE id=#{id}")
  void deleteCustomer(int id);
}
