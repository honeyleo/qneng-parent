package cn.lfy.qneng.tree;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;

public class TreeTest {

	public static void main(String[] args) {
		List<TreeNode> menuList = Lists.newArrayList( 
				new TreeNode("2","广西", "1"), new TreeNode("3","博白", "2"), new TreeNode("5","深圳", "4"), new TreeNode("1","中国", "0"), new TreeNode("4","广东", "1"), new TreeNode("6","广州", "4"), new TreeNode("7","凤山镇", "3"));
		  
		List<TreeNode> nodeList = new ArrayList<TreeNode>();  
		for(TreeNode node1 : menuList){  
		    boolean mark = false;  
		    for(TreeNode node2 : menuList) {  
		        if(node1.getParentId().equals(node2.getId())) {  
		            mark = true;  
		            if(node2.getChildren() == null)  
		                node2.setChildren(new ArrayList<TreeNode>());  
		            node2.getChildren().add(node1);   
		            break;  
		        }  
		    }  
		    if(!mark) {  
		        nodeList.add(node1);   
		    }  
		}  
		//转为json格式        
		String json = JSONArray.toJSONString(nodeList);
		System.out.println("json:"+json);  
	}
}
