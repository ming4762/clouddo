/**
 * 添加修改页面抽象
 */
var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
        return extendStatics(d, b);
    }
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var com;
(function (com) {
    var clouddo;
    (function (clouddo) {
        var ui;
        (function (ui) {
            var page;
            (function (page) {
                //声明vue
                var VueInit = com.clouddo.ui.vue.VueInit;
                var AuthRestUtil = com.clouddo.ui.util.AuthRestUtil;
                var AddEditPage = /** @class */ (function (_super) {
                    __extends(AddEditPage, _super);
                    // // vue挂在后事件
                    // private vueMountedFunction: Function
                    /**
                     * 构造函数
                     * @param {string} saveUpdateUrl 保存更新URL地址
                     * @param {string} ident 标识
                     */
                    function AddEditPage(saveUpdateUrl, getUrl) {
                        var _this = _super.call(this, '#formVue') || this;
                        //页面标识，默认添加
                        _this.ident = AddEditPage.ADD_IDENT;
                        /**
                         * 数据加载成功后执行
                         * @param data
                         */
                        _this.getDataSuccess = function (data) {
                            _this.formVue.formData = data;
                        };
                        _this.saveUpdateUrl = saveUpdateUrl;
                        _this.getUrl = getUrl;
                        return _this;
                    }
                    /**
                     * 初始化
                     */
                    AddEditPage.prototype.init = function () {
                        var pageObject = this;
                        //解析属性信息
                        var configs = this.analysisAttributeConfigs();
                        _super.prototype.addCustomData.call(this, {
                            formData: configs["formData"],
                            rules: configs["rules"],
                        });
                        _super.prototype.addCustomMethods.call(this, {
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
                        });
                        this.formVue = _super.prototype.init.call(this);
                        // this.formVue = new Vue({
                        //     el: "#formVue",
                        //     data: $.extend({}, {
                        //         formData: configs["formData"],
                        //         rules: configs["rules"],
                        //     }, this.customData),
                        //     methods: $.extend({}, {
                        //         //提交表单
                        //         submitForm: function (formName) {
                        //             //表单验证
                        //             pageObject.validForm(formName);
                        //         },
                        //         //重置表单
                        //         //TODO 修改功能的重置应改为原来的
                        //         resetForm: function (formName) {
                        //             pageObject.resetForm(formName);
                        //         }
                        //     }, this.customMethods),
                        //     // 生命周期钩子：挂在后状态
                        //     mounted: function () {
                        //
                        //     }
                        // });
                        //如果是编辑页面，加载数据
                        if (this.ident == AddEditPage.EDIT_IDENT) {
                            //加载数据
                            this.getData();
                        }
                    };
                    /**
                     * 设置vue自定义方法
                     * @param {{[p: string]: Function}} customMethods
                     * @returns {com.clouddo.ui.page.AddEditPage}
                     */
                    // public setCustomMethods(customMethods : {[index:string]: Function}) : AddEditPage{
                    //     this.customMethods = customMethods;
                    //     return this;
                    // }
                    //
                    // /**
                    //  * 设置vue自定义数据
                    //  * @param {{[p: string]: any}} customData
                    //  * @returns {com.clouddo.ui.page.AddEditPage}
                    //  */
                    // public setCustomData(customData : {[index:string]: any}) : AddEditPage {
                    //     this.customData = customData;
                    //     return this;
                    // }
                    /**
                     * 设置属性配置
                     * @param {Array<any>} attributeConfigs
                     * @returns {com.clouddo.ui.page.AddEditPage}
                     */
                    AddEditPage.prototype.setAttributeConfigs = function (attributeConfigs) {
                        this.attributeConfigs = attributeConfigs;
                        return this;
                    };
                    /**
                     * 设置keyObject
                     * @returns {com.clouddo.ui.page.AddEditPage}
                     */
                    AddEditPage.prototype.setKeyData = function (keyData) {
                        this.keyObject = keyData;
                        return this;
                    };
                    /**
                     * 设置页面标识
                     * @param {string} ident
                     * @returns {com.clouddo.ui.page.AddEditPage}
                     */
                    AddEditPage.prototype.setIdent = function (ident) {
                        this.ident = ident;
                        return this;
                    };
                    //-----------以下方法可重写修改功能------
                    /**
                     * 加载编辑数据
                     */
                    AddEditPage.prototype.getData = function () {
                        AuthRestUtil.postAjax(this.getUrl, this.keyObject, this.getDataSuccess, null);
                    };
                    /**
                     * 验证表单
                     * @param formName 表单名称
                     */
                    AddEditPage.prototype.validForm = function (formName) {
                        var _this = this;
                        this.formVue.$refs[formName].validate(function (valid) {
                            if (valid) {
                                _this.submitData(_this.formVue.formData);
                            }
                            else {
                                console.log('error submit!!');
                                return false;
                            }
                        });
                    };
                    /**
                     * 重置表单
                     * @param formName
                     */
                    AddEditPage.prototype.resetForm = function (formName) {
                        this.formVue.$refs[formName].resetFields();
                    };
                    /**
                     * 提交数据
                     * @param data
                     */
                    AddEditPage.prototype.submitData = function (data) {
                        AuthRestUtil.postAjax(this.saveUpdateUrl, data, this.addEditSuccess, this.addEditFail);
                    };
                    /**
                     * 保存修改成功函数
                     * @param data
                     */
                    AddEditPage.prototype.addEditSuccess = function (result) {
                        //关闭当前窗口，如果在iframe中，关闭弹窗如果不在则显示保存成功
                        if (self != top) {
                            //关闭当前窗口，刷新上级页面
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                            if (parent.tableObject) {
                                //TODO 精准添加数据
                                parent.tableObject.reload();
                            }
                        }
                        else {
                            layer.alert("保存成功！", { icon: "1" });
                        }
                    };
                    /**
                     * 保存修改失败函数
                     * @param result
                     */
                    AddEditPage.prototype.addEditFail = function (result) {
                        console.log(result);
                        layer.alert("保存时发生错误！", { icon: "0" });
                    };
                    //-------------私有方法---------
                    /**
                     * 解析属性信息
                     * @returns {any}
                     */
                    AddEditPage.prototype.analysisAttributeConfigs = function () {
                        if (this.attributeConfigs) {
                            var formData = {};
                            var rules = {};
                            //循环遍历所有属性配置
                            for (var _i = 0, _a = this.attributeConfigs; _i < _a.length; _i++) {
                                var attributeConfig = _a[_i];
                                //获取name值
                                formData[attributeConfig["name"]] = null;
                                //获取role配置
                                var role = {};
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
                                // 自定义校验规则
                                if (attributeConfig['validator']) {
                                    role['validator'] = attributeConfig['validator'];
                                }
                                //判断roles是否已经还有该条规则
                                if (!rules[attributeConfig["name"]]) {
                                    rules[attributeConfig["name"]] = [];
                                }
                                rules[attributeConfig["name"]].push(role);
                            }
                            return { formData: formData, rules: rules };
                        }
                        return null;
                    };
                    //编辑/添加标识
                    AddEditPage.ADD_IDENT = "add";
                    AddEditPage.EDIT_IDENT = "edit";
                    return AddEditPage;
                }(VueInit));
                page.AddEditPage = AddEditPage;
            })(page = ui.page || (ui.page = {}));
        })(ui = clouddo.ui || (clouddo.ui = {}));
    })(clouddo = com.clouddo || (com.clouddo = {}));
})(com || (com = {}));
