var com;
(function (com) {
    var clouddo;
    (function (clouddo) {
        var ui;
        (function (ui) {
            var CKeditor;
            (function (CKeditor_1) {
                var RestUtil = com.clouddo.ui.util.RestUtil;
                var CKeditor = /** @class */ (function () {
                    /**
                     * 构造方法
                     * @param {string} elementId
                     */
                    function CKeditor(elementId) {
                        this.elementId = elementId;
                    }
                    /**
                     * 获取ckeditor数据
                     * @returns {string}
                     */
                    CKeditor.prototype.getData = function () {
                        return this.editor.getData();
                    };
                    /**
                     * 设置ckeditor数据
                     * @param {string} data
                     */
                    CKeditor.prototype.setData = function (data) {
                        this.editor.setData(data);
                    };
                    /**
                     * 将图片地址转为thymeleaf标签地址
                     */
                    CKeditor.prototype.convertImageUrl = function (data) {
                        return this.convertUrl(data, "img");
                    };
                    /**
                     * 将指定类型dom的url转为thymeleaf标签地址
                     * @param {string} domTypes
                     * @returns {string}
                     */
                    CKeditor.prototype.convertUrl = function (data) {
                        var domTypes = [];
                        for (var _i = 1; _i < arguments.length; _i++) {
                            domTypes[_i - 1] = arguments[_i];
                        }
                        if (!data || data.trim() == "") {
                            return "";
                        }
                        //生成div
                        var divObject = document.createElement("div");
                        //设置内容
                        divObject.innerHTML = data;
                        //遍历dom
                        for (var _a = 0, domTypes_1 = domTypes; _a < domTypes_1.length; _a++) {
                            var domType = domTypes_1[_a];
                            //查找相应的元素
                            var doms = $(divObject).find(domType);
                            //遍历doms
                            for (var _b = 0, doms_1 = doms; _b < doms_1.length; _b++) {
                                var dom = doms_1[_b];
                                //处理img标签
                                if (domType.toUpperCase() == "IMG") {
                                    var src = $(dom).attr("src");
                                    //获取图片显示ID
                                    if (src) {
                                        var index = src.indexOf("?");
                                        src = src.substring(0, index);
                                        var strs = src.split("/");
                                        var id = strs[strs.length - 1];
                                        $(dom).attr("src", RestUtil.getShowImageUrl(id));
                                    }
                                }
                            }
                        }
                        return divObject.innerHTML;
                    };
                    /**
                     * 创建
                     */
                    CKeditor.prototype.create = function () {
                        var _this = this;
                        ClassicEditor.create(document.querySelector("#" + this.elementId), {
                            language: "zh-cn"
                        })
                            .then(function (editor) {
                            _this.editor = editor;
                            //加载自定义适配器
                            editor.plugins.get('FileRepository').createUploadAdapter = function (loader) {
                                return new UploadAdapter(loader);
                            };
                        })
                            .catch(function (error) {
                            console.error(error);
                        });
                    };
                    return CKeditor;
                }());
                CKeditor_1.CKeditor = CKeditor;
                /**
                 * 自定义上传组件
                 */
                var UploadAdapter = /** @class */ (function () {
                    function UploadAdapter(loader) {
                        this.loader = loader;
                    }
                    /**
                     * 上传组件
                     */
                    UploadAdapter.prototype.upload = function () {
                        var _this = this;
                        return new Promise(function (resolve, reject) {
                            //执行上传
                            RestUtil.upload(_this.loader.file, {}, success, error);
                            function success(data) {
                                resolve({
                                    default: RestUtil.getShowImageUrl(data.fileId)
                                });
                            }
                            function error(data) {
                                reject(data);
                            }
                        });
                    };
                    UploadAdapter.prototype.abort = function () {
                    };
                    return UploadAdapter;
                }());
                CKeditor_1.UploadAdapter = UploadAdapter;
            })(CKeditor = ui.CKeditor || (ui.CKeditor = {}));
        })(ui = clouddo.ui || (clouddo.ui = {}));
    })(clouddo = com.clouddo || (com.clouddo = {}));
})(com || (com = {}));
