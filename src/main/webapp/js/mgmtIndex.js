/**
 *
 *	导航栏相关代码
 *	Author: hzucmj
 *
 **/
Ext.define('MgmtApp.V', {
	extend: 'Ext.Viewport',
	layout: 'border',
	items: [{
		xtype: 'mainview',
		id: 'app-mainview'
	}, {
		xtype: 'header'
	}, {
		xtype: 'nav'
	}, {
		xtype: 'footer'
	}]
});
/**
 *
 *	主视图相关代码
 *	Author: hzucmj
 *
 **/
Ext.define('MgmtApp.Mainview', {
	extend: 'Ext.tab.Panel',
	alias: 'widget.mainview',
//	id: 'app-mainview',
	region: 'center',
	items: [{
		xtype: 'panel',
		title: 'Welcome'
	}]
});
/**
 *
 *	导航栏相关代码
 *	Author: hzucmj
 *
 **/
Ext.define('MgmtApp.Addorg', {
	extend: 'Ext.panel.Panel',
	id: 'add-org',
	title: '添加组织',
	html: '添加组织',
	closable: true
});
Ext.define('MgmtApp.Nav', {
	extend: 'Ext.panel.Panel',
	alias: 'widget.nav',
	id: 'app-nav',
	layout: 'accordion',
	title: '导航栏',
	//html: 'west',
	region: 'west',
	width: 200,
	items: [{
		title: '组织管理',
		layout: 'vbox',
		items: [{
			xtype: 'button',
			id: 'add-mgmt-btn',
			text: '添加组织',
			handler: function(){
				var cmp = Ext.getCmp('add-org');
				var tab = Ext.getCmp('app-mainview');
				if (cmp) {
					tab.setActiveTab(cmp);
				} else {
					cmp = new MgmtApp.Addorg();
					tab.add(cmp);
					tab.setActiveTab(cmp);
				}
			}
		}, {
			xtype: 'button',
			id: 'update-mgmt-btn',
			text: '更新组织信息',
			handler: function(){
				Ext.Msg.alert("提示", this.text);
			}
		}]
	}, {
		title: '成员管理'
	}]
});

/**
 *
 *	头部信息相关代码
 *	Author: hzucmj
 *
 **/
Ext.define('MgmtApp.Header', {
	extend: 'Ext.panel.Panel',
	alias: 'widget.header',
	id: 'app-header',
	html: 'header',
	border: false,
	region: 'north',
	height: 50
});
/**
 *
 *	底部相关代码
 *	Author: hzucmj
 *
 **/
Ext.define('MgmtApp.Footer', {
	extend: 'Ext.panel.Panel',
	alias: 'widget.footer',
	id: 'app-footer',
	html: 'footer',
	border: false,
	region: 'south',
	height: 50
});