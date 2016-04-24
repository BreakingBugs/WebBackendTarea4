package py.una.pol.web.tarea4.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import py.una.pol.web.tarea4.model.DuplicateItem;

import java.util.List;


public interface DuplicateItemMapper {
    @Select("SELECT * FROM producto_duplicado WHERE id = #{id}")
    DuplicateItem getDuplicateItem(int id);

    @Select("SELECT * FROM producto_duplicado")
    List<DuplicateItem> getDuplicateItems();

    @Insert("INSERT INTO producto_duplicado (item_id, cantidad) VALUES (#{item.id}, #{cantidad})")
    void insertDuplicateItem(DuplicateItem duplicate);

    @Update("UPDATE producto_duplicado SET cantidad=#{cantidad} WHERE id=#{id}")
    void updateDuplicateItem(DuplicateItem duplicate);

    @Delete("DELETE FROM producto_duplicado WHERE id=#{id}")
    void deleteDuplicateItem(int id);
}
