package club.throwable.zookeeper.console.dao;

import club.throwable.zookeeper.console.model.entity.ZookeeperInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2018/4/24 22:44
 */
@Repository
public class ZookeeperInfoDao {

	private static final RowMapper<ZookeeperInfo> ROW_MAPPER = BeanPropertyRowMapper.newInstance(ZookeeperInfo.class);
	private static final String FIND_ALL = "SELECT * FROM t_zookeeper_info";
	private static final String FIND_PAGINATION = FIND_ALL + " LIMIT ?,?";
	private static final String FIND_BY_ID = FIND_ALL + " WHERE id = ?";
	private static final String SAVE = "INSERT INTO t_zookeeper_info(id,connection_string,description,session_timeout) VALUES (?,?,?,?)";
	private static final String UPDATE = "UPDATE t_zookeeper_info SET connection_string = ?,description = ?,session_timeout = ? WHERE id = ?";
	private static final String DELETE = "DELETE FROM t_zookeeper_info WHERE id = ?";
	private static final String COUNT = "SELECT COUNT(id) FROM t_zookeeper_info";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<ZookeeperInfo> findAll() {
		return jdbcTemplate.query(FIND_ALL, ROW_MAPPER);
	}

	public Integer saveZookeeperInfo(ZookeeperInfo zookeeperInfo) {
		return jdbcTemplate.update(SAVE, preparedStatement -> {
			preparedStatement.setString(1, zookeeperInfo.getId());
			preparedStatement.setString(2, zookeeperInfo.getConnectionString());
			preparedStatement.setString(3, zookeeperInfo.getDescription());
			preparedStatement.setInt(4, zookeeperInfo.getSessionTimeout());
		});
	}

	public Integer updateZookeeperInfo(ZookeeperInfo zookeeperInfo) {
		return jdbcTemplate.update(UPDATE, preparedStatement -> {
			preparedStatement.setString(1, zookeeperInfo.getConnectionString());
			preparedStatement.setString(2, zookeeperInfo.getDescription());
			preparedStatement.setInt(3, zookeeperInfo.getSessionTimeout());
			preparedStatement.setString(4, zookeeperInfo.getId());
		});
	}

	public Integer deleteZookeeperInfo(String id) {
		return jdbcTemplate.update(DELETE, preparedStatement -> {
			preparedStatement.setString(1, id);
		});
	}

	public List<ZookeeperInfo> queryZookeeperInfoByPagination(Integer pageNumber, Integer pageSize) {
		return jdbcTemplate.query(FIND_PAGINATION, preparedStatement -> {
			preparedStatement.setInt(1, pageNumber);
			preparedStatement.setInt(2, pageSize);
		}, ROW_MAPPER);
	}

	public Integer countTotal() {
		return jdbcTemplate.queryForObject(COUNT, Integer.class);
	}

	public ZookeeperInfo findById(String id) {
		return jdbcTemplate.queryForObject(FIND_BY_ID, new Object[]{id}, ROW_MAPPER);
	}
}
