let pageModel : NewsList;

$(document).ready(function () {
    pageModel = new NewsList();
    pageModel.init();
});

/**
 * 新闻列表页面js
 */
namespace com.clouddo.news.ui.web {

    //声明VUE
    import RestUtil = com.clouddo.ui.util.RestUtil;
    import TimeUtil = com.clouddo.ui.util.TimeUtil;
    declare let Vue : any;
    declare let layer : any;
    //生成moduleName（在页面定义）
    declare let moduleName : string;
    //页面路径定义在页面
    declare let context : string;

    export class NewsList {

        //栏目vue
        private columnVue : any;

        //每次加载记录数
        private static limit : number = 20;

        /**
         * 新闻列表vue
         */
        private newsListVue : any;

        //构造方法
        constructor() {
            //初始化vue
            this.bindVue();
        }

        /**
         * 初始化页面
         */
        public init() : void {
            //加载栏目数据
            this.loadColumnData();
        }

        /**
         * 加载栏目数据
         */
        private loadColumnData() :  void {
            let pageObject = this;
            let parameters = {"moduleName": moduleName};
            RestUtil.postAjax("news/cloudColumn/list", parameters, success, null);


            function success(data) {
                //设置栏目vue数据
                let cloudColumnList = data["cloudColumnList"];
                //在每一个子栏目最前面加上全部，显示该栏目下所有新闻
                for(let column of cloudColumnList) {
                    let all = {subsectionId: "", subsectionName: "全部", columnId: column["columnId"]};
                    if (column["cloudSubsectionList"] == null) {
                        column["cloudSubsectionList"] = [all];
                    } else {
                        //在最开始位置添加 全部
                        column["cloudSubsectionList"].splice(0, 0, all);
                    }
                }
                pageObject.columnVue.columnList = cloudColumnList;
                //设置默认的新闻列表显示，显示第一个子栏目
                if (cloudColumnList.length >0 && cloudColumnList[0].cloudSubsectionList) {
                    let subsectionList = data["cloudColumnList"][0].cloudSubsectionList;
                    if (subsectionList && subsectionList.length > 0)
                    pageObject.columnVue.selectSubsection(subsectionList[0].columnId, subsectionList[0].subsectionId, subsectionList[0].subsectionName);
                }
            }
        }

        /**
         * 加载
         * @param {string} subsectionId
         * @param {number} pageNum
         */
        public loadnewsList(pageNum : number, sort? : {[index:string]: string}) {
            let pageModel = this;
            //加载数据
            //生成请求参数
            let parameters : any = {};
            parameters["limit"] = NewsList.limit;
            parameters["offset"] = (pageNum - 1) * pageNum + 1;
            parameters["subsectionId"] = this.newsListVue.subsectionId;
            parameters["columnId"] = this.newsListVue.columnId;
            //设置默认排序
            if (sort) {
                let sortNameList : string = "";
                let sortOrderList : string = "";
                for (let key in sort) {
                    sortNameList += key + ",";
                    sortOrderList+= sort[key] + ",";
                }
                if (sortNameList.length > 0) {
                    sortNameList = sortNameList.substring(0, sortNameList.length - 1);
                }
                if (sortOrderList.length > 0) {
                    sortOrderList = sortOrderList.substring(0, sortOrderList.length - 1);
                }
                parameters["sortName"] = sortNameList;
                parameters["sortOrder"] = sortOrderList;
            }

            RestUtil.postAjax("news/cloudNews/list", parameters, success, null);
            function success(data) {
                pageModel.newsListVue.newsList = data;

            }
        }

        /**
         * 绑定vue
         */
        private bindVue() : void {
            let pageModel = this;
            this.columnVue = new Vue({
                el: "#column_id",
                data: {
                    //栏目列表
                    columnList : []
                },
                methods: {
                    //选中子栏目
                    selectSubsection : function (columnId, subsectionId, subsectionName) {
                        pageModel.newsListVue.title = subsectionName;
                        pageModel.newsListVue.columnId = columnId;
                        pageModel.newsListVue.subsectionId = subsectionId;
                        //设置排序信息
                        let sort : {[index:string]: string} = {createTime: "desc"};
                        pageModel.loadnewsList(1, sort);
                    }
                }
            });
            //初始化新闻列表vue
            this.newsListVue = new Vue({
                el: "#newsList",
                data: {
                    //标题
                    title: "",
                    //新闻列表
                    newsList: [],
                    //栏目
                    columnId: "",
                    //子栏目ID
                    subsectionId: ""
                },
                methods: {
                    //显示添加新闻页面
                    showNewsAdd : function () {
                        layer.open({
                            type: 2,
                            area: ['60%', '80%'],
                            fixed: false, //不固定
                            maxmin: true,
                            skin: 'layui-layer-rim', //加上边框
                            content: context + "news/newsAdd?moduleName=" + moduleName
                        });
                    },
                    //显示热门的
                    showHot: function () {
                        //设置排序信息
                        let sort : {[index:string]: string} = {commentNum: "desc", createTime: "desc"};
                        //加载数据
                        pageModel.loadnewsList(1, sort);
                    },
                    showNewest: function () {
                        let sort : {[index:string]: string} = {createTime: "desc"};
                        pageModel.loadnewsList(1, sort);
                    }

                },
                filters: {
                    //格式化图片显示路径
                    formatPicUrl : function (value) {
                        return RestUtil.getShowImageUrl(value);
                    },
                    //格式化时间
                    formatCreateTime: function (value) {
                        return TimeUtil.contrastTime(value);
                    }
                }
            });
        }
    }
}

import NewsList = com.clouddo.news.ui.web.NewsList;
