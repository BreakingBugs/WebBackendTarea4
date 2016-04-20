package py.una.pol.web.tarea4.mapper;

import py.una.pol.web.tarea4.model.Item;

import java.util.List;

/**
 * Created by jordan on 4/20/16.
 */
public interface ItemMapper {
  Item getItem(int id);
  List<Item> getItems();
}
