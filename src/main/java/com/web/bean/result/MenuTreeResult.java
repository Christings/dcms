package com.web.bean.result;

import com.web.entity.Menu;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 菜单以树方式返回
 *
 * @author 杜延雷
 * @date 2016-06-29
 */
public class MenuTreeResult {
	private String id;

	private Short level;

	private String name;

	private Short rank;

	private String url;

	private String parentId;

	private String iconId;

	private Short type;

	private List<MenuTreeResult> childMenu;

	/**
	 * 将菜单转换成菜单树 返回到前端结果集
	 *
	 * @return 菜单结果
	 */
	public static MenuTreeResult convert(Menu menu) {
		if (null == menu) {
			return null;
		}

		MenuTreeResult result = new MenuTreeResult();
		BeanUtils.copyProperties(menu, result);

		return result;
	}

	/**
	 * 将菜单转换成菜单树 返回到前端结果集
	 *
	 * @param menus
	 * @return 菜单结果集
	 */
	public static List<MenuTreeResult> convert(Collection<Menu> menus) {
		if (null == menus || menus.size()==0) {
			return null;
		}

		List<MenuTreeResult> result = new ArrayList<>();
		for(Menu menu:menus){
			result.add(MenuTreeResult.convert(menu));
		}

		return result;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public Short getLevel() {
		return level;
	}

	public void setLevel(Short level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Short getRank() {
		return rank;
	}

	public void setRank(Short rank) {
		this.rank = rank;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId == null ? null : parentId.trim();
	}

	public String getIconId() {
		return iconId;
	}

	public void setIconId(String iconId) {
		this.iconId = iconId == null ? null : iconId.trim();
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public List<MenuTreeResult> getChildMenu() {
		return childMenu;
	}

	public void setChildMenu(List<MenuTreeResult> childMenu) {
		this.childMenu = childMenu;
	}
}