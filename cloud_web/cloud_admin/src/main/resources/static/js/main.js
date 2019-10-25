var mainObject;
$("document").ready(function () {
    //创建页面JS对象
    mainObject = new Main();
    mainObject.init();
});
var com;
(function (com) {
    var clouddo;
    (function (clouddo) {
        var admin;
        (function (admin) {
            var RestUtil = com.clouddo.ui.util.RestUtil;
            var Main = /** @class */ (function () {
                function Main() {
                    //使用map存放顶级菜单，便于处理
                    this.topMenuMap = {};
                }
                /**
                 * 初始化页面
                 */
                Main.prototype.init = function () {
                    //初始化vue
                    this.initVue();
                    this.loadPageData();
                };
                //加载页面数据
                Main.prototype.loadPageData = function () {
                    var mainObject = this;
                    RestUtil.postAjax("system/index", null, success, null);
                    //页面加载完成后执行
                    function success(data) {
                        console.log(data);
                        if (data) {
                            mainObject.topVue.topMenuList = data.menus;
                            mainObject.topVue.username = data.username;
                            if (data.menus && data.menus[0]) {
                                mainObject.leftVue.changeLeftMenu(data.menus[0].children);
                            }
                            //转换数据，将顶级菜单放入map中，便于处理
                            if (data.menus && data.menus.length > 0) {
                                for (var _i = 0, _a = data.menus; _i < _a.length; _i++) {
                                    var menu = _a[_i];
                                    mainObject.topMenuMap[menu.id] = menu;
                                }
                            }
                        }
                    }
                };
                /**
                 * 初始化vue
                 */
                Main.prototype.initVue = function () {
                    var mainObject = this;
                    //初始化顶部VUE
                    this.topVue = new Vue({
                        el: "#vueTop",
                        data: {
                            //顶级菜单
                            topMenuList: [],
                            //用户名
                            username: null
                        },
                        methods: {
                            //点击菜单切换二级菜单
                            clickTop: function (menuId) {
                                var topMenu = mainObject.topMenuMap[menuId];
                                if (topMenu) {
                                    mainObject.leftVue.changeLeftMenu(topMenu.children);
                                }
                            }
                        }
                    });
                    //初始化左侧vue
                    this.leftVue = new Vue({
                        el: "#leftVueElement",
                        data: {
                            secondMenuList: []
                        },
                        methods: {
                            //改变左侧菜单
                            changeLeftMenu: function (data) {
                                this.secondMenuList = data;
                                // this.$nextTick(function(){
                                //     jQuery.getScript(context + "js/hplus.js");
                                //     contabs();
                                // });
                            }
                        }
                    });
                };
                return Main;
            }());
            admin.Main = Main;
        })(admin = clouddo.admin || (clouddo.admin = {}));
    })(clouddo = com.clouddo || (com.clouddo = {}));
})(com || (com = {}));
var Main = com.clouddo.admin.Main;
