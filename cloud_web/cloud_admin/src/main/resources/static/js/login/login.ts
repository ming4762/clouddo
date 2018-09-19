


$(document).ready(function () {
    let loginObject = new Login();
    loginObject.init();
});

namespace com.clouddo.admin {

    import RestUtil = com.clouddo.ui.util.RestUtil;
    import AuthRestUtil = com.clouddo.ui.util.AuthRestUtil;
    declare let Vue : any;
    declare let context : string;

    export class Login {

        //表单vue
        private formVue : any;

        //初始化页面
        public init() : void {
            this.initVue();
            this.loadLocalConfig()
        }

        /**
         * 加载本地化配置信息
         */
        private loadLocalConfig (): void {
            RestUtil.loadLocalConfig(() => {
                let localConfig = RestUtil.getLocalConfig()
                this.formVue.localConfig = localConfig
            })
        }

        //执行登录
        private login() : void {
            AuthRestUtil.login(this.formVue.username, this.formVue.password, success, error);
            let loginObject = this;
            //登录成功
            function success(data) {
                if (data) {
                    //跳转到主页
                    parent.location.href = context + "web/index?Authorization=" + data.token;
                }
            }
            //登录失败处理函数
            function error(data) {
                console.log(data);
                if (data) {
                    loginObject.formVue.errors.push(data.message);
                } else {
                    loginObject.formVue.errors.push("发生未知错误，请联系开发人员" );
                }
            }
        }

        //初始化VUE
        private initVue() : void {
            let loginObject = this;
            this.formVue = new Vue({
                el : "#loginPageVue",
                data : {
                    errors : [],
                    username : null,
                    password : null,
                    // 开发人员信息
                    localConfig: {
                        development: {},
                        system: {}
                    }
                },
                methods : {
                    doLogin : function (event) {
                        this.errors = [];
                        //验证表单
                        if(!this.username) {
                            this.errors.push("请输入您的用户名");
                        }
                        if (!this.password) {
                            this.errors.push("请输入您的密码");
                        }
                        if (this.errors.length == 0) {
                            //执行登录
                            loginObject.login();
                        }
                    }
                }
            });
        }
    }
}


import Login = com.clouddo.admin.Login;