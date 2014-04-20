Ext.namespace("Ext.ux.form");
/**
 * 富文本编辑器控件
 * 扩展 Ext HtmlEditor度富文本
 * @class Ext.ux.form.MyHtmlEditorField
 * @extends Ext.form.HtmlEditor
 *  
 */
Ext.ux.form.MyHtmlEditorField = Ext.extend(Ext.form.HtmlEditor,{
	  initFrame : function(){  
        Ext.TaskMgr.stop(this.monitorTask);  
        var doc = this.getDoc();  
        this.win = this.getWin();  
        doc.open();  
        doc.write(this.getDocMarkup());  
        doc.close();  
  
        this.task = { // must defer to wait for browser to be ready  
            run : function(){  
                var doc = this.getDoc();  
                if(doc.body || doc.readyState == 'complete'){  
                    Ext.TaskMgr.stop(this.task);  
                    this.setDesignMode(true);  
                    this.initEditor.defer(10, this);  
                }  
            },  
            interval : 10,  
            duration:10000,  
            scope: this  
        };  
        Ext.TaskMgr.start(this.task);  
    },  
    beforeDestroy : function(){  
        if(this.monitorTask){  
            Ext.TaskMgr.stop(this.monitorTask);  
        }  
        if(this.task){  
            Ext.TaskMgr.stop(this.task);  
        }  
        if(this.rendered){  
            Ext.destroy(this.tb);  
            var doc = this.getDoc();  
            if(doc){  
                try{  
                    Ext.EventManager.removeAll(doc);  
                    for (var prop in doc){  
                        delete doc[prop];  
                    }  
                }catch(e){}  
            }  
            if(this.wrap){  
                this.wrap.dom.innerHTML = '';  
                this.wrap.remove();  
            }  
        }  
        Ext.ux.form.MyHtmlEditorField.superclass.beforeDestroy.call(this);  
    }
});
//注册成xtype以便能够延迟加载   
Ext.reg('myhtmleditorfield', Ext.ux.form.MyHtmlEditorField);  