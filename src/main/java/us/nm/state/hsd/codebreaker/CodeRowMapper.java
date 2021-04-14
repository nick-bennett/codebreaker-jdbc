package us.nm.state.hsd.codebreaker;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.jdbc.core.RowMapper;

public class CodeRowMapper implements RowMapper<Map<String, Object>> {

  @Override
  public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
    
    ByteBuffer buffer = ByteBuffer.wrap(rs.getBytes("code_id"));
    long msb = buffer.getLong();
    long lsb = buffer.getLong();
    UUID id = new UUID(msb, lsb);
    
    Map<String, Object> data = new HashMap<>();
    data.put("code_id", id);
    data.put("created", rs.getDate("created"));
    data.put("length", rs.getInt("length"));
    data.put("pool", rs.getString("pool"));
    data.put("code_text", rs.getString("code_text"));
    
    return data;
  }

}
