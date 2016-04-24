package py.una.pol.web.tarea4.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import py.una.pol.web.tarea4.model.Item;

import java.util.List;

public interface ItemMapper {
    Item getItem(int id);

    List<Item> getItems();


    Item getItemByName(String name);

    @Insert("INSERT INTO item(name, price, stock, provider_id) VALUES (#{name}, #{price}, #{stock}, #{provider.id})")
    void insertItem(Item item);

    @Update("UPDATE item SET name=#{name}, price=#{price}, stock=#{stock}, provider_id=#{provider.id} WHERE id=#{id}")
    void updateItem(Item item);

    @Delete("DELETE FROM item WHERE id=#{id}")
    void deleteItem(int id);
}
