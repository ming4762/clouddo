
namespace com.clouddo.ui.vue {
    // 声明vue
    declare let Vue
    export abstract class BaseComponentImpl implements BaseComponent {


        /**
         * 组件名称
         */
        name: string


        /**
         * 构造函数，创造组件
         * @param {string} name
         */
        constructor (name: string, constructData?: any) {
            this.name = name
        }

        /**
         * 组件
         * @returns {{[p: string]: any}}
         */
        components(): { [index: string]: any} {
            return {}
        }

        /**
         * 创建组件
         */
        create(): void {
            Vue.component(this.name, this.init())
        }

        /**
         * 组件模板
         */
        abstract template (): string {
            return null
        }

        /**
         * 组件数据
         * @returns {Function}
         */
        abstract data(): {[index: string]: any} {
            return null
        }

        /**
         * 初始化函数
         * @returns {{[p: string]: any}}
         */
        init(): { [p: string]: any } {
            let initData: {[index: string]: any} = {
                template: this.template(),
                filters: this.filters(),
                components: this.components(),
                data: this.data,
                props: this.props(),
                methods: this.methods(),
                watch: this.watch(),
                computed: this.computed(),
                beforeCreate: this.beforeCreate,
                created: this.created,
                beforeMount: this.beforeMount,
                mounted: this.mounted,
                beforeUpdate: this.beforeUpdate,
                updated: this.updated,
                beforeDestroy: this.beforeDestroy,
                destroyed: this.destroyed
            }
            return initData
        }

        /**
         * 组件方法
         */
        methods(): { [index: string]: Function} {
            return null
        }

        /**
         * 向子组件传值
         * @returns {any}
         */
        props() {
            return null
        }

        /**
         * 过滤器
         * @returns {{[p: string]: Function}}
         */
        filters(): { [index: string]: Function; } {
            return null
        }
        
        /**
         * 侦听器
         */
        watch(): { [index: string]: Function } {
            return null
        }

        /**
         * 计算属性
         */
        computed(): { [index: string]: Function } {
            return null
        }

        beforeCreate(): void {
        }

        created(): void {
        }

        beforeMount(): void {
        }

        mounted(): void {
        }

        beforeUpdate(): void {
        }

        updated(): void {
        }

        beforeDestroy(): void {
        }

        destroyed(): void {
        }

    }
}