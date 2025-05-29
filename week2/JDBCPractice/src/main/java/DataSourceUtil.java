import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

public class DataSourceUtil {
    private static final HikariDataSource ds;

    static {
        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        // cfg.setUsername("myapp");
        // cfg.setPassword("secret");
        cfg.setMaximumPoolSize(10);
        ds = new HikariDataSource(cfg);
    }

    public static DataSource getDataSource() {
        return ds;
    }
}
