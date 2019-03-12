package edu.ahpu.boke.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;

import edu.ahpu.boke.dao.VideoDao;
import edu.ahpu.boke.domain.Video;

public class VideoConverter {
    private static boolean isRunning = false; // 标识转码线程是否正在运行
    private boolean stopFlag = false; // 控制线程结束的标识
    private static Queue<Video> queue; // 待转码视频队列

    private VideoDao videoDao;// 此处的dao对象由Service层的类注入

    // 单例
    private static VideoConverter instance;

    // 以静态语句块初始化单例和队列
    static {
        instance = new VideoConverter();
        queue = new LinkedList<Video>();
    }

    private VideoConverter() {// 私有构造方法
    }

    // 获取单例
    public static VideoConverter getInstance() {
        return instance;
    }

    public VideoDao getVideoDao() {
        return videoDao;
    }

    public void setVideoDao(VideoDao videoDao) {
        this.videoDao = videoDao;
    }

    // 添加视频到队列
    public void add(Video v) {
        queue.offer(v);
    }

    // 停止转码线程
    public void stopConvertJob() {
        stopFlag = true;
    }

    // 开始转码
    public void startConvertJob() {
        if (isRunning) { // 若已开启
            return;
        }
        new Thread() { // 构造新线程对象
            @Override
            public void run() {
                // 以stopFlag标识控制循环
                while (!stopFlag) {
                    // 取出队列中的视频
                    Video v = queue.peek();
                    if (v != null) {
                        convert(v); // 转码
                    }
                    try {
                        sleep(1000); // 休眠1秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();// 启动新线程对象
        isRunning = true; // 更新运行标识
    }

    // 转avi：mencoder in.rmvb -oac mp3lame -lameopts preset=64 -ovc xvid -xvidencopts bitrate=600 -of avi -o out.avi
    // 转flv：ffmpeg -i in.avi -ab 128 -acodec libmp3lame -ac 1 -ar 22050 -r 29.97 -qscale 6 -y out.flv
    // 截图： ffmpeg -i in.flv -y -f image2 -ss 8 -t 0.001 -s 120x90 out.jpg
    

    // 对视频进行转码和截图
    private void convert(Video v) {
        String filename = v.getServerFileName();
        long duration = v.getDuration();
        // 得到待转码视频的完整路径
        String oldFileFullName = Const.UPLOAD_REAL_PATH + filename;
        // 转码得到的avi文件的完整路径
        String aviFileFullName = oldFileFullName + ".avi";
        // 转码得到的flv文件的完整路径
        String flvFileFullName = oldFileFullName + ".flv";
        // 转码得到的截图文件的完整路径
        String picFileFullName = oldFileFullName + ".jpg";

        File oldFile = new File(oldFileFullName);
        File aviFile = new File(aviFileFullName);

        // 拼接mencoder转码命令（转为avi）
        StringBuffer cmd = new StringBuffer(Const.MENCODER_EXE);
        cmd.append(" ");
        cmd.append(oldFileFullName);
        cmd.append(" -oac mp3lame -lameopts preset=64 -ovc xvid -xvidencopts bitrate=600 -of avi -o ");
        cmd.append(aviFileFullName);
        cmd.append("\r\n");// Windows的换行符

        // 拼接ffmpeg转码命令（转为flv）
        cmd.append(Const.FFMPEG_EXE);
        cmd.append(" -i ");
        cmd.append(aviFileFullName);
        cmd.append(" -ab 128 -acodec libmp3lame -ac 1 -ar 22050 -r 29.97 -qscale 6 -y ");
        cmd.append(flvFileFullName);
        cmd.append("\r\n");

        // 拼接ffmpeg截图命令
        cmd.append(Const.FFMPEG_EXE);
        cmd.append(" -i ");
        cmd.append(flvFileFullName);
        // 在视频播放时长的中间处截图
        cmd.append(" -y -f image2 -ss " + (duration / 2) + " -t 0.001 -s 120x90 ");
        cmd.append(picFileFullName);
        cmd.append("\r\n");
        cmd.append("exit"); // 退出Windows命令窗口的命令

        try {
            // 将上述若干命令串写入批处理文件
            File batchFile = new File(Const.UPLOAD_REAL_PATH + "convert.bat");
            FileWriter fw = new FileWriter(batchFile);
            fw.write(cmd.toString());
            fw.flush();
            fw.close();

            System.out.print("转码开始...");
            // 调用本地cmd命令执行批处理文件
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("cmd.exe /C start " + batchFile.getAbsolutePath());
            // 下面的代码主要使得proc与当前线程同步，与转码业务无关。
            InputStream stderr = proc.getErrorStream();
            InputStreamReader isr = new InputStreamReader(stderr);
            BufferedReader br = new BufferedReader(isr);
            while (br.readLine() != null) {// readLine为阻塞方法
                // 不作任何处理
            }
            proc.waitFor();// 使当前线程等待
            br.close();
            batchFile.delete();// 转码完毕后删除批处理文件
            System.out.println("转码完毕");
        } catch (IOException e) {
            System.out.println("文件读写失败！");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        oldFile.delete();// 删除转码前的视频文件
        aviFile.delete();// 删除转码的中间（avi）文件

        queue.remove(v);// 将视频对象从队列中删除
        v.setStatus(Const.VIDEO_STATUS_CONVERTED);// 修改视频状态为已转码
        videoDao.update(v);// 更新视频对象到数据库
    }
}
