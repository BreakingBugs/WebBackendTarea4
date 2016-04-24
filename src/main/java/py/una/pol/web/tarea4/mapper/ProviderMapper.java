package py.una.pol.web.tarea4.mapper;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;
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
}
