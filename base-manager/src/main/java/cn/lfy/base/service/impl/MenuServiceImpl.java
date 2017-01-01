package cn.lfy.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import cn.lfy.base.dao.MenuDAO;
import cn.lfy.base.dao.RoleDefaultMenuDAO;
import cn.lfy.base.model.Criteria;
import cn.lfy.base.model.Menu;
import cn.lfy.base.model.RoleDefaultMenu;
import cn.lfy.base.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDAO menuDAO;
    
    @Autowired
    private RoleDefaultMenuDAO roleDefaultMenuDAO;
    
    @Override
    public int save(Menu record) {
        if(record.getId() == null || record.getId().intValue() < 1){
        	int ret = menuDAO.insert(record);
        	RoleDefaultMenu roleMenu = new RoleDefaultMenu();
            roleMenu.setRoleId(1L);
            roleMenu.setMenuId(record.getId());
        	roleDefaultMenuDAO.insert(roleMenu);
            return ret;
        }else{
            Menu old=this.getById(record.getId());
            int cnt=this.updateByIdSelective(record);
            if(!old.getParentId().equals(record.getParentId())){
                String oldParentIdPath = old.getParentIdPath() + old.getId() + "$";
                String newParentIdPath = record.getParentIdPath() + record.getId() + "$";
                menuDAO.updateChildParentPath(oldParentIdPath, newParentIdPath);
            }
            return cnt;
        }
    }

    @Override
    public Menu getById(Long id) {
        return menuDAO.selectByPrimaryKey(id);
    }
    
    @Cacheable(value = "commonCache", key = "'Menu_id_' + #id")
    @Override
    public Menu getByIdInCache(Long id) {
        return menuDAO.selectByPrimaryKey(id);
    }

    @Override
    public int updateByIdSelective(Menu record) {
        return menuDAO.updateByPrimaryKeySelective(record);
    }
    
    @Override
    public void deleteById(Long id){
    	List<Menu> list = listSubMenuByParentId(String.valueOf(id));
    	if(list != null) {
    		for(Menu menu : list) {
    			roleDefaultMenuDAO.deleteByMenuId(menu.getId());
    		}
    	}
        menuDAO.deleteByPrimaryKey(id);
        roleDefaultMenuDAO.deleteByMenuId(id);
    }

	@Override
	public List<Menu> findMenuList() {

		return menuDAO.selectByExample(new Criteria());
	}
	
	public List<Menu> listAllParentMenu() {
		Criteria c = new Criteria();
		c.put("parentId", 1);
		return menuDAO.selectByExample(c);
	}
	
	public List<Menu> listSubMenuByParentId(String parentId) {
		Criteria c = new Criteria();
		c.put("parentId", parentId);
		return menuDAO.selectByExample(c);
	}
	
	public boolean updateIcon(Long id, String icon) {
		Menu menu = new Menu();
		menu.setId(id);
		menu.setIcon(icon);
		return menuDAO.updateByPrimaryKeySelective(menu) > 0 ? true : false;
	}

}
