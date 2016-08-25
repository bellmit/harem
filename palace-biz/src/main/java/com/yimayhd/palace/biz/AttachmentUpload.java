package com.yimayhd.palace.biz;

import com.chinanetcenter.api.domain.*;
import com.chinanetcenter.api.exception.HttpClientException;
import com.chinanetcenter.api.sliceUpload.JSONObjectRet;
import com.chinanetcenter.api.sliceUpload.PutExtra;
import com.chinanetcenter.api.util.*;
import com.chinanetcenter.api.wsbox.FileAvInfo;
import com.chinanetcenter.api.wsbox.FileManageCommand;
import com.chinanetcenter.api.wsbox.FileUploadCommand;
import com.chinanetcenter.api.wsbox.SliceUploadResumable;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimayhd.palace.result.AttachmentUploadResult;
import com.yimayhd.palace.util.UrlUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

public class AttachmentUpload {

    public static void main(String[] args) throws Exception {
        Config.AK = "3789c5976cd89914f990b642af2924c6b4fa1f0e";
        Config.SK = "89563041d009a238fb27407a4d0de71061977d8e";//89563041d009a238fb27407a4d0de71061977d8e  //89563041d009a238fb27407a4d0de71061977d8ek
        String bucketName = "gellefreres-test001"; // 空间名称
        String file_key = "123.mp3";
        String localFilePath = "F://123.mp3";         // 本地文件路径
        com.chinanetcenter.api.Demo demo = new com.chinanetcenter.api.Demo();
        String fileName = RandomStringUtils.randomAlphabetic(32);
        //文件上传
        File tempFile = File.createTempFile(fileName, null);
        demo.upload(bucketName, file_key, localFilePath);

        //文件列表
        //  demo.listFile(bucketName);
//		//文件下载
//		demo.downFile(bucketName, file_key,localFilePath);
//
//		// 文件转码
//		String notifyURL = "";
//		String force = "1";
//		String fops = "avthumb/flv;avthumb/mp4/vcodec/H.264/acodec/AAC";
//		HttpClientResult result = demo.fileTrans(bucketName, fileName, fops, notifyURL, force);
//		System.out.println(result);
//
//		// 大文件分片上传
//		PutPolicy putPolicy = new PutPolicy();
//		putPolicy.setOverwrite(1); // 1 服务器上已有文件则覆盖上传 0 不覆盖
//		demo.sliceUpload(bucketName, fileName, localFilePath, putPolicy);

        //获取avinfo
//        demo.getAvinfo(file_key);

    }

