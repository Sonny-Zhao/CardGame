package com.tianyu.cardgame.model;
/**
 * All rights Reserved, Designed By Vongvia
 * @Title:  得到随机数的函数，且6个不重复
 * @author:	Vongvia  欢迎各位童鞋来交流 ：441256563
 * @date:	2015.11.17
 * @version	V1.0
 */
import java.util.Random;

public class GetIndex {
    //生成随机数
    public static int[] Getnum()
    {
        //图片的随机索引
        int[] temp = new int[12];
        int[] temp_ = new int[6];
        //图片索引的随机索引
        int[] index = new int[6];
        int temp1;
        Random r = new Random();
        for (int i = 0; i < 6; i++)
        {
            temp1 = r.nextInt(36);//0-35
            temp[i] = GetNumber(temp, i, 1, 36, temp1, r);
        }
        //保存前面的6个随机数
        for (int i = 0; i < 6; i++)
        {
            temp_[i] = temp[i];
        }
        //产生0-5的不重复随机数
        for (int i = 0; i < 6; i++)
        {
            //随机赋值
            index[i] = r.nextInt(6);
            //检查有无重复元素
            for (int j = i; j < 6; j++)
            {
                if (index[j] == index[i])
                {
                    index[j] = r.nextInt(6);
                }
            }
        }
        //给剩下的6个元素赋值

        int[] num = new int[6];
        for (int i = 0; i < num.length; i++)
        {
            num[i] = i + 1;
        }
        Random w= new Random();
        //存放最终结果
        int[] result = new int[6];
        int max = 6;//设置随机数最大值
        for (int j = 0; j < result.length; j++)
        {

            int nindex = w.nextInt(max);
            //取出该索引位置所存的数
            result[j] = num[nindex]-1;
            //用最后一个数替换掉已被放入result中的数

            //这样num数组中从0到max-1又都是未被存放入result而且不重复的数了
            num[nindex] = num[max - 1];
            //随机产生的数组索引最大值减一
            max--;
        }

        for (int i = 6; i < 12; i++)
        {
            //随机赋值
            temp[i] = temp_[result[i-6]];
        }
        return temp;
    }
    static int GetNumber(int[] a, int index, int minValue, int maxValue, int temp, Random r)
    {
        for (int i = 0; i < index; i++)
        {
            if (a[i] == temp)
            {
                int newTemp = r.nextInt(maxValue)+minValue;
                a[index] = newTemp;
                return GetNumber(a, index, minValue, maxValue, newTemp, r);
            }
        }
        return temp;
    }


}
