var com;
(function (com) {
    var clouddo;
    (function (clouddo) {
        var ui;
        (function (ui) {
            var util;
            (function (util) {
                /**
                 * 本地存储工具类
                 */
                var StorageUtil = /** @class */ (function () {
                    function StorageUtil() {
                    }
                    /**
                     * 存储
                     * @param {string} key key
                     * @param data 数据
                     * @param {string} type 类型
                     */
                    StorageUtil.set = function (key, data, type) {
                        var $data = {
                            datetime: new Date().getTime(),
                            data: data,
                            dataType: typeof data,
                            type: type
                        };
                        this.getStorage(type).setItem(key, JSON.stringify($data));
                    };
                    /**
                     * 存储数据到sessionStorage
                     * @param {string} key
                     * @param data
                     */
                    StorageUtil.setToSession = function (key, data) {
                        this.set(key, data, this.TYPE_SESSION);
                    };
                    /**
                     * 读取
                     * @param {string} key
                     * @param {boolean} debug
                     * @returns {any}
                     */
                    StorageUtil.get = function (key, debug) {
                        var data = this.getStorage().getItem(key);
                        if (!data) {
                            data = this.getStorage(this.TYPE_SESSION).getItem(key);
                        }
                        if (!data) {
                            return null;
                        }
                        data = JSON.parse(data);
                        if (debug) {
                            console.log(data);
                        }
                        return data['data'];
                    };
                    /**
                     * 获取存储器
                     * @param {string} type 类型
                     * @returns {any}
                     */
                    StorageUtil.getStorage = function (type) {
                        if (type === StorageUtil.TYPE_SESSION) {
                            return sessionStorage;
                        }
                        return localStorage;
                    };
                    StorageUtil.TYPE_LOCAL = 'localStorage';
                    StorageUtil.TYPE_SESSION = 'sessionStorage';
                    return StorageUtil;
                }());
                util.StorageUtil = StorageUtil;
            })(util = ui.util || (ui.util = {}));
        })(ui = clouddo.ui || (clouddo.ui = {}));
    })(clouddo = com.clouddo || (com.clouddo = {}));
})(com || (com = {}));
