let mainObject : Main;

$("document").ready(function () {
    //创建页面JS对象
    // mainObject = new Main();
    // mainObject.init();
});

namespace com.clouddo.admin {

    declare let $ : any;
    declare let Vue : any;

    import RestUtil = com.clouddo.ui.util.RestUtil;

    export class Main {


        //页面头部vue
        private topVue : any;
        //左侧vue
        private leftVue : any;
        //使用map存放顶级菜单，便于处理
        private topMenuMap : {[index:string]: any} = {};

        /**
         * 初始化页面
         */
        public init(): void{
            //初始化vue
            this.initVue();
            this.loadPageData() ;
        }

        //加载页面数据
        private loadPageData() {
            let mainObject = this;
            RestUtil.postAjax("system/index", null, success, null);
            //页面加载完成后执行
            function success(data) {
                console.log(data);
                if (data) {
                    mainObject.topVue.topMenuList = data.menus;
                    // mainObject.topVue.username = data.username;
                    // if(data.menus && data.menus[0]) {
                    //     mainObject.leftVue.changeLeftMenu(data.menus[0].children);
                    // }
                    //转换数据，将顶级菜单放入map中，便于处理
                    if(data.menus && data.menus.length > 0) {
                        for (let menu of data.menus) {
                            mainObject.topMenuMap[menu.id] = menu;
                        }
                    }
                }
            }
        }

        /**
         * 初始化vue
         */
        private initVue() : void {
            let mainObject = this;
            //初始化顶部VUE
            this.topVue = new Vue({
                el : "#topVue",
                data : {
                    //顶级菜单
                    topMenuList : [],
                    //用户名
                    username : null
                },
                methods : {
                    //点击菜单切换二级菜单
                    clickTop : function (menuId) {
                        let topMenu= mainObject.topMenuMap[menuId];
                        if (topMenu) {
                            mainObject.leftVue.changeLeftMenu(topMenu.children);
                        }
                    }
                },
                filters : {
                    //格式化menuId
                    formatMenuId : function (value) {
                        return "navTabsItem-" + value;
                    }
                }
            });
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

import Main = com.clouddo.admin.Main;