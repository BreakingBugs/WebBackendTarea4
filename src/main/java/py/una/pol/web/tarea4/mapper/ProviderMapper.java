package py.una.pol.web.tarea4.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import py.una.pol.web.tarea4.model.Customer;
import py.una.pol.web.tarea4.model.Item;
import py.una.pol.web.tarea4.model.Provider;

import java.util.List;


public interface ProviderMapper {
    @Select("SELECT * FROM Provider WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "items", column = "id", javaType = List.class, many = @Many(select = "getItemsByProvider"))
    })
    Provider getProvider(int id);

    @Select("SELECT * FROM Provider")
    List<Provider> getProviders();

    @Select("SELECT * FROM Item WHERE provider_id=#{id}")
    List<Item> getItemsByProvider(int id);

    @Insert("INSERT INTO provider(name) VALUES (#{name})")
    void insertProvider(Provider p);

    @Update("UPDATE provider SET name=#{name} WHERE id=#{id}")
    void updateProvider(Provider p);

    @Delete("DELETE FROM provider WHERE id=#{id}")
    void deleteProvider(int id);
}
