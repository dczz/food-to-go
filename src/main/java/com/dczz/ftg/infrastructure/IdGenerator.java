package com.dczz.ftg.infrastructure;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDGenerator;

@Slf4j
public class IdGenerator extends UUIDGenerator {

  @SneakyThrows
  @Override
  public Serializable generate (SharedSessionContractImplementor session, Object object) {
    String id = UUID.randomUUID().toString().replace("-","");
    Connection connection = session.connection();
    try (Statement statement = connection.createStatement()) {
      ResultSet rs = statement.executeQuery("select count(*) from id_generator where id = '" + id + "'");
      rs.next();
      if (rs.getInt(1) != 1) {
        return id;
      }
      generate(session, object);
      return id;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }
}
