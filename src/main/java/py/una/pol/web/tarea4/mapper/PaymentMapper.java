package py.una.pol.web.tarea4.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import py.una.pol.web.tarea4.model.Payment;

import java.util.List;

public interface PaymentMapper {
  @Select("SELECT * FROM Payment WHERE id = #{id}")
  Payment getPayment(int id);

  @Select("SELECT * FROM Payment")
  List<Payment> getPayments();

  @Insert("INSERT INTO payment(amount, date, customer_id) VALUES (#{amount}, now(), #{customer.id})")
  void insertPayment(Payment p);
}
