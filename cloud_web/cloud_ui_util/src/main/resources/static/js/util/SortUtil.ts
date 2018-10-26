/**
 * 排序工具类
 */
namespace com.clouddo.ui.util {
    export class SortUtil {

        public static SORT_ARITHMETIC = {
            SHELL: 'shell'
        }

        /**
         * 排序算法
         * @author zhongming
         * TODO 待完善 降序
         * TODO 待完善 其他排序算法
         * @param array 要排序的数组
         * @param isDesc 是否是降序
         * @param arithmetic 排序算法
         */
        public static sort (array: number[], isDesc?: boolean, arithmetic?: string): number[] {
            let result: number[] = [];
            switch (arithmetic) {
                case '':
                    break;
                default:
                    result = this.shellSort(array)
            }
            return result
        }

        /**
         * 希尔排序
         * @author zhongming
         * @param array 要排序的数组
         */
        private static shellSort (array: number[]): number[] {
            let gap = Math.floor(array.length/2);
            while (gap >= 1) {
                for(let i = gap; i<array.length; i++){
                    let temp = array[i];
                    let j;
                    for(j=i-gap; j>=0&&temp<array[j]; j-=gap){
                        array[j+gap] = array[j];
                    }
                    array[j+gap] = temp;
                }
                gap = Math.floor(gap/2);
            }
            return array
        }
    }
}