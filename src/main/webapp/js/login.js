Ext.define('LoginForm', {
	extend: 'Ext.form.Panel',
	id: 'loginForm',
	alias: 'widget.loginform',
	baseCls: 'x-plain',
	bodyPadding: 5,
	width: 250,
	height: 80,
	layout: 'anchor',
	defaults: {
		anchor: '100%',
		labelWidth: 50
	},
	defaultType: 'textfield',
	items: [{
		id: 'username',
		fieldLabel: '用户名',
		name: 'username',
		allowBlank: false
	}, {
		id: 'password',
		fieldLabel: '密码',
		name: 'password',
		allowBlank: false
	}]
});

Ext.define('LoginWin', {
	extend: 'Ext.Window',
	title: '管理员登录窗口',
	width: 270,
	height: 125,
	resizable: false,
	closable: false,
	modal: true,
	items: [{
		xtype: 'loginform'
	}],
	buttons: [{
		text: '登录',
		handler: function(){
			var loginform = Ext.getCmp('loginForm').getForm();
			if (loginform.isValid) {
				loginform.submit({
					url: './adminLogin.jxp?action=adminLogin',
					method: 'post',
					success: function(form, action){
						Ext.Msg.alert("提示", action.result.msg);
					},
					failure: function(form, action){
						Ext.Msg.alert("提示", action.result.msg);
					}
				});
			}
		}
	}]
	
});