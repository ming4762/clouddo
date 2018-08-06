
namespace com.clouddo.ui.CKeditor {

    import RestUtil = com.clouddo.ui.util.RestUtil;
    declare let Promise : any;
    declare let $ : any;
    //CKeditor
    declare let ClassicEditor : any;

    export class CKeditor {
        //绑定的domID
        private elementId : string;
        //editor对象
        private editor : any;

        /**
         * 构造方法
         * @param {string} elementId
         */
        constructor(elementId: string) {
            this.elementId = elementId;
        }

        /**
         * 获取ckeditor数据
         * @returns {string}
         */
        public getData() : string {
            return this.editor.getData();
        }

        /**
         * 将图片地址转为thymeleaf标签地址
         */
        public convertImageUrl(data: string, ) : string {
            return this.convertUrl(data, "img");
        }

        /**
         * 将指定类型dom的url转为thymeleaf标签地址
         * @param {string} domTypes
         * @returns {string}
         */
        public convertUrl(data: string, ...domTypes : string[]) : string {
            if (!data || data.trim() == "") {
                return "";
            }
            //生成div
            var divObject = document.createElement("div");
            //设置内容
            divObject.innerHTML = data;
            //遍历dom
            for (let domType of domTypes) {
                //查找相应的元素
                let doms = $(divObject).find(domType);
                //遍历doms
                for (let dom of doms) {
                    //处理img标签
                    if (domType.toUpperCase() == "IMG") {
                        let src : string = $(dom).attr("src");
                        //获取图片显示ID
                        if (src) {
                            let index : number = src.indexOf("?");
                            src = src.substring(0, index);
                            let strs : Array<string> = src.split("/")
                            let id = strs[strs.length - 1];
                            $(dom).attr("src", RestUtil.getShowImageUrl(id));
                        }
                    }
                }
            }
            return divObject.innerHTML;
        }

        /**
         * 创建
         */
        public create() : void {
            ClassicEditor.create(document.querySelector( "#" + this.elementId),{
                language:"zh-cn"
            })
                .then(editor => {
                    this.editor = editor;
                    //加载自定义适配器
                    editor.plugins.get('FileRepository').createUploadAdapter = (loader)=>{
                        return new UploadAdapter(loader);
                    };
                })
                .catch(error => {
                    console.error( error );
                });
        }
    }


    /**
     * 自定义上传组件
     */
    export class UploadAdapter{
        //文件加载器
        private loader : any;

        constructor(loader) {
            this.loader = loader;
        }

        /**
         * 上传组件
         */
        public upload() {
            return new Promise((resolve, reject) => {
                //执行上传
                RestUtil.upload(this.loader.file, {}, success, error);
                function success(data) {
                    resolve({
                        default: RestUtil.getShowImageUrl(data.fileId)
                    });
                }

                function error(data) {
                    reject(data);
                }
            });
        }

        public abort() {

        }
    }
}