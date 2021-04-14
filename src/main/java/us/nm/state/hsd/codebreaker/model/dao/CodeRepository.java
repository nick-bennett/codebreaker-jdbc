package us.nm.state.hsd.codebreaker.model.dao;

import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import us.nm.state.hsd.codebreaker.CodeRowMapper;

@Repository
public class CodeRepository {

  private final JdbcTemplate jdbcTemplate;
  private final NamedParameterJdbcTemplate namedTemplate;
  private final SimpleJdbcInsert insertTemplate;

  @Autowired
  public CodeRepository(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
    namedTemplate = new NamedParameterJdbcTemplate(dataSource);
    insertTemplate = new SimpleJdbcInsert(dataSource).withTableName("code");
  }
  
  
  public void add(int length, String pool, String text) {
    UUID id = UUID.randomUUID();
    Map<String, Object> columnValues = new HashMap<>();
    ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
    buffer.putLong(id.getMostSignificantBits());
    buffer.putLong(id.getLeastSignificantBits());
    
    columnValues.put("created", new Date());
    columnValues.put("code_id", buffer.array());
    columnValues.put("length", length);
    columnValues.put("pool", pool);
    columnValues.put("code_text", text);

    insertTemplate.execute(columnValues);  
  }
  
   
  public List<Map<String, Object>> getAll() {
    String query = "SELECT * FROM code";
    return jdbcTemplate.query(query, new CodeRowMapper());
  }
  
}
