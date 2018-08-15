/**
 * 添加修改页面抽象
 */

namespace com.clouddo.ui.page {

    //声明vue
    import RestUtil = com.clouddo.ui.util.RestUtil;
    declare let Vue : any;
    //生成jquery
    declare let $ : any;

    export class AddEditPage {

        //编辑/添加标识
        public static ADD_IDENT : string = "add";
        public static EDIT_IDENT : string = "edit";

        //页面标识，默认添加
        private ident : string = AddEditPage.ADD_IDENT;
        //保存更新接口地址
        private saveUpdateUrl: string;
        //加载数据接口
        private getUrl : string;

        //Vue实体
        protected formVue: any;

        //属性配置
        private attributeConfigs : Array<any>;

        //自定义方法
        private customMethods : {[index:string]: Function} = {};
        //自定义数据
        private customData : {[index:string]: any} = {};

        //键信息，用户向后台查询数据
        private keyObject : {[index:string]: any};

        /**
         * 构造函数
         * @param {string} saveUpdateUrl 保存更新URL地址
         * @param {string} ident 标识
         */
        constructor(saveUpdateUrl : string, getUrl : string) {
            this.saveUpdateUrl = saveUpdateUrl;
            this.getUrl = getUrl;
        }


        /**
         * 初始化
         */
        public init() : void {
            let pageObject = this;
            //解析属性信息
            let configs = this.analysisAttributeConfigs();
            this.formVue = new Vue({
                el: "#formVue",
                data: $.extend({}, {
                    formData: configs["formData"],
                    rules: configs["rules"],
                }, this.customData),
                methods: $.extend({}, {
                    //提交表单
                    submitForm: function (formName) {
                        //表单验证
                        pageObject.validForm(formName);
                    },
                    //重置表单
                    //TODO 修改功能的重置应改为原来的
                    resetForm: function (formName) {
                        pageObject.resetForm(formName);
                    }
                }, this.customMethods)
            });

            //如果是编辑页面，加载数据
            if(this.ident == AddEditPage.EDIT_IDENT) {
                //加载数据
                this.getData();
            }
        }

        /**
         * 设置vue自定义方法
         * @param {{[p: string]: Function}} customMethods
         * @returns {com.clouddo.ui.page.AddEditPage}
         */
        public setCustomMethods(customMethods : {[index:string]: Function}) : AddEditPage{
            this.customMethods = customMethods;
            return this;
        }

        /**
         * 设置vue自定义数据
         * @param {{[p: string]: any}} customData
         * @returns {com.clouddo.ui.page.AddEditPage}
         */
        public setCustomData(customData : {[index:string]: any}) : AddEditPage {
            this.customData = customData;
            return this;
        }

        /**
         * 设置属性配置
         * @param {Array<any>} attributeConfigs
         * @returns {com.clouddo.ui.page.AddEditPage}
         */
        public setAttributeConfigs(attributeConfigs : Array<any>) : AddEditPage {
            this.attributeConfigs = attributeConfigs;
            return this;
        }

        /**
         * 设置keyObject
         * @returns {com.clouddo.ui.page.AddEditPage}
         */
        public setKeyData(keyData : {[index:string]: any}) : AddEditPage {
            this.keyObject = keyData;
            return this;
        }

        /**
         * 设置页面标识
         * @param {string} ident
         * @returns {com.clouddo.ui.page.AddEditPage}
         */
        public setIdent(ident : string) : AddEditPage{
            this.ident = ident;
            return this;
        }



        //-----------以下方法可重写修改功能------

        /**
         * 加载编辑数据
         */
        public getData() : void {
            RestUtil.postAjax(this.getUrl, this.keyObject, this.getDataSuccess, null);
        }

        /**
         * 数据加载成功后执行
         * @param data
         */
        public getDataSuccess = (data) => {
            this.formVue.formData = data;
        }

        /**
         * 验证表单
         * @param formName 表单名称
         */
        public validForm(formName) : void {
            this.formVue.$refs[formName].validate((valid) => {
                if (valid) {
                    this.submitData(this.formVue.formData);
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        }

        /**
         * 重置表单
         * @param formName
         */
        public resetForm(formName) : void {
            this.formVue.$refs[formName].resetFields();
        }

        /**
         * 提交数据
         * @param data
         */
        public submitData(data) {
            console.log(data);
            RestUtil.postAjax(this.saveUpdateUrl, data, this.addEditSuccess, this.addEditFail);

        }

        /**
         * 保存修改成功函数
         * @param data
         */
        public addEditSuccess(result) {
            //关闭当前窗口，如果在iframe中，关闭弹窗如果不在则显示保存成功
            if (self != top) {
                //关闭当前窗口，刷新上级页面
                let index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
                if (parent.tableObject) {
                    //TODO 精准添加数据
                    parent.tableObject.reload();
                }
            } else {
                layer.alert("保存成功！", {icon: "1"})
            }

        }

        /**
         * 保存修改失败函数
         * @param result
         */
        public addEditFail(result) {
            console.log(result);
            layer.alert("保存时发生错误！", {icon: "0"});
        }




        //-------------私有方法---------
        /**
         * 解析属性信息
         * @returns {any}
         */
        private analysisAttributeConfigs() : any {
            if (this.attributeConfigs) {
                let formData = {};
                let rules = {};
                //循环遍历所有属性配置
                for (let attributeConfig of this.attributeConfigs) {
                    //获取name值
                    formData[attributeConfig["name"]] = null;
                    //获取role配置
                    let role = {};
                    //required解析
                    if (attributeConfig["required"] != undefined) {
                        role["required"] = attributeConfig["required"];
                    }
                    if (attributeConfig["message"]) {
                        role["message"] = attributeConfig["message"];
                    }
                    if (attributeConfig["trigger"]) {
                        role["trigger"] = attributeConfig["trigger"];
                    }
                    if (attributeConfig["type"]) {
                        role["type"] = attributeConfig["type"];
                    }

                    //判断roles是否已经还有该条规则
                    if (!rules[attributeConfig["name"]]) {
                        rules[attributeConfig["name"]] = [];
                    }
                    rules[attributeConfig["name"]].push(role);
                }

                return {formData : formData, rules: rules};
            }
            return null;
        }

    }


}