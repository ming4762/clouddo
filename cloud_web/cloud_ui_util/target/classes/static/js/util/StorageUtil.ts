
namespace com.clouddo.ui.util {
    /**
     * 本地存储工具类
     */
    export class StorageUtil {
        public static TYPE_LOCAL = 'localStorage'
        public static TYPE_SESSION = 'sessionStorage'

        /**
         * 存储
         * @param {string} key key
         * @param data 数据
         * @param {string} type 类型
         */
        public static set (key: string, data: any, type?: string) {
            let $data: {[index:string]: any} = {
                datetime: new Date().getTime(),
                data: data,
                dataType: typeof data,
                type: type
            }
            this.getStorage(type).setItem(key, JSON.stringify($data))
        }

        /**
         * 存储数据到sessionStorage
         * @param {string} key
         * @param data
         */
        public static setToSession (key: string, data: any) {
            this.set(key, data, this.TYPE_SESSION)
        }

        /**
         * 读取
         * @param {string} key
         * @param {boolean} debug
         * @returns {any}
         */
        public static get (key: string, debug?: boolean) {
            let data: any = this.getStorage().getItem(key)
            if (!data) {
                data = this.getStorage(this.TYPE_SESSION).getItem(key)
            }
            if (!data) {
                return null
            }
            data = JSON.parse(data)
            if (debug) {
                console.log(data)
            }

            return data['data']
        }

        /**
         * 获取存储器
         * @param {string} type 类型
         * @returns {any}
         */
        public static getStorage (type?: string): any {
            if (type === StorageUtil.TYPE_SESSION) {
                return sessionStorage
            }
            return localStorage
        }
    }
}