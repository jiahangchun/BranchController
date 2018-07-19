//package com.jiahangchun;
//
//import com.google.common.collect.Lists;
//import com.mockuai.ec.celebritycenter.core.exception.ServerException;
//import com.mockuai.ec.celebritycenter.core.util.CmdExecuter;
//import com.mockuai.ec.celebritycenter.core.util.StringGetter;
//import lombok.extern.log4j.Log4j2;
//import org.apache.commons.lang.StringUtils;
//
//import java.io.*;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * @author chunchun
// * @descritpion
// * @date Created at 2018/7/13 下午5:42
// **/
//public class MysqlCorrect {
//
//    private static String ffmpegUri="ffmpeg";
//
//    public static void main(String[] args) {
//        Map<Long,Long> heightMap=new HashMap<>();
//        Map<Long,String> gifUrlMap=new HashMap<>();
//
//        //init
//        gifUrlMap.put(1L,"https://mktv-in.oss-cn-hangzhou.aliyuncs.com/open_file2018070802373416304012896.gif");
//
//        for(Map.Entry<Long, String> entity:gifUrlMap.entrySet()){
//            String gifUrl=entity.getValue();
//            String localFilePath=saveFileToLocal(gifUrl);
//            Long id=entity.getKey();
//
//            List<String> cmd = Lists.newArrayListWithExpectedSize(13);
//            cmd.add(ffmpegUri);
//            cmd.add("-i");
//            cmd.add(localFilePath);
//            final StringBuilder sb = new StringBuilder();
//            StringGetter<String> test = new StringGetter<String>() {
//                @Override
//                public void dealString(String str) {
//                    sb.append(str);
//                }
//            };
//            CmdExecuter.exec(cmd, test);
//            log.info("videoInfo=" + localFilePath);
//
//            //获取视频的高度和宽度
//            //获取具体的数
//            //截取
//            String content = sb.toString();
//            Pattern pt = Pattern.compile("(bgra, [0-9]*x)([0-9]*)");
//            Matcher match = pt.matcher(content);
//            if (match.find()) {
//                String size = match.group(2);
//                log.info("video_height=" + size);
//                if (StringUtils.isNotBlank(size)) {
//                    Long gifHeight=Long.valueOf(size);
//                    heightMap.put(id,gifHeight);
////                    videoDTO.setGifHeight(Long.valueOf(size));
//                }
//            }
//        }
//
//        StringBuilder sb=new StringBuilder();
////        update video set gif_height=667 where id=19
//        for(Map.Entry<Long, Long> entity:heightMap.entrySet()){
//            Long id=entity.getKey();
//            Long height=entity.getValue();
//            sb.append("update video set gif_height=").append(height).append(" where id=").append(id).append("/r/n");
//        }
//
//     }
//
//
//    /**
//     * 下载本地文件
//     *
//     * @param
//     * @return
//     * @throws ServerException
//     */
//    private static String saveFileToLocal(String ossFilePath) throws ServerException {
//
//        try {
//            HttpURLConnection connection = null;
//            InputStream inputStream = null;
//            URL httpUrl = new URL(ossFilePath);
//            connection = (HttpURLConnection) httpUrl.openConnection();
//            connection.setDoInput(true);
//            connection.setDoOutput(true);
//            connection.setUseCaches(false);
//            connection.connect();
//            inputStream = connection.getInputStream();
//            BufferedInputStream bis = new BufferedInputStream(inputStream);
//
//
//            String tmpFolderPath = "/tmp";
//            //文件临时存放目录准备
//            File folder = new File(tmpFolderPath);
//            if (folder.exists() == false) {
//                folder.mkdir();
//            } else if (folder.isDirectory() == false) {
//                folder.delete();
//                folder.mkdir();
//            }
//
//            String fileName = "" + System.currentTimeMillis()+".gif";
//            String filePath = tmpFolderPath + "/" + fileName;
//            OutputStream os = null;
//            try {
//                File file = new File(filePath);
//                os = new FileOutputStream(file);
//                BufferedOutputStream bos = new BufferedOutputStream(os);
//
//                byte[] buf = new byte[4096];
//                int length = bis.read(buf);
//                while (length != -1) {
//                    bos.write(buf, 0, length);
//                    length = bis.read(buf);
//                }
//                bos.close();
//                bis.close();
//                connection.disconnect();
//                return file.getAbsolutePath();
//            } catch (Exception e) {
//                log.error("error to write file data on local storage", e);
//            } finally {
//                if (os != null) {
//                    try {
//                        os.close();
//                    } catch (Exception ignore) {
//
//                    }
//                }
//            }
//            return null;
//        } catch (Exception e) {
//            log.error("下载文件失败,{}", e.getMessage());
//            throw new ServerException("下载OSS文件失败," + e.getMessage());
//        }
//    }
//
//}
