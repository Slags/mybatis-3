import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Created by FqCoder
 * @Date 2020/3/4 下午4:02
 * @Description TODO
 */
public class MybatisTest {

  /**
   * 传统方式
   * @throws IOException
   */
  public void test1()throws IOException{

    //1.读取配置文件，读成字节输入流：还未解析
    InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

    //2.解析XMl配置文件，封装成Configuration对象，创建DefaultSqlSessionFactory对象
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

    //3.生产了DefaultSqlSession实例对象 设置了事务不自动提交  完成了executor 对象的创建
    SqlSession sqlSession=sqlSessionFactory.openSession();

    /**
     * 1.根据Statement Id 来从Configuration中map集合中获取到了指定的MappedStatement对象
     * 2.将查询任务委派了executor执行器
     */
    List<Object> objects = sqlSession.selectList("namespace.id");

  }

  /**
   * mapper代理方式
   * @throws IOException
   */
  public void test2()throws IOException{
    InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
    SqlSession sqlSession=sqlSessionFactory.openSession();

    //使用JDK动态代理对Mapper借口产生代理对象
    IUserMapper userMapper=sqlSession.getMapper(IUserMapper.class);
    List<Object> objects = userMapper.queryAll();

  }
}
