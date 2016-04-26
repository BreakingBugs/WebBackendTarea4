package py.una.pol.web.tarea4.mapper;

import org.apache.ibatis.annotations.*;
import py.una.pol.web.tarea4.model.*;

import java.util.List;


public interface CustomerMapper {
  @Select("SELECT * FROM Customer WHERE id = #{id}")
  Customer getCustomer(int id);

  @Select("SELECT * FROM Customer")
  @Results({
          @Result(property = "id", column = "id"),
          @Result(property = "payments", column = "id", javaType = List.class, many = @Many(select = "getPaymentsByCustomer")),
          @Result(property = "sales", column = "id", javaType = List.class, many = @Many(select = "getSalesByCustomer"))
  })
  List<Customer> getCustomers();

  @Insert("INSERT INTO customer(name, amountToPay) VALUES (#{name}, #{amountToPay})")
  void insertCustomer(Customer c);

  @Update("UPDATE customer SET name=#{name}, amountToPay=#{amountToPay} WHERE id=#{id}")
  void updateCustomer(Customer c);

  @Delete("DELETE FROM customer WHERE id=#{id}")
  void deleteCustomer(int id);

  @Select("SELECT * FROM Payment WHERE customer_id=#{id}")
  List<Payment> getPaymentsByCustomer(int id);

  @Select("SELECT * FROM Sale WHERE customer_id=#{id}")
  @Results({
          @Result(property = "id", column = "id"),
          @Result(property = "orders", column = "id", javaType = List.class, many = @Many(select = "py.una.pol.web.tarea4.mapper.SaleOrderMapper.getOrdersBySale"))
  })
  List<Sale> getSalesByCustomer(int id);
}
