package com.yi.demo3.flow;

import com.yi.demo3.flow.entity.FlowBean;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 流量数据读取 拆分 k v
 *
 * @author huangwenyi
 * @date 2019-8-22
 */
public class FlowMapper extends Mapper<LongWritable, Text, Text, FlowBean> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] split = value.toString().split("\t");
        String phoneNum = split[1];
        String upFlow = split[6];
        String downFlow = split[7];
        String upCountFlow = split[8];
        String downCountFlow = split[9];

        FlowBean flowBean = new FlowBean(
                Integer.parseInt(upFlow),
                Integer.parseInt(downFlow),
                Integer.parseInt(upCountFlow),
                Integer.parseInt(downCountFlow)
        );

        // 把每个手机号码的流量处理之后发送到reduce
        context.write(new Text(phoneNum), flowBean);
    }
}
