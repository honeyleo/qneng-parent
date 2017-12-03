import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.lfy.base.mapper.UserMapper;
import cn.lfy.base.model.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class DaoTest {

	@Autowired
	private UserMapper userDAO;
	
	@Test
	public void getByUsername() {
		User user = userDAO.selectByUsername("admin");
		System.out.println(user);
	}
	
	@Test
	public void pageList() {
	}
	
	@Test
	public void list3() {
	}
}
