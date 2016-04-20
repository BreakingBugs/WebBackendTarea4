package py.una.pol.web.tarea4.initialization;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.IOException;
import java.io.InputStream;

@Startup
@Singleton
public class MyBatisSingleton {
  private SqlSessionFactory factory;

  @PostConstruct
  void init() throws IOException {
    String resource = "mybatis/config.xml";
    InputStream inputStream;
    inputStream = Resources.getResourceAsStream(resource);
    factory = new SqlSessionFactoryBuilder().build(inputStream);
  }

  @Lock(LockType.READ)
  public SqlSessionFactory getFactory() {
    return factory;
  }
}
