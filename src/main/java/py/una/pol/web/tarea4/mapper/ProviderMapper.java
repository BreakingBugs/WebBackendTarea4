package py.una.pol.web.tarea4.mapper;

import org.apache.ibatis.annotations.Select;
import py.una.pol.web.tarea4.model.Provider;

import java.util.List;


public interface ProviderMapper {
  @Select("SELECT * FROM Provider WHERE id = #{id}")
  Provider getProvider(int id);

  @Select("SELECT * FROM Provider")
  List<Provider> getProviders();
}
