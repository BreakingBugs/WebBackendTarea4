package py.una.pol.web.tarea4.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import py.una.pol.web.tarea4.model.Item;

import java.util.List;

public interface ItemMapper {
    @Select("SELECT * FROM Item WHERE id = #{id}")
    Item getItem(int id);

    @Select("SELECT * FROM Item")
    List<Item> getItems();

    @Select("SELECT * FROM Item WHERE name = #{name}")
    Item getItemByName(String name);

    @Insert("INSERT INTO item(name, price, stock, provider_id) VALUES (#{name}, #{price}, #{stock}, #{provider.id})")
    void insertItem(Item item);
}
