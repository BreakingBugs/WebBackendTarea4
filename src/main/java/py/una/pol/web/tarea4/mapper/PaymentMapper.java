package py.una.pol.web.tarea4.mapper;

import org.apache.ibatis.annotations.Select;
import py.una.pol.web.tarea4.model.Payment;

import java.util.List;

public interface PaymentMapper {
  @Select("SELECT * FROM Payment WHERE id = #{id}")
  Payment getPayment(int id);

  @Select("SELECT * FROM Payment")
  List<Payment> getPayments();
}
