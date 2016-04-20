package py.una.pol.web.tarea4.mapper;

import org.apache.ibatis.annotations.Select;
import py.una.pol.web.tarea4.model.DuplicateItem;

import java.util.List;


public interface DuplicateItemMapper {
  @Select("SELECT * FROM DuplicateItem WHERE id = #{id}")
  DuplicateItem getDuplicateItem(int id);

  @Select("SELECT * FROM DuplicateItem")
  List<DuplicateItem> getDuplicateItems();
}
