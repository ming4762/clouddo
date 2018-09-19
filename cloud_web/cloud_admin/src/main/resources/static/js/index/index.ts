let indexObject : Index;

$("document").ready(function () {
    //创建页面JS对象
    indexObject = new Index();
    indexObject.init();
    // mainObject.init();
});

namespace com.clouddo.admin {

    import AuthRestUtil = com.clouddo.ui.util.AuthRestUtil;
    import RestUtil = com.clouddo.ui.util.RestUtil;
    declare let $ : any;
    declare let Vue : any;
    declare let layer : any;



    export class Index {


        //页面头部vue
        private topVue : any;
        //左侧vue
        private leftVue : any;
        //右上角按钮vue
        private topButtonVue : any;
        // 底部VUE
        private footerVue: any
        //使用map存放顶级菜单，便于处理
        private topMenuMap : {[index:string]: any} = {};

        /**
         * 初始化页面
         */
        public init(): void{
            //初始化vue
            this.initVue();
            // this.loadPageData() ;
        }
        /**
         * 初始化vue
         */
        private initVue() : void {
            let mainObject = this
            this.footerVue = new Vue({
                el: '#footerVue',
                data: {
                    // 本地化配置信息
                    localConfig: RestUtil.getLocalConfig()
                }
            })
            this.topButtonVue = new Vue({
                el: "#topButtonDiv",
                data: {

                },
                methods: {
                    //登出操作
                    logout: function (event) {
                        layer.confirm("您确定要退出吗？",{
                            btn: ['确定','取消'],
                            icon: 0,
                        }, function () {
                            AuthRestUtil.logout();
                        }, function (index) {
                            layer.close(index);
                        });
                    }
                }
            });
            //初始化顶部VUE
            // this.topVue = new Vue({
            //     el : "#topVue",
            //     data : {
            //         //顶级菜单
            //         topMenuList : [],
            //         //用户名
            //         username : null
            //     },
            //     methods : {
            //         //点击菜单切换二级菜单
            //         clickTop : function (menuId) {
            //             let topMenu= mainObject.topMenuMap[menuId];
            //             if (topMenu) {
            //                 mainObject.leftVue.changeLeftMenu(topMenu.children);
            //             }
            //         }
            //     },
            //     filters : {
            //         //格式化menuId
            //         formatMenuId : function (value) {
            //             return "navTabsItem-" + value;
            //         }
            //     }
            // });
            //
            //初始化左侧vue
            // this.leftVue = new Vue({
            //     el : "#leftVueElement",
            //     data : {
            //         secondMenuList : []
            //     },
            //     methods : {
            //         //改变左侧菜单
            //         changeLeftMenu : function (data) {
            //             this.secondMenuList = data;
            //             // this.$nextTick(function(){
            //             //     jQuery.getScript(context + "js/hplus.js");
            //             //     contabs();
            //             // });
            //         }
            //     }
            // });
        }
    }
}

import Index = com.clouddo.admin.Index;