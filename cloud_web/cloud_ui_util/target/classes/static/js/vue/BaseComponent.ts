/**
 * vue组件类
 */
namespace com.clouddo.ui.vue {
    export interface BaseComponent {

        /**
         * 组件名称
         */
        name: string

        /**
         * 向子组件传值
         * @returns {any}
         */
        props (): any

        /**
         * 创建组件
         */
        create (): void

        /**
         * 组件
         * @returns {{[p: string]: any}}
         */
        components (): {[index: string]: any}

        filters (): {[index: string]: Function}

        /**
         * 组件方法
         */
        methods (): {[index: string]: Function}

        /**
         * 侦听器
         */
        watch (): {[index: string]: Function}

        /**
         * 计算属性
         */
        computed (): {[index: string]: Function}

        /**
         * 组件模板
         */
        template (): string

        /**
         * 组件数据
         * @returns {Function}
         */
        data (): {[index: string]: any}

        /**
         * 生命周期钩子：创建前状态 el 和 data 并未初始化
         */
        beforeCreate (): void

        /**
         * 生命周期钩子：创建完毕状态 完成了 data 数据的初始化，el没有
         */
        created (): void

        /**
         * 生命周期钩子 完成了 el 和 data 初始化
         */
        beforeMount (): void

        /**
         * 生命周期钩子 完成挂载
         */
        mounted (): void

        /**
         * 生命周期钩子 数据修改dom更新前
         */
        beforeUpdate (): void

        /**
         * 生命周期钩子 数据修改dom更新后
         */
        updated (): void

        /**
         * 生命周期钩子 vue实例销毁前
         */
        beforeDestroy (): void

        /**
         * 生命周期钩子 vue实例销毁后
         */
        destroyed (): void


        /**
         * 初始化方法
         * @returns {{[p: string]: any}}
         */
        init (): {[index: string]: any}
    }
}