    /**
     * 文件上传，上传本地路径的文件
     */
    public AttachmentUploadResult upload(String bucketName, String fileName, String localFilePath) {
        PutPolicy putPolicy = new PutPolicy(); // 上传策略
        putPolicy.setOverwrite(1); // 1覆盖上传 0 不覆盖
        //"fsizeLimit":0,"instant":0,"separate":0
      /*  putPolicy.setFsizeLimit(0);
        putPolicy.setInstant(0);
        putPolicy.setSeparate(0L);
        putPolicy.setDeadline("1472054400000");*/
        putPolicy.setReturnBody("key=$(key)&fname=$(fname)&fsize=$(fsize)&url=$(url)"); // 设置返回字符串，不设置默认返回hash
        HttpClientResult result = FileUploadCommand.upload(bucketName, fileName, localFilePath, putPolicy);
        //System.out.println(result.toString());
        if (result.getStatus() == 200) {
            AttachmentUploadResult attachmentUploadResult = null;
            try {
                attachmentUploadResult = new AttachmentUploadResult();
                BeanUtils.populate(attachmentUploadResult, UrlUtil.convertParamsString2Map(result.getResponse()));
                //获取时长
                Avinfo avinfo = getAvinfo(attachmentUploadResult.getKey());
                attachmentUploadResult.setDuration(getDuration(avinfo));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return attachmentUploadResult;
        } else {
            return null;
        }
    }

    /**
     * 文件上传，根据文件流上传
     */
    public void upload(String bucketName, String file_key, String fileName, InputStream inputStream) {
        PutPolicy putPolicy = new PutPolicy(); // 上传策略
        putPolicy.setOverwrite(1); // 1覆盖上传 0 不覆盖
        putPolicy.setReturnBody("key=$(key)&fname=$(fname)&fsize=$(fsize)&url=$(url)"); // 设置返回字符串，不设置默认返回hash
        HttpClientResult result = FileUploadCommand.upload(bucketName, file_key, fileName, inputStream, putPolicy);
        System.out.println(result.toString());
    }


    /**
     * 转码
     *
     * @param bucketName
     * @param fileKey
     * @param fops
     * @param notifyURL
     * @param force
     * @return
     */
    public HttpClientResult fileTrans(String bucketName, String fileKey, String fops, String notifyURL, String force) {
        String encodedEntryURI = EncodeUtils.urlsafeEncode(bucketName + ":" + fileKey);
        String url = Config.MGR_URL + "/fileTypeTransferCmd/transfer/" + encodedEntryURI;
        String value = EncodeUtils.urlsafeEncode(EncryptUtil.sha1Hex(("/transfer/" + encodedEntryURI + "\n").getBytes(), Config.SK));
        String Authorization = Config.AK + ":" + value;
        Map<String, String> headMap = new HashMap<String, String>();
        headMap.put("Authorization", Authorization);
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("bucket", bucketName);
        paramMap.put("key", fileKey);
        paramMap.put("fops", fops);
        if (StringUtil.isNotEmpty(notifyURL)) {
            paramMap.put("notifyURL", notifyURL);
        }
        if (StringUtil.isNotEmpty(force)) {
            paramMap.put("force", force);
        }
        return HttpClientUtil.httpPost(url, headMap, paramMap);
    }

    /**
     * 分片上传文件，适合大文件
     *
     * @param bucketName
     * @param fileKey
     * @param filePath
     * @param putPolicy
     */
    public void sliceUpload(final String bucketName, final String fileKey, final String filePath, PutPolicy putPolicy) {
        if (StringUtil.isEmpty(putPolicy.getScope())) {
            putPolicy.setScope(bucketName + ":" + fileKey);
        }
        if (putPolicy.getDeadline() == null) {
            putPolicy.setDeadline(String.valueOf(DateUtil.nextHours(3, new Date()).getTime()));
        }
        // 读取持久化数据，如果不做断点续传，则不需获取
        PutExtra putExtra = getPutExtra(bucketName, fileKey);

        // 设置默认参数,不断点续传也需要设置此参数
        if (putExtra == null) {
            putExtra = setPutExtra(bucketName, fileKey, filePath, putPolicy);
        }

        JSONObjectRet jsonObjectRet = new JSONObjectRet() {
            /**
             * 文件上传成功后会回调此方法
             */
            @Override
            public void onSuccess(JsonNode obj) {
                File fileHash = new File(filePath);
                String eTagHash = WetagUtil.getEtagHash(fileHash.getParent(), fileHash.getName());// 根据文件内容计算hash
                SliceUploadHttpResult result = new SliceUploadHttpResult(obj);
                if (eTagHash.equals(result.getHash())) {
                    System.out.println("上传成功");
                } else {
                    System.out.println("hash not equal,eTagHash:" + eTagHash + " ,hash:" + result.getHash());
                }
            }

            @Override
            public void onSuccess(byte[] body) {
                System.out.println(new String(body));
            }

            // 文件上传失败回调此方法
            @Override
            public void onFailure(Exception ex) {
                if (ex instanceof HttpClientException) {
                    System.out.println(((HttpClientException) ex).code);
                }
                System.out.println("上传出错，" + ex.getMessage());
            }

            // 进度条展示，每上传成功一个块回调此方法
            @Override
            public void onProcess(long current, long total) {
                System.out.printf("%s\r", current * 100 / total + " %");
            }

            /**
             * 持久化，断点续传时把进度信息保存，下次再上传时把JSONObject赋值到PutExtra 如果无需断点续传，则此方法放空
             */
            @Override
            public void onPersist(JsonNode obj) {
                String key = DigestUtils.md5Hex(bucketName + ":" + fileKey);
                File configFile = new File(System.getProperty("user.home")
                        + File.separator + bucketName + File.separator + key
                        + "_sliceConfig.properties");

                synchronized (configFile) {
                    FileOutputStream fileOutputStream = null;
                    try {
                        if (!configFile.getParentFile().exists()) {
                            configFile.getParentFile().mkdirs();
                        }
                        if (!configFile.exists()) {
                            configFile.createNewFile();
                        }
                        fileOutputStream = new FileOutputStream(configFile);
                        ObjectMapper objectMapper = new ObjectMapper();
                        fileOutputStream.write(objectMapper.writeValueAsString(obj).getBytes());
                        fileOutputStream.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e) {
                            }
                        }
                    }
                }
            }
        };
        SliceUploadResumable sliceUploadResumable = new SliceUploadResumable();
        sliceUploadResumable.execUpload(bucketName, fileKey, filePath, putPolicy, putExtra, jsonObjectRet);

    }

    public void listFile(String bucket) {
        int querySize = 10;
        String prex = "";
        HttpClientResult result = FileManageCommand.fileList(bucket, String.valueOf(querySize), prex, "", "");
//        System.out.println(result.getStatus() + ":" + result.getInnerResponse());
        while (result != null && result.getStatus() == 200 && StringUtil.isNotEmpty(result.getResponse()) && !"{}".equals(result.getResponse())) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                FileListObject fileListObject = objectMapper.readValue(result.getResponse(), FileListObject.class);
                for (String folder : fileListObject.getCommonPrefixes()) {
                    System.out.println("folder:" + folder);
                }
                for (FileMessageObject object : fileListObject.getItems()) {
                    System.out.print("key:" + object.getKey() + "\t");
//                    System.out.print("putTime:" + object.getPutTime() + "\t");
//                    System.out.print("hash:" + object.getHash() + "\t");
//                    System.out.print("fsize:" + object.getFsize() + "\t");
//                    System.out.print("mimeType:" + object.getMimeType() + "\t");
                    System.out.println();
                }
                if (fileListObject.getItems().size() < querySize) {
                    break;
                }
                result = FileManageCommand.fileList(bucket, String.valueOf(querySize), prex, "", fileListObject.getMarker());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public PutExtra setPutExtra(String bucketName, String fileKey, String filePath, PutPolicy putPolicy) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("bucketName", bucketName);
        params.put("fileKey", fileKey);
        params.put("filePath", filePath);
        params.put("putPolicy", putPolicy.toString());
        String token = TokenUtil.getUploadToken(putPolicy);
        params.put("token", token);
        PutExtra putExtra = new PutExtra();
        putExtra.params = params;
        return putExtra;
    }

    public void testMultiUploadTest() {
        List<File> fileList = new ArrayList<File>();//要上传的文件列表需与PutPolicy里的scope对应上
        fileList.add(new File("D:/001.jpg"));
        fileList.add(new File("D:/abcd111.txt"));
        PutPolicy putPolicy = new PutPolicy();
        //scope只能是bucket:fileName:originFileName或者bucket 且两者不能同时存在如(bucket1:fileName1:originFileName1#bucket1)
        StringBuilder scope = new StringBuilder();
        scope.append(EncodeUtils.urlsafeEncode("fuyztest001:0.png:abcd111.txt")).append("#");
        scope.append(EncodeUtils.urlsafeEncode("fuyztest001:1.png:001.jpg"));
        putPolicy.setScope(scope.toString());
        putPolicy.setOverwrite(0);
        putPolicy.setDeadline(String.valueOf(DateUtil.nextHours(1, new Date()).getTime()));
        HttpClientResult result = FileUploadCommand.multiUpload(fileList, putPolicy, null);
        System.out.println(result);
    }

    public PutExtra getPutExtra(String bucketName, String fileName) {
        String key = DigestUtils.md5Hex(bucketName + ":" + fileName);
        File configFile = new File(System.getProperty("user.home")
                + File.separator + bucketName + File.separator + key
                + "_sliceConfig.properties");
        if (!configFile.exists())
            return null;
        FileReader reader;
        int fileLen = (int) configFile.length();
        char[] chars = new char[fileLen];
        try {
            reader = new FileReader(configFile);
            reader.read(chars);
            String txt = String.valueOf(chars);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode obj = objectMapper.readTree(txt);
            PutExtra putExtra = new PutExtra(obj);
            reader.close();
            return putExtra;
        } catch (Exception e) {
            return null;
        }
    }

    public Avinfo getAvinfo(String fileKey) {
        try {
            Avinfo avinfo = FileAvInfo.getFileAvinfo(fileKey);
            if (avinfo != null) {
                System.out.println(avinfo.toJson());
                return avinfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public long getDuration(Avinfo avinfo) {
        if (avinfo == null) {
            return 0;
        }
        List<Stream> streams = avinfo.getStreams();
        if (streams == null || streams.size() <= 0) {
            return 0;
        }
        String d = streams.get(0).getDuration();
        if (StringUtils.isNotBlank(d)) {
            BigDecimal bigDecimal = new BigDecimal(d);
            return bigDecimal.longValue();
        } else {
            return 0;
        }
    }

}
