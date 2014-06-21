/*!
 * Ext JS Library 4.0
 * Copyright(c) 2006-2011 Sencha Inc.
 * licensing@sencha.com
 * http://www.sencha.com/license
 */

Ext.define('MyDesktop.douban', {
    extend: 'Ext.ux.desktop.Module',

    requires: [ ],

    id:'douban',
    init : function(){
        this.launcher = {
            text: '豆瓣电台',
            iconCls:'notepad'
        }
    },

    createWindow : function(){
        var desktop = this.app.getDesktop();
        var win = desktop.getWindow('douban');
        if(!win){
            win = desktop.createWindow({
                id: 'douban',
                title:'豆瓣电台',
                width:430,
                height:221,
                iconCls: 'notepad',
                animCollapse:false,
                border: false,
                hideMode: 'offsets',
                layout: 'fit',
                html:'<iframe width=100% height=100% src="http://douban.fm/partner/webqq?mode=third_party"></iframe>'
            });
        }
        return win;
    }
});
