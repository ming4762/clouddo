
let newsModel;

$(document).ready(function () {
    newsModel = new NewsAdd();
    newsModel.init();

    console.log(parent.frames.length + "====" + window.frames.length);
});

/**
 * 表单添加页面js
 * @author zhongming
 */
namespace com.clouddo.news.ui.web {
    import CKeditor = com.clouddo.ui.CKeditor.CKeditor;
    import RestUtil = com.clouddo.ui.util.RestUtil;

    //声明vue
    declare let Vue : any;
    //模块名（在页面声明）
    declare let moduleName : any;
    //生成layui
    declare let layui : any;
    declare let layer : any;

    export class NewsAdd {

        /**
         * 表单vue
         */
        private formVue : any;

        /**
         * ckeditor实例
         */
        private ckeditor : CKeditor;

        /**
         * 使用map存储副标题
         * key：columnId
         * value: subsectionList
         * @type {{}}
         */
        private columnMap : {[index:string]: any} = {};

        //layui form实例
        private formLayui : any;

        public init() : void {
            //初始化vue
            this.initVue();
            //初始化ckeditor
            this.ckeditor = new CKeditor("editor");
            this.ckeditor.create();
            //加载栏目数据，渲染select
            this.loadColumnData();
        }

        /**
         * 加载栏目数据
         */
        private loadColumnData() : void {
            let pageModel = this;

            let parameters = {"moduleName": moduleName};
            RestUtil.postAjax("news/cloudColumn/list", parameters, success, null);
            //加载成功数据
            function success(data) {
                //设置栏目数据
                if (data) {
                    let columnList = data["cloudColumnList"];
                    //将数据结果放入map中，翻遍以后通过columnId获取副栏目
                    for (let column of columnList) {
                        pageModel.columnMap[column.columnId] = column.cloudSubsectionList;
                    }
                    pageModel.formVue.setColumnData(columnList);
                }
            }
        }

        //初始化vue
        private initVue() : void {
            let pageModel = this;
            //初始化vue
            this.formVue = new Vue({
                el: "#formVue",
                data: {
                    //栏目列表
                    columnList: [],
                    //副栏目列表
                    subsectionList: [],

                    imageUrl: "",
                    //新闻实体
                    news: {
                        columnId: "",
                        subsectionId: "",
                        title: "",
                        subtitle: "",
                        titlePic: "",
                    },
                    rules : {
                        title :[
                            {required: true, message: '请输入标题', trigger: 'blur'}
                        ],
                        columnId: [
                            {required: true, message: '请选择栏目', trigger: 'change'}
                        ],
                        titlePic: [
                            {required: true, message: '请选择图片', trigger: 'change'}
                        ],
                        content: [
                            {required: false, message: '请填写正文', trigger: 'change'}
                        ],
                        subtitle: [
                            {required: false, message: '请输入副标题', trigger: 'blur'}
                        ],
                        subsectionId: [
                            {required: false, message: '请输入副栏目', trigger: 'change'}
                        ]
                    }
                },
                methods: {
                    //设置栏目数据
                    setColumnData: function (data) {
                        this.columnList = data;
                        if (data && data[0]) {
                            this.setSubsectionData(data[0].cloudSubsectionList);

                        }
                    },
                    //设置副栏目数据
                    setSubsectionData: function(data) {
                        this.subsectionList = data;
                        this.news.subsectionId = "";
                    },
                    //选中栏目
                    chouseColumn: function (columnId) {
                        this.setSubsectionData(pageModel.columnMap[columnId]);
                    },
                    //
                    uploadImage : function (file) {
                        RestUtil.upload(file, {}, success, null);
                        function success(data) {
                            if (data) {
                                //上传成功回调
                                pageModel.formVue.imageUrl = RestUtil.getShowImageUrl(data.fileId);
                                pageModel.formVue.news.titlePic = data.fileId;
                            }
                        }
                        //不自动上传
                        return false;
                    },
                    //提交数据
                    submit: function (formName) {
                        //表单校验
                        this.$refs[formName].validate((valid) => {
                            if (valid) {
                                console.log("提交表单")
                                //设置内容
                                this.news["content"] = pageModel.ckeditor.getData();
                                RestUtil.postAjax("news/cloudNews/insert", this.news, success, null);
                            } else {
                                return false;
                            }
                        });
                        function success(data) {
                            console.log(data);
                            //判断当前窗口是否在iframe中
                            if (window.frames.length != parent.frames.length) {
                                //在iframe中,关闭窗口
                                let index = parent.layer.getFrameIndex(window.name);
                                parent.layer.close(index);
                                let sort : {[index:string]: string} = {createTime: "desc"};
                                parent.pageModel.loadnewsList(1, sort);
                            } else {
                                layer.alert('添加成功！', {icon: 1});
                                pageModel.formVue.$refs[formName].resetFields();
                                pageModel.formVue.imageUrl = "";
                                // pageModel.ckeditor.
                            }
                        }

                    }
                }
            });
        }

    }
}


import NewsAdd = com.clouddo.news.ui.web.NewsAdd;