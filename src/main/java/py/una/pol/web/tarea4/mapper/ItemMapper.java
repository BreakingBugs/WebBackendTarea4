package py.una.pol.web.tarea4.mapper;

import org.apache.ibatis.annotations.*;
import py.una.pol.web.tarea4.model.Item;

import java.util.List;

public interface ItemMapper {
    Item getItem(int id);

    List<Item> getItems();


    Item getItemByName(String name);

    @Insert("INSERT INTO item(name, price, stock, provider_id) VALUES (#{name}, #{price}, #{stock}, #{provider.id})")
    void insertItem(Item item);
}
