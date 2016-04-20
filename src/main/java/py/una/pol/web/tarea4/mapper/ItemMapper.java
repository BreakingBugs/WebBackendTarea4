package py.una.pol.web.tarea4.mapper;

import org.apache.ibatis.annotations.Select;
import py.una.pol.web.tarea4.model.Item;

import java.util.List;

public interface ItemMapper {
  @Select("SELECT * FROM Item WHERE id = #{id}")
  Item getItem(int id);

  @Select("SELECT * FROM Item")
  List<Item> getItems();
}